//Copyright (C) 2012 Potix Corporation. All Rights Reserved.
//History: Sat, July 07, 2012
// Author: tomyeh
package org.rikulo.rimd;

import org.pegdown.PegDownProcessor;
import java.io.Writer;
import java.io.IOException;

public class Processor {
	public final String header, footer, api, dartapi, source, extension;
	public int count = 0;

	public Processor(String header, String footer, String api, String dartapi, String source, String ext) {
		this.header = header;
		this.footer = footer;
		this.api = api;
		this.dartapi = dartapi;
		this.source = source;
		this.extension = ext;
	}
	public void process(String src, Writer dst) throws IOException {
		if (this.header != null)
			dst.write(header);

		++ count;
		dst.write(new PegDownProcessor().markdownToHtml(src, new RikuloLinkRenderer(this)));

		if (footer != null)
			dst.write(footer);
	}
}
