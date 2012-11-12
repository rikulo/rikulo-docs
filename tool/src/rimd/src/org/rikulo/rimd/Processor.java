//Copyright (C) 2012 Potix Corporation. All Rights Reserved.
//History: Sat, July 07, 2012
// Author: tomyeh
package org.rikulo.rimd;

import org.pegdown.PegDownProcessor;
import java.io.Writer;
import java.io.IOException;
import java.util.Properties;

public class Processor {
	public final String api, libapi, dartapi, source, libsource, extension;
	public int count = 0;
	private final String _header, _footer0, _footer1;
	private Properties _props;

	public Processor(Properties props, String header, String footer,
	String api, String libapi, String dartapi, String source, String libsource, String ext) {
		_props = props;
		_header = header;
		if (footer != null) {
			final int i = footer.indexOf("${toc}");
			if (i >= 0) {
				_footer0 = footer.substring(0, i);
				_footer1 = footer.substring(i + 6);
			} else {
				_footer0 = footer;
				_footer1 = null;
			}
		} else {
			_footer0 = _footer1 = null;
		}
		this.api = api;
		this.libapi = libapi;
		this.dartapi = dartapi;
		this.source = source;
		this.libsource = libsource;
		this.extension = ext;
	}
	public void process(String src, Writer dst, String toc) throws IOException {
		if (_header != null)
			dst.write(_header);

		++ count;
		dst.write(new PegDownProcessor().markdownToHtml(src, new RikuloLinkRenderer(this)));

		if (_footer0 != null) {
			dst.write(_footer0);
			if (_footer1 != null) {
				toc = _props != null ? _props.getProperty(toc): null;
				if (toc != null)
					dst.write(toc);
				dst.write(_footer1);
			}
		}
	}
}
