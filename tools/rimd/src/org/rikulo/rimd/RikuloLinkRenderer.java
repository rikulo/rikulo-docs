package org.rikulo.rimd;

import org.pegdown.LinkRenderer;
import org.pegdown.ast.ExpLinkNode;

public class RikuloLinkRenderer extends LinkRenderer {
	final String _ext;
	RikuloLinkRenderer(String ext) {
		_ext = ext;
	}
	public Rendering render(ExpLinkNode node, String text) {
		String url = node.url;
		if (url.equals("api:")) { //package: [view](api:)
			return new Rendering(
				"http://rikulo.org/api/_/rikulo_" + text.replace('/', '_') + ".html", text);
		} else if (url.startsWith("api:")) { //class: [View:set:width](api:view)
			final String pkg = url.substring(4).replace('/', '_');
			final int i = text.indexOf(':');
			final String clsnm = i >= 0 ?
				text.substring(0, i) + ".html#" + text.substring(i + 1):
				text + _ext;
			return new Rendering(
				"http://rikulo.org/api/_/rikulo_" + pkg + "/" + clsnm, text);
		} else {
			final int i = url.lastIndexOf(".md");
			final char cc;
			if (i >= 0 && ((i + 3) == url.length() || (cc=url.charAt(i + 3)) == '#' || cc == '?'))
				return new Rendering(
					url.substring(0, i+1) + "html" + url.substring(i + 3), text);
		}
		return super.render(node, text);
	}
}
