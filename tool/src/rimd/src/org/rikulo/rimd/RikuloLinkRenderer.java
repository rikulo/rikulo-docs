//Copyright (C) 2012 Potix Corporation. All Rights Reserved.
//History: Sat, July 07, 2012
// Author: tomyeh
package org.rikulo.rimd;

import org.pegdown.LinkRenderer;
import org.pegdown.ast.ExpLinkNode;

public class RikuloLinkRenderer extends LinkRenderer {
	final Processor _proc;
	RikuloLinkRenderer(Processor proc) {
		_proc = proc;
	}
	public Rendering render(ExpLinkNode node, String text) {
		String url = node.url;
		boolean bApi = false, bDart = false;
		if (url.startsWith("source:")) {
		//source: [name](source:dir) or [name](source:lib:dir)
			final int i = url.indexOf(':', 7);
			final String lib = i >= 0 ? url.substring(7, i): null;
			String dir = url.substring(i >= 0 ? i + 1: 7);
			if (dir.length() > 0 && dir.charAt(dir.length() - 1) != '/')
				dir += '/';
			return new Rendering(
				(lib != null ? _proc.libsource.replace("{lib}", lib): _proc.source) + dir + text,
				"<code>" + text + "</code>");
		} else if ((bApi = url.equals("api:")) || (bDart = url.equals("dart:"))
		|| url.endsWith(":")) {
		//package: [view](api:) or [html](dart:) or (el_impl)(el:)
			final String lib = !bApi && !bDart ? url.substring(0, url.length() - 1): null;
			final String urlPrefix = bApi ? _proc.api: bDart ? _proc.dartapi:
				_proc.libapi.replace("{lib}", lib);
			return new Rendering(urlPrefix + text.replace('/', '_') + ".html",
				"<code>" + text + "</code>");
		} else if ((bApi = url.startsWith("api:")) || (bDart = url.startsWith("dart:"))
		|| url.indexOf(':') > 0) {
		/* Link to a class: [ViewConfig](api:view/impl) or [CSSStyleDecalration](dart:html)
		* Link to a method: [View.requestLayout()](api:view)
		* Link to a getter: [View.width](api:view) or [View.width](api:view:get)
		* Link to a setter: [View.width](api:view:set)
		* Link to a global variable: [rootViews](api:view)
		*/
			int iColon = 0;
			final String lib = !bApi && !bDart ? url.substring(0, iColon = url.indexOf(':')): null;
			final String urlPrefix = bApi ? _proc.api: bDart ? _proc.dartapi:
				_proc.libapi.replace("{lib}", lib);
			boolean bGet = url.endsWith(":get"), bSet = !bGet && url.endsWith(":set");
			if (bGet || bSet)
				url = url.substring(0, url.length() - 4);
			final String pkg = url.substring(bApi ? 4: bDart ? 5: iColon + 1).replace('/', '_');

			int i = text.lastIndexOf('(');
//			final boolean bMethod = i >= 0;
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
					mtd = mtd.indexOf('=') > 0 ? "[]=": "[]";
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
					+ mtd + (bSet ? "=": ""):
				info + _proc.extension;
			return new Rendering(urlPrefix + pkg + "/" + clsnm,
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
