package org.rikulo.rimd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/** Generate TOC.
 * @author tomyeh
 */
class TOC {
	final List<TOCInfo> tocs = new LinkedList<TOCInfo>();

	void read(Properties props, File dir, String contextPath) throws IOException {
		final String[] names = dir.list();
		if (names != null)
			for (int i = 0; i < names.length; ++i) {
				final String name = names[i];
				if (name.startsWith("toc-") && name.endsWith(".txt")) {
					Main.log("Read TOC in "+name);
					props.put(name.substring(0, name.length() - 4),
						parse(new File(dir, name), contextPath));
				}
			}
	}
	String parse(File fl, String contextPath) throws IOException {
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
						throw new IOException(fl+":"+nLine+": TAB not allowed. Please use space instead");
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

				final boolean link = cc != '!', index = cc == '+';
				String txt = (!link || index ? in.substring(i + 1): in.substring(i)).trim();
				final String uri, realuri;
				if (txt.endsWith(")")) { //xxx (sublink)
					final int m = txt.lastIndexOf('(');
					if (m < 0)
						throw new IOException(fl+":"+nLine+": Unclosed parenthesis");
					final String sublink = txt.substring(m + 1, txt.length() - 1); 
					txt = txt.substring(0, m).trim();
					realuri = (txt + '/' + sublink).replace(' ', '_');
					uri = txt.replace(' ', '_');
				} else {
					uri = realuri = txt.replace(' ', '_');
				}

				sb.append("<li>");
				if (link) {
					sb.append("<a href=\"");
					if (contextPath != null)
						sb.append(contextPath);
					for (int j = 0; j < level; ++j)
						sb.append(paths[j]);
					sb.append('/').append(realuri);
					if (!index)
						sb.append(".html");
					sb.append("\">");
				}
				sb.append(txt);
				if (link)
					sb.append("</a>");
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
}
class TOCInfo {
	String name;
	String content;
	TOCInfo(String name, String content) {
		this.name = name;
		this.content = content;
	}
}