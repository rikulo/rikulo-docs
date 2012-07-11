//Copyright (C) 2012 Potix Corporation. All Rights Reserved.
//History: Wed, July 11, 2012
// Author: jimmyshiau
package org.zkoss.rikulo.tmpgen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Main {
	private static final String TMP_PATTERN = "<tmp.*?>(?s)(.+?)(?s)</tmp>";
	private File headerFile, footerFile;
	private String header, footer;
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		new Main(args).run();
	}
	
	private Main() throws IOException {
		initFiles(new File("D:/home/jimmy/prj/rikulo-docs/docs/_config_/config.props"));
	}

	private Main(String[] args) throws IOException {
		for (int i = 0; i < args.length; ++i) {
			String arg = args[i];
			if ("--config".equals(arg)) {
				if (++i >= args.length)
					error("Filename required");
				initFiles(new File(arg = args[i]));
			} else {
				error("Too many arguments");
			}
		}
	}
	
	private void initFiles(File config) throws IOException {
		final Properties props = new Properties();
		final Reader reader = new InputStreamReader(new FileInputStream(config), "UTF-8");
		try {
			props.load(reader);
		} finally {
			reader.close();
		}
		
		header = getTemplateString((String) props.get("tmp-header"), config);
		footer = getTemplateString((String) props.get("tmp-footer"), config);
		
		headerFile = getTemplateFile((String) props.get("header"), config);
		footerFile = getTemplateFile((String) props.get("footer"), config);
	}


	private String getTemplateString(String str, File config) throws IOException {
		if (str != null) {
			if (!new File(str).isAbsolute())
				str = new File(config.getParent(), str).getPath();
			str = read(new File(str));
		}
		return str;
	}
	
	private File getTemplateFile(String str, File config) {
		if (str != null) {
			File f = new File(str);
			if (!f.isAbsolute())
				f = new File(config.getParent(), str);
			return f;
		}
		return null;
	}


	private void run() throws IOException {
		try {
			Document doc = Jsoup.parse(new URL("http://rikulo.org/learn/"), 10000);
			
			generateHeaderTemplate(doc);
			generateFooterTemplate(doc);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	private void generateHeaderTemplate(Document doc) throws IOException {
		String headerCnt = "";
		String bodyCntTop = header.replaceAll(TMP_PATTERN, "");
		Matcher matcher = Pattern.compile(TMP_PATTERN).matcher(header);
		if (matcher.find()) {
			headerCnt = matcher.group(1);
	    }
		
		relocalHref(doc.getElementsByTag("link"), "href");
		relocalHref(doc.getElementsByTag("script"), "src");
		relocalHref(doc.getElementsByTag("a"), "href");
		relocalHref(doc.getElementsByTag("img"), "src");
		
		doc.getElementsByTag("title").remove();
		doc.getElementsByAttributeValue("href", "/less/learn").remove();
		doc.getElementsByTag("script").get(0).before(headerCnt);
		
		Element inner = doc.getElementById("template-inner");
		inner.children().remove();
		
		matcher = Pattern.compile("(?s)(.+?)template-inner.*?>")
			.matcher(doc.html());
		if (matcher.find()) {
			writeStringToFile(headerFile, matcher.group(0)
				.replace("/resource/js/lib/html5.js", "http://rikulo.org/resource/js/lib/html5.js")
				.concat(bodyCntTop));
	    }
	}
	
	private void relocalHref(Elements links, String attr) {
		for (Element link : links) {
			String href = link.attr(attr);
			if (href.startsWith("/"))
				 link.attr(attr, "http://rikulo.org" + href);
		}
	}

	private void generateFooterTemplate(Document doc) throws IOException {
		String footerCnt = "";
		String bodyCntBottom = footer.replaceAll(TMP_PATTERN, "");
		Matcher matcher = Pattern.compile(TMP_PATTERN).matcher(footer);
		if (matcher.find()) {
			footerCnt = matcher.group(1);
	    }
		doc.getElementById("footer").before(footerCnt);
		
		matcher = Pattern.compile("template-inner.*?>(?s)(.+?)(</div>(?s)(.+?)</html>)").matcher(doc.html());
		
		if (matcher.find()) {
			 writeStringToFile(footerFile, bodyCntBottom.concat(matcher.group(2)));
		}
	}

	private void writeStringToFile(File outFile, String str) throws IOException {
		final Writer out = new OutputStreamWriter(new FileOutputStream(outFile));
		try {
			out.write(str);
		} finally {
			out.close();
		}
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
	private static void error(String msg) {
		System.err.println("rimd: " + msg);
		System.exit(-1);
	}
	private static void log(String msg) {
		System.out.println("rimd: " + msg);
	}

}
