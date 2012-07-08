//Copyright (C) 2012 Potix Corporation. All Rights Reserved.
//History: Sat, July 07, 2012
// Author: tomyeh
package org.rikulo.rimd;

import java.io.*;
import java.nio.channels.FileChannel;

public class Main {
	final Processor _proc;
	final File _src, _dst;
	final boolean _force;

	public static void main(String[] args) throws IOException {
		new Main(args).run();
	}
	private Main(String[] args) throws IOException {
		String src = null, dst = null, header = null, footer = null, ext = ".html";
		boolean force = false;
		for (int i = 0; i < args.length; ++i) {
			if ("--head".equals(args[i])) {
				if (++i >= args.length)
					error("Filename required");
				header = args[i];
			} else if ("--foot".equals(args[i])) {
				if (++i >= args.length)
					error("Filename required");
				footer = args[i];
			} else if ("--ext".equals(args[i])) {
				if (++i >= args.length)
					error("Filename required");
				ext = args[i];
				if (ext.length() > 0 && ext.charAt(0) != '.')
					ext = "." + ext;
			} else if ("-f".equals(args[i])) {
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
		if (src == null)
			error(
		 "rimd - convert rikulo flavored markdown to HTML\n\n"
		+"rimd [-h header] [-f footer] [src [dst]]\n\n"
		+"--head header\tspecifies a file that will be generated before the content.\n"
		+"--foot footer\tspecifies a file that will be generated after the content.\n"
		+"--ext extension\tspecifies the extension to replace \".md\". Default: \".html\".\n"
		+"-f\tforces the creation of the destination; overwrite if exists.\n"
		+"src\tspecifies the source. If it is the directory, all files under the given directory will be processed.\n"
		+"dst\tspecifies the destination, either a file or a console. If omitted, it will be generated to the console."
			);

		_src = new File(src);
		if (_src.isDirectory() && (dst == null || new File(dst).isFile()))
			error("The destination must be specified and can't be a file, because the source is a directory, "+_src);
		_dst = dst != null ? new File(dst): null;

		if (header != null)
			header = read(new File(header));
		if (footer != null)
			footer = read(new File(footer));

		_proc = new Processor(header, footer, ext);
		_force = force;
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
					error(dst + " exists. Please specify -f to overwrite if you want.");
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
			error(dst+" not found. Please specify -f to create it if you want.");
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
