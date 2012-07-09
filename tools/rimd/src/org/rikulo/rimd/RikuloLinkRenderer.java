//Copyright (C) 2012 Potix Corporation. All Rights Reserved.
//History: Sat, July 07, 2012
// Author: tomyeh
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
				"http://rikulo.org/api/rikulo_" + text.replace('/', '_') + ".html",
				"<code>" + text + "</code>");
		} else if (url.startsWith("api:")) {
		/* Link to a class: [ViewConfig](api:view/impl)
		* Link to a method: [View.requestLayout()](api:view)
		* Link to a getter: [View.width](api:view) or [View.width](api:view:get)
		* Link to a setter: [View.width](api:view:set)
		* Link to a global variable: [activity](api:app)
		*/
			boolean bGet = url.endsWith(":get"), bSet = !bGet && url.endsWith(":set");
			if (bGet || bSet)
				url = url.substring(0, url.length() - 4);
			final String pkg = url.substring(4).replace('/', '_');

			int i = text.lastIndexOf('(');
			final boolean bMethod = i >= 0;
			final String info = i >= 0 ? text.substring(0, i): text;
			i = info.indexOf('.');
			boolean bVar = i < 0 && Character.isLowerCase(text.charAt(0));
			if (bVar)
				return new Rendering(
					"http://rikulo.org/api/rikulo_" + pkg + ".html#" + info,
					"<code>" + text + "</code>");

			final String clsnm = i >= 0 ?
				info.substring(0, i) + ".html#"
					+ (bSet ? "set:": bGet || !bMethod ? "get:": "") +  info.substring(i + 1):
				info + _ext;
			return new Rendering(
				"http://rikulo.org/api/rikulo_" + pkg + "/" + clsnm,
				"<code>" + text + "</code>");
		} else if (url.startsWith("source:")) { //source: [name](source:path)
			String path = url.substring(7);
			if (path.length() > 0 && path.charAt(path.length() - 1) != '/')
				path += '/';
			return new Rendering(
				"https://github.com/rikulo/rikulo/tree/master/" + path + text,
				"<code>" + text + "</code>");
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
