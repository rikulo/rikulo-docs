#UI Overview

The basic building block of the user interface in a Rikulo application is [View](api:view). A View occupies a rectangular area on the browser and is responsible for drawing and event handling.

Rikulo provides a collection of [View](api:view) subclasses that offer predefined features, such as input controls and grids.

##View Hierarchy

The user interface for each element of your application is defined using a hierarchy of [View](api:view), as shown below. Unless overridden by the subclass, every view can have any number of the child views.

![Tree of Views](view-hierarchy.jpg?raw=true)

To draw the user interface, you can instantiate [View](api:view) and its subclasses, and then compose them into a hierarchy tree of views. This hierarchy tree can be as simple or complex as you need it to be. For example,

    final ScrollView view = new ScrollView();
    view.profile.text = "location: center center; width: 80%; height: 80%";
    view.classes.add("v-dialog");

    for (int x = 0; x < 30; ++x) {
      for (int y = 0; y < 30; ++y) {
        View child = new View();
        final String color = CSS.color(250 - x * 4, 250 - y * 4, 200);
        child.style.cssText = "border: 1px solid #553; background-color: $color";
        child.left = x * 50 + 2;
        child.top = y * 50 + 2;
        child.width = child.height = 46;
        view.addChild(child);
      }
    }

As shown above, the composing of a hierarchy tree is done by use of [View.addChild()](api:view).

##Show View on the Browser

When a view is instantiated, it is just like any ordinary object and has no effect on the user interface. To show a view on the browser, you can add it to [document](dart:html). It can be done by invoking [View.addToDocument()](api.view) against the root view of a hierarchy of views. For example,

    new View()
      ..layout.type = "linear"
      ..addChild(new TextView("Name"))
      ..addChild(new TextBox())
      ..addToDcument(); //make it available to the browser

> Notice that we interchangeably use *screen*, *browser*, and *document* to represent the same thing: the visual area of the device that the user interact with.

To remove a view from a hierarchy tree of views, you can use [View.removeFromParent()](api:view). It also means the view will be detached from the browser, if it was attached.

    view.removeFromParent(); //detach the given view from the hierarchy tree

By default, [View.addToDocument()](api:view) will check if any element ([Element](dart:html)) is assigned with id called `v-main`. If found, the views will be inserted into the element. If not found, the views will be inserted right under `document.body`.

##Attach views under a particular element

If you want to attach a hierarchy of views to a particular element, you can specify the element as the first argument. For example, assume you want want to put it under an element named `part`, you can do as follows.

    view.addToDocument(document.query("#part"));

For more information, please refer to [Embed in HTML Page](../Views/Fundamentals/Embed_in_HTML_Page.md).

###Attach views as a dialog

A dialog is a user interface that limits the user from accessing other user interfaces except the hierarchy views. It is useful if you want the user to enter something before proceed. To do so, you can specify `mode: "dialog"` when calling [View.addToDocument](api:view), such as

    view.addToDocument(mode: "dialog");

Here is an example ([source code is available here](https://github.com/rikulo/rikulo/blob/master/test/TestDialog2.html)).

![Dialog](dialog.jpg?raw=true)

As shown, there is a *semi-transparent mask* to prevent the user from accessing user interfaces other than the given view.

##Position

The geometry of a view is that of a rectangle. A view has a location, expressed as a pair of left and top coordinates, and two dimensions, expressed as a width and a height.

As shown in the previous example, you can position a view to any location by setting the `left` and `top` properties. You can change the dimensions by setting the `width` and `height` properties.

    child.left = x * 50 + 2;
    child.top = y * 50 + 2;

The coordinates of a view is related to the left-top corner of its parent view. When a view is moved, all of descendant views are moved accordingly. For example, if the `left` property of a view is 100, the view will be positioned at 100 pixels from the left border of the parent view. 

###Border Width

If a view has a border, the coordinates of the child views will start from the inner corner (i.e., the right edge of the left border, and the bottom edge of the top border). For example, if the border's with is 5, then the coordinates will be 5 pixel away from the view's the left-top corner without the border.

The width and height of a view includes the border. Thus, if the border's width is 5, then the inner space to draw is 10 pixel smaller.

> You can add additional offset to the coordinate of child views, such that you have more space to put other user interfaces, such as a toolbar. Please refer to [the viewport example](source:example) for details.

##Layout

In additions to managing the coordinates and dimensions manually, Rikulo provides [anchor layout](../../Layouts/Anchor_Layout.md), [linear layout](../../Layouts/Linear_Layout.md) and others to position the views automatically. It is useful if you are targeting different devices with different resolutions. For example, if the user change the orientation of his tablet, all the layout will be re-positioned automatically to fit into the different aspect ratio.

Yet another example: if you don't specify the width and height, they will be calculated automatically to best fit the view.

Yet another example: you can *anchor* a view to its parent to particular position and with particular ratio of dimensions:

    view.profile.text =
      "anchor: parent; location: center center; width: 80%; height: 80%";

For more information, please refer to [the Layouts chapter](../../Layouts/index.md).

##Relation with DOM Element

To show itself on the browser, a view is built with one or multiple DOM elements, depending on the complexity that the view offers.

> Rikulo is aimed to encapsulate the complexity from the application developers. You can skip this section if you'd like.

To save the memory, the DOM element(s) of a hierarchy of views won't be created until [View.addToDocument()](api:view) of its root view has been called (aka., attached). Once attached, you can retrieve the DOM element(s) that represents the view on the browser by use of [View.node](api:view).

    print("${view.node.id}");

Notice that you can access [View.node](api:view) only if the view has been attached to the browser. Otherwise, it will throws an exception.

To know if a view is attached to the browser, you can check [View.inDocument](api:view).

    if (view.inDocument) //the view is attached (and then accessible by the user)
      doSomething();

If you'd like to learn the details of how to develop a view, please refer to [View Development](../../View_Development).
