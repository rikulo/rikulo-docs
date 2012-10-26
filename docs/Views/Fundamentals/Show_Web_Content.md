#Show Web Content

You can use [TextView](api:view) to display web content in your application. To do so, you simply instantiate it with the `fromHTML` constructor and then attach it to the hierarchy tree of views.

    view.addChild(new TextView.fromHTML('''
      <ul>
      <li>Welcome to Rikulo</li>
      </ul>
    '''));

Furthermore, you can change the content dynamically by setting the `html` property.

    textView.html = "<table><tr><td>${data1}</td><td>${data2}</tr></table>";

> [TextView](api:view) can show a plain text, or a fragment of HTML content, depending on the constructor (and the property) you use.

> Here we discuss how to display Web content in a view. If you embed a view into a part of a HTML page, please refer to [Embed in HTML Page](Embed_in_HTML_Page.md).

##Replace Part of Web Content with Views

In additions to displaying the static Web content, you can insert views into the static Web content shown in [TextView](api:view).

The approach is the same as [Embed in HTML Page](Embed_in_HTML_Page.md): define a DOM element in the HTML fragment that you can insert the view into. Then, insert the view you want into this DOM element by invoking [View.addToDocument()](api:view).

For example, let us say that we want to display a list of items with a switch at the right as depicted below.

![Embed Web that embeds View](embedWebEmbedView.jpg?raw=true)

Then, we can write the code as follows.

    final webView = new TextView.fromHTML('''
      <ul style="line-height: 23px">
        <li>Structured Web Apps <span style="float:right"></span></li>
        <li>Structured UI Model <span style="float:right"></span></li>
      </ul>
      ''')
      ..width = 250
      ..addToDocument();

    for (final node in webView.node.queryAll("span"))
      new Switch(true)
        ..profile.location = "right top"
        ..addToDocument(ref: node);

1. In this example, we use a SPAN element to indicate where to insert the view. It could be anything as long as it matches what you need. 

    Here we set the style to `"float:right"` since we want the SPAN element to align at the right. Of course, it depends on what you need. For example, you might prefer to set the width explicitly.

2. We then insert the view to the SPAN element by use of [View.addToDocument()](api:view). In additions, you can specify [View.profile](api:view) to control where to align the view. Here we set it to `"right top"`, such that we can align at the right-top corner edge (since we style the SPAN element with `"float:right"`). Again, it is up to what you need.

        for (final node in webView.node.queryAll("span"))
          new Switch(true)
            ..profile.location = "right top"
            ..addToDocument(ref: node);

    Notice that we have to do it after `webVeiw..addToDocument()` has been called. Otherwise, `webView.node` won't be ready.
