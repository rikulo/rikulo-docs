package org.rikulo.rimd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/** Generate TOC.
 * @author tomyeh
 */
class TOC {
	void read(Properties props, File dir, String contextPath) throws IOException {
		//Parse toc.txt (root) first and also generate cats
		final List<Category> cats = new ArrayList<Category>();
		props.put("toc-root", parse(new File(dir, "toc.txt"), contextPath, null, null, cats));

		Main.log("Read TOC. There are "+cats.size()+" categories.");

		//Parse others
		final String[] flnms = dir.list();
		if (flnms != null)
			for (int i = 0; i < flnms.length; ++i) {
				final String flnm = flnms[i];
				if (flnm.startsWith("toc-") && flnm.endsWith(".txt")) {
					final String name = flnm.substring(4, flnm.length() - 4);
					final StringBuffer bfr = new StringBuffer(), afr = new StringBuffer();
					boolean found = false;
					for (final Category cat: cats) {
						if (found) {
							afr.append(cat.output);
						} else {
							found = cat.name.equals(name);
							if (!found)
								bfr.append(cat.output);
						}
					}
					if (!found)
						Main.error("Category "+name+" not found in "+cats);

					props.put("toc-" + name, parse(new File(dir, flnm), contextPath, bfr.toString(), afr.toString(), null));
				}
			}
	}
	/**
	 * Parse a TOC file
	 * @param before the content that shall be inserted before the category. Ignored if null.
	 * @param after the content that shall be inserted after the category. Ignored if null.
	 * @param cats a list of categories to return. Ignored if null. If not null, the categories
	 * found will be added to the given list.
	 */
	String parse(File fl, String contextPath, String before, String after, List<Category> cats)
	throws IOException {
		final BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(fl), "UTF-8"));
		try {
			final StringBuffer sb = new StringBuffer(8192).append("<ul class=\"root-level\">\n");
			if (before != null) sb.append(before);

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
					realuri = (txt + '/' + sublink).replace(' ', '_').replace('*', '-');
					uri = txt.replace(' ', '_').replace('*', '-');
				} else {
					uri = realuri = txt.replace(' ', '_').replace('*', '-');
				}

				final StringBuffer lnsb = new StringBuffer();
				lnsb.append("<li>");
				if (link) {
					lnsb.append("<a href=\"");
					if (contextPath != null)
						lnsb.append(contextPath);
					for (int j = 0; j < level; ++j)
						lnsb.append(paths[j]);
					lnsb.append('/').append(realuri);
					if (!index)
						lnsb.append(".html");
					lnsb.append("\">");
				}
				lnsb.append(txt);
				if (link)
					lnsb.append("</a>");
				lnsb.append("</li>\n");

				if (cats != null && level == 0)
					cats.add(new Category(txt.replace(' ', '_'), lnsb.toString()));
				sb.append(lnsb);

				paths[level] = '/' + uri;
			}

			while (--level >= 0)
				sb.append("</ul></li>\n");

			if (after != null)
				sb.append(after);
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

class Category {
	String name;
	String output;

	Category(String name, String output) {
		this.name = name;
		this.output = output;
	}
	public String toString() {
		return name;
	}
}
