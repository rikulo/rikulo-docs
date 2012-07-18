#Show Web Content

You can use [TextView](api:view) embed web content in your application. To do so, you simply instantiate it with the `html` constructor and then attach it to the hierarchy tree of views.

    view.addChild(new TextView.html('''
      <ul>
      <li>Welcome to Rikulo</li>
      </ul>
    '''));

Furthermore, you can change the content dynamically by setting the `html` property.

    textView.html = "<table><tr><td>${data1}</td><td>${data2}</tr></table>";

> [TextView](api:view) can show a plain text, or a fragment of HTML content, depending on the constructor and the property you use.

##Replace Part of Web Content with Views

You can replace some of the web content shown in [TextView](api:view) with views. To do so, you have to put a special DOM element in the HTML fragment that you can insert the view into. Then, create a view and insert it to the special DOM element with the `addToDocument` method. In other words, the special DOM element acts as an anchor.

For example, let us say that we want to display a list of items with a switch at the right as depicted below.

![Embed Web that embeds View](embedWebEmbedView.jpg?raw=true)

Then, we can write the code as follows.

    void onCreate_() {
      final TextView webView = new TextView.html('''
        <ul style="line-height: 23px">
          <li>Structured Web Apps <span style="float:right"></span></li>
          <li>Structured UI Model <span style="float:right"></span></li>
        </ul>
        ''');
      webView.width = 250;
      mainView.addChild(webView);

      for (Element n in webView.node.queryAll("span"))
        new Switch(true).addToDocument(n, location: "right top");
    }

1. In this example, we use a SPAN element to indicate where to insert the view. It could be anything as long as it matches what you need.

    Here we set the style to `"float:right"` since we want the anchor to align at the right. Of course, it depends on what you need.

2. We then insert the view to the anchor (the SPAN element mentioned above) by use of [View.addToDocument](api:view). The `node` argument shall be the anchor element. In addtions, you can specify an addition argument called `location` to control where to align the view. Here we set it to `"right top"`, such that we can align at the right edge (since we style the anchor with `"float:right"`). Again, it is up to what you need.

        for (Element n in webView.node.queryAll("span"))
          new Switch(true).addToDocument(n, location: "right top");

    Notice that we have to do it after `webVeiw` has been added to [Activity.mainView](api:app), since the corresponding DOM elements of views are created only after they are attached to the hierarchy tree of [Activity.mainView](api:app).

    If the postion or dimension of the web view is handled by the layout (for example, it is part of a linear layout). Then, you can defer the insertion of views by listening to the `layout` event (such that the anchor's position is finalized).

        webView.on.layout.add((event) {
        ...//addToDocument
