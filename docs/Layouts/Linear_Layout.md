#Linear Layout

Linear layout arranges the child views in a single column or a single row. You can set the direction by use of the orient attribute. You can also control the alignment, spacing and other attributes that can be found in [LayoutDeclaration](api:view) (the type of [View.layout](api:view)).

##Example 1: Header, Footer and Sidebar

Here is a typical application layout.

![Border Layout](borderlayout.jpg?raw=true)

    final rootView = new View()
      ..layout.text = "type: linear; orient: vertical; spacing: 0"
      ..addToDocument();

    View header = new View();
    header.style.cssText = "background: blue";
    header.profile.text = "width: flex; height: 30";
    rootView.addChild(header);

    View inner = new View();
    inner.layout.text = "type: linear; spacing: 0"; //default: horizontal
    inner.profile.text = "width: flex; height: flex";
    rootView.addChild(inner);

    View sidebar = new View();
    sidebar.style.cssText = "background: orange";
    sidebar.profile.text = "width: 20%; height: flex";
    inner.addChild(sidebar);

    View content = new View();
    content.profile.text = "width: flex; height: flex";
    inner.addChild(content);

    View footer = new View();
    footer.style.cssText = "background: green";
    footer.profile.text = "width: flex; height: 30";
    rootView.addChild(footer);

##Example 2: Input Form

![Input Form](inputform.jpg?raw=true)

    final rootView = new View()
      ..layout.text = "type: linear; orient: vertical"
      ..addToDocument();

    for (final String type in
    ["text", "password", "multiline", "number", "date", "color"]) {
      View view = new View();
      view.layout.text = "type: linear; align: center; spacing: 0 3";
        //top and bottom: 0 since nested
      rootView.addChild(view);

      TextView label = new TextView(type);
      label.style.textAlign = "right";
      label.profile.width = "70";
      view.addChild(label);

      view.addChild(type == "multiline" ? new TextArea(): new TextBox(null, type));
    }

##Samples

* [LinearLayoutDemo.dart](source:example/linear-layout)
* [InputSamples.dart](source:example/input)
