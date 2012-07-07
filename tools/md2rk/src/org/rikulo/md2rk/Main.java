package org.rikulo.md2rk;

import java.io.*;

public class Main {
	final Processor _proc;
	final File _src, _dst;

	public static void main(String[] args) throws IOException {
		new Main(args).run();
	}
	private static void error(String msg) {
		System.err.println(msg);
		System.exit(-1);
	}
	private Main(String[] args) throws IOException {
		String src = null, dst = null, header = null, footer = null, ext = ".html";
		for (int i = 0; i < args.length; ++i) {
			if ("-h".equals(args[i])) {
				if (++i >= args.length)
					error("Filename required");
				header = args[i];
			} else if ("-f".equals(args[i])) {
				if (++i >= args.length)
					error("Filename required");
				footer = args[i];
			} else if ("-x".equals(args[i])) {
				if (++i >= args.length)
					error("Filename required");
				ext = args[i];
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
		 "md2rk - Convert Markdown to Rikulo HTML files\n\n"
		+"md2rk [-h header] [-f footer] [src [dst]]\n\n"
		+"-h header\tspecifies a file that will be generated before the content.\n"
		+"-f footer\tspecifies a file that will be generated after the content.\n"
		+"-x extension\tspecifies the extension to replace \".md\". Default: \".html\".\n"
		+"src\tspecifies the source. If it is the directory, all files under the given directory will be processed.\n"
		+"dst\tspecifies the destination, either a file or a console. If omitted, it will be generated to the console."
			);

		_src = new File(src);
		if (_src.isDirectory() && dst == null)
			error("The destination must be specified, because the source is a directory, "+_src);
		_dst = dst != null ? new File(dst): null;

		if (header != null)
			header = read(new File(header));
		if (footer != null)
			footer = read(new File(footer));

		_proc = new Processor(header, footer, ext);
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
		if (!_src.isDirectory()) {
			@SuppressWarnings("resource")
			final Writer out = new OutputStreamWriter(
				_dst != null ? new FileOutputStream(_dst): System.out, "UTF-8");
			try {
				_proc.process(read(_src), out);
			} finally {
				out.close();
			}
			return;
		}
	}
}
