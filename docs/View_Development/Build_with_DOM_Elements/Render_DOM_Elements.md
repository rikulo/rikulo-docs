#Render DOM Elements

##Override [View.render_()](api:view)

Rendering the DOM elements is straightforward: override [View.render_()](api:view) to create and return them:

    Element render_() => new Element.tag("select");

You don't need to worry the child views. Their DOM elements will be created recursively if necessary, and composed based on the hierarchy of views.

###Handle DOM events

After rendering the DOM elements, you can initialize the DOM elements before returning. For example, you can register a DOM listener:

    Element render_() {
      final node = new Element.tag("input");
      node.on.change((event) {
        //do something
        });
      return node;
    }

For more information, please refer to [the Handle DOM Events section](Handle_DOM_Events.md).

###Access child elements

If the view is represented by a hierarchy of DOM elements, you can access a child element by use of [Element.query()](dart:html). For example,

    void render_() {
      final node = new Element.html('<div><div class="foo-button"></div></div>');
      node.query("div.foo-button").on.add((event) {
        //handle it
        });
      return node;
    }

However, be aware that a view might have child views of the same type. It means, your [Element.query()](dart:html) might return the wrong element if child views have been added (e.g., it might return a child view's element wrongly).

> The previous example is safe, since the child nodes are not added when [View.render_()](api:view) is called.

To avoid the conflict, you can name the id of the child element by prefixing [View.uuid](api:view) as follows.

    Element render_()
    => new Element.html('<div><div id="$uuid-inner"</div>');

[View.uuid](api:view) is unique in the whole browser (even if you run multiple Dart applications), so `id="$uuid-inner"` can uniquely identify the child element regardless how the user might organize the view hierarchy.

If your child element is identified in this way, you can then use [View.getNode()](api:view) to retrieve them. For example,

    Element get innerNode => getNode('inner');
      //'inner' shall match the suffix following $uuid-

It is a short cut of `node.query('#$uuid-inner')`.
