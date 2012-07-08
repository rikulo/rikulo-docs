package org.rikulo.rimd;

import org.pegdown.PegDownProcessor;
import java.io.Writer;
import java.io.IOException;

public class Processor {
	private final String _header, _footer, _ext;

	public Processor(String header, String footer, String ext) {
		_header = header;
		_footer = footer;
		_ext = ext;
	}
	public void process(String src, Writer dst) throws IOException {
		if (_header != null)
			dst.write(_header);

		dst.write(new PegDownProcessor().markdownToHtml(
			src, new RikuloLinkRenderer(_ext)));

		if (_footer != null)
			dst.write(_footer);
	}
}
