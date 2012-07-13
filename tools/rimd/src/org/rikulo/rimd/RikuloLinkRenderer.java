//Copyright (C) 2012 Potix Corporation. All Rights Reserved.
//History: Sat, July 07, 2012
// Author: tomyeh
package org.rikulo.rimd;

import org.pegdown.LinkRenderer;
import org.pegdown.ast.ExpLinkNode;

public class RikuloLinkRenderer extends LinkRenderer {
	final String _api, _source, _dart, _ext;
	RikuloLinkRenderer(String api, String source, String ext) {
		_api = api;
		_dart = api.substring(0, api.lastIndexOf('/') + 1);
		_source = source;
		_ext = ext;
	}
	public Rendering render(ExpLinkNode node, String text) {
		String url = node.url;
		boolean bApi = false;
		if ((bApi = url.equals("api:")) || url.equals("dart:")) {
		//package: [view](api:) or [html](dart:)
			final String urlPrefix = bApi ? _api: _dart;
			return new Rendering(urlPrefix + text.replace('/', '_') + ".html",
				"<code>" + text + "</code>");
		} else if ((bApi = url.startsWith("api:")) || url.startsWith("dart:")) {
		/* Link to a class: [ViewConfig](api:view/impl) or [CSSStyleDecalration](dart:html)
		* Link to a method: [View.requestLayout()](api:view)
		* Link to a getter: [View.width](api:view) or [View.width](api:view:get)
		* Link to a setter: [View.width](api:view:set)
		* Link to a global variable: [activity](api:app)
		*/
			final String urlPrefix = bApi ? _api: _dart;
			boolean bGet = url.endsWith(":get"), bSet = !bGet && url.endsWith(":set");
			if (bGet || bSet)
				url = url.substring(0, url.length() - 4);
			final String pkg = url.substring(bApi ? 4: 5).replace('/', '_');

			int i = text.lastIndexOf('(');
			final boolean bMethod = i >= 0;
			final String info = i >= 0 ? text.substring(0, i): text;
			i = info.indexOf('.');
			boolean bVar = i < 0 && Character.isLowerCase(text.charAt(0));
			if (bVar)
				return new Rendering(urlPrefix + pkg + ".html#" + info,
					"<code>" + text + "</code>");

			String mtd = i >= 0 ? info.substring(i + 1).trim(): null;
			boolean bOp = mtd != null && mtd.startsWith("operator");
			if (bOp) {
				if (mtd.indexOf('[') > 0)
					mtd = mtd.indexOf('=') > 0 ? ":setindex": ":index";
				else if (mtd.indexOf("==") > 0)
					mtd = ":eq";
				else if (mtd.indexOf('+') > 0)
					mtd = ":add";
				else if (mtd.indexOf('-') > 0)
					mtd = ":sub";
				else if (mtd.indexOf('*') > 0)
					mtd = ":mul";
				else if (mtd.indexOf('/') > 0)
					mtd = ":div";
				else
					bOp = false;
			}
			final String clsnm = i >= 0 ?
				info.substring(0, i) + ".html#"
					+ (bSet ? "set:": bGet || (!bMethod && !bOp) ? "get:": "") +  mtd:
				info + _ext;
			return new Rendering(urlPrefix + pkg + "/" + clsnm,
				"<code>" + text + "</code>");
		} else if (url.startsWith("source:")) { //source: [name](source:path)
			String path = url.substring(7);
			if (path.length() > 0 && path.charAt(path.length() - 1) != '/')
				path += '/';
			return new Rendering(_source + path + text,
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
