#Override Inherited Methods

Here are a list of methods of [View](api:view) that are worth to check when you implement a view.

##[View.className](api:view)

[View.className](api:view) returns the *tag* name in a CSS selector recognized by [View.query()](api:view) and [View.queryAll()](api:view). It is recommended to override it to return the class's name, so [View.query()](api:view) and [View.queryAll()](api:view) can work correctly. For example

    class FooView extends View {
      String get className() => "FooView";
    ...

Then, the user can search it by the class's name:

    view.query("FooView");

In additions, [View.View](api:view) (the constructor) will add a CSS class named `"v-$className"`, such that you customize the look easily.

##[View.isViewGroup()](api:view)

[View.isViewGroup()](api:view) returns whether the given view allows any child views. If true, it means the view is a view group, i.e., it can contain other views (child views). If false, Rikulo will throw an exception if the application tries to add a child view to it.

##[View.insertChildToDocument_()](api:view)

[View.insertChildToDocument_()](api:view) inserts the DOM element(s) of the given child view into the browser's DOM tree (aka., the document). For example, let us assume the view called `p` is already attached, then invoking `p.addChild(c)` will cause `p.insertChildToDocument_(c, html, null)` to be called implicitly.

The default implementation will insert the DOM element(s) under `p.node`. In other words, it assumes there is only one layer of DOM elements. If you'd like to put the child elements in a nested layers, you have to override this method. For example, assume the nested element to put the child elements is called `_nestedNode`, then you can do as follows

    void insertChildToDocument_(View child, var childInfo, View beforeChild) {
      if (beforeChild !== null)
        super.insertChildToDocument_(child, childInfo, beforeChild);
      else if (childInfo is Element)
        _nestedNode.$dom_appendChild(childInfo);
      else
        _nestedNode.insertAdjacentHTML("beforeEnd", childInfo);
    }

For a real example, you can refer to [the viewport example](https://github.com/rikulo/rikulo/tree/master/example/viewport).
