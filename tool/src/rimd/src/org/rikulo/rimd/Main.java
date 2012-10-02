//Copyright (C) 2012 Potix Corporation. All Rights Reserved.
//History: Sat, July 07, 2012
// Author: tomyeh
package org.rikulo.rimd;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Map;
import java.util.Properties;

public class Main {
	final Processor _proc;
	final File _src, _dst;
	final boolean _force;

	public static void main(String[] args) throws IOException {
		new Main(args).run();
	}
	private Main(String[] args) throws IOException {
		String src = null, dst = null, header = null, footer = null,
			api = "", dartapi = "", source = "", ext = ".html", toc = null;
		final Properties props = new Properties();
		boolean force = false;
		for (int i = 0; i < args.length; ++i) {
			if ("--config".equals(args[i])) {
				if (++i >= args.length)
					error("Filename required");
				final File config = new File(args[i]);
				final Reader reader = new InputStreamReader(new FileInputStream(config), "UTF-8");
				try {
					props.load(reader);
				} finally {
					reader.close();
				}
				for (final Map.Entry<Object, Object> entry: props.entrySet()) {
					final String nm = (String)entry.getKey(), val = (String)entry.getValue();
					if ("header".equals(nm)) {
						header = val;
						if (!new File(header).isAbsolute())
							header = new File(config.getParent(), header).getPath();
					} else if ("footer".equals(nm)) {
						footer = val;
						if (!new File(footer).isAbsolute())
							footer = new File(config.getParent(), footer).getPath();
					} else if ("extension".equals(nm)) {
						ext = val;
					} else if ("api".equals(nm)) {
						api = val;
					} else if ("dart-api".equals(nm)) {
						dartapi = val;
					} else if ("source".equals(nm)) {
						source = val;
					} else if ("toc".equals(nm)) {
						toc = val;
						if (!new File(toc).isAbsolute())
							toc = new File(config.getParent(), toc).getPath();
					} else if ("force".equals(nm)) {
						force = "true".equals(val);
					}
				}
			} else if ("--force".equals(args[i])) {
				force = true;
			} else if (args[i].startsWith("-")) {
				error("Unknown option: " + args[i]);
			} else if (src == null) {
				src = args[i];
			} else if (dst == null) {
				dst = args[i];
			} else {
				error("Too many arguments");
			}
		}
		if (ext.length() > 0 && ext.charAt(0) != '.')
			ext = "." + ext;
		if (src == null)
			error(
		 "rimd - convert rikulo flavored markdown to HTML\n\n"
		+"rimd [-h header] [-f footer] [src [dst]]\n\n"
		+"--config config.props\tspecifies a configuration file.\n"
		+"--force\tforces the creation of the destination; overwrite if exists.\n"
		+"src\tspecifies the source. If it is the directory, all files under the given directory will be processed.\n"
		+"dst\tspecifies the destination, either a file or a console. If omitted, it will be generated to the console.\n\n"
		+"Here is a sample of config.props:\n\n"
		+"api=http://api.rikulo.org/rikulo/latest/rikulo_\n"
		+"source=https://github.com/rikulo/rikulo/tree/master/\n"
		+"header=header.html\n"
		+"footer=footer.html\n"
		+"extension=.html\n"
		+"force=true\n"
			);

		_src = new File(src);
		if (_src.isDirectory() && (dst == null || new File(dst).isFile()))
			error("The destination must be specified and can't be a file, because the source is a directory, "+_src);
		_dst = dst != null ? new File(dst): null;

		if (header != null)
			header = read(new File(header));
		if (footer != null)
			footer = read(new File(footer));

		if (toc != null)
			props.put("toc", readTOC(new File(toc), (String)props.get("context-path")));

		_proc = new Processor(replaceVariables(header, props),
			replaceVariables(footer, props), api, dartapi, source, ext);
		_force = force;
	}
	private static String replaceVariables(String content, Properties props) {
		if (props.isEmpty())
			return content;

		StringBuffer sb = null;
		for (int i = 0;;) {
			final int j = content.indexOf("${", i);
			if (j < 0)
				return sb != null ? sb.append(content.substring(i)).toString(): content;

			final int k = content.indexOf('}', j + 2);
			if (k < 0)
				error("Unclosed " + (content.length() > j + 10 ? content.substring(j, j + 10): content.substring(j)));

			if (sb == null)
				sb = new StringBuffer();
			sb.append(content.substring(i, j));
			final Object val = props.get(content.substring(j + 2, k));
			if (val != null)
				sb.append(val);
			i = k + 1;
		}
	}
	private static String readTOC(File fl, String contextPath) throws IOException {
		final BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(fl), "UTF-8"));
		try {
			final StringBuffer sb = new StringBuffer(8192).append("<ul class=\"root-level\">\n");
			String in;
			int indent = 0, level = 0;
			final int[] indents = new int[10]; //at most 10 levels
			final String[] paths = new String[10];
			paths[0] = "";
			if (contextPath != null && contextPath.endsWith("/"))
				contextPath = contextPath.substring(0, contextPath.length() - 1);

			nextLine:
			for (int nLine = 1; (in = reader.readLine()) != null; ++nLine) {
				int i = 0, len = in.length();
				char cc;
				for (;; ++i) {
					if (i >= len)
						continue nextLine;
					cc = in.charAt(i);
					if (cc == '\t')
						throw new IOException("Line "+nLine+": TAB not allowed. Please use space instead");
					if (cc != ' ') {
						if (cc == '/' && i + 1 < len && in.charAt(i + 1) == '/')
							continue nextLine;
						break;
					}
				}
				if (i > indent) {
					final int sblen = sb.length();
					if (sblen > 6 && sb.substring(sblen - 6, sblen - 1).equals("</li>"))
						sb.delete(sblen - 6, sblen - 1); //remove "</li>"
					addSpaces(sb, level);
					sb.append("<ul>\n");
					indents[++level] = i;
				} else if (i < indent) {
					while (--level >= 0) {
						addSpaces(sb, level);
						sb.append("</ul></li>\n");
						if (indents[level] <= i)
							break;
					}
				}
				indent = i;
				addSpaces(sb, level);
				final boolean
					link = cc != '!',
					index = cc == '+';
				final String txt = (!link || index ? in.substring(i + 1): in.substring(i)).trim(),
					uri = txt.replace(' ', '_');
				sb.append("<li");
				if (link) {
					sb.append("><a href=\"");
					if (contextPath != null)
						sb.append(contextPath);
					for (int j = 0; j < level; ++j)
						sb.append(paths[j]);
					sb.append('/').append(uri);
					if (!index)
						sb.append(".html");
					sb.append("\">");
				} else {
					sb.append(" class=\"category\"><span>");
				}
				sb.append(txt);
				if (link)
					sb.append("</a>");
				else
					sb.append("</span>");
				sb.append("</li>\n");
				paths[level] = '/' + uri;
			}
			while (--level >= 0)
				sb.append("</ul>\n");
			return sb.append("</ul>\n").toString();
		} finally {
			reader.close();
		}
	}
	private static void addSpaces(StringBuffer sb, int count) {
		while (--count >= 0)
			sb.append(' ');
	}
	private static String read(File fl) throws IOException {
		final Reader reader = new InputStreamReader(new FileInputStream(fl), "UTF-8");
		try {
			final StringBuffer sb = new StringBuffer(4096);
			final char[] buf = new char[1024*4];
			for (int v; (v = reader.read(buf)) >= 0;) {
				if (v > 0)
					sb.append(new String(buf, 0, v));
			}
			return sb.toString();
		} finally {
			reader.close();
		}
	}
	private void run() throws IOException {
		File src = _src, dst = _dst;
		if (!src.isDirectory()) {
			if (dst != null && dst.isDirectory())
				dst = new File(dst, rename(src.getName(), _proc.extension));
			final Writer out;
			if (dst != null) {
				if (!_force && dst.exists())
					error(dst + " exists. Please specify --force to overwrite if you want.");
				log(src + " => " + dst);
				out = new OutputStreamWriter(new FileOutputStream(dst), "UTF-8");
			} else {
				out = new OutputStreamWriter(System.out);
			}
			try {
				_proc.process(read(src), out);
			} finally {
				out.close();
			}
			return;
		}

		//Process the whole directory
		if (!_force && !dst.exists())
			error(dst+" not found. Please specify --force to create it if you want.");
		process(src, dst);
		log("Total " + _proc.count + " markdown files are processed.");
	}
	private void process(File src, File dst) throws IOException {
		if (src.isFile()) {
			if (src.getName().endsWith(".md")) {
				dst = new File(dst.getParent(), rename(dst.getName(), _proc.extension));
				log(src + " => " + dst);
				final Writer out = new OutputStreamWriter(new FileOutputStream(dst), "UTF-8");
				try {
					_proc.process(read(src), out);
				} finally {
					out.close();
				}
			} else {
				log("copy " + src);
				copy(src, dst);
			}
			return;
		}

		if ("_config_".equals(src.getName()))
			return; //ignored

		if (!dst.exists() && !dst.mkdirs())
			error("Failed to creae a directory, "+dst);
		final String[] files = src.list();
		if (files == null)
			error("Failed to list the files in "+src);
		for (int i =0; i < files.length; ++i)
			process(new File(src, files[i]), new File(dst, files[i]));
	}
	private static void copy(File src, File dst) throws IOException {
		if(!dst.exists())
			dst.createNewFile();

		FileChannel source = null;
		FileChannel destination = null;

		try {
			source = new FileInputStream(src).getChannel();
			destination = new FileOutputStream(dst).getChannel();
			destination.transferFrom(source, 0, source.size());
		} finally {
			if(source != null)
			    source.close();
			if(destination != null)
			    destination.close();
		}
	}	
	private static String rename(String name, String ext) {
		return name.endsWith(".md") ? name.substring(0, name.length() - 3) + ext: name;
	}
	private static void error(String msg) {
		System.err.println("rimd: " + msg);
		System.exit(-1);
	}
	private static void log(String msg) {
		System.out.println("rimd: " + msg);
	}
}
