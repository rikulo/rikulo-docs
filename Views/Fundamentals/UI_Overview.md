#UI Overview

The user interface elements in a Rikulo application are built using [View](http://rikulo.org/api/_/view/View.html). A view is the basic building block. It occupies a rectangular area on the screen, draws something on it, and interact with the user. 

Rikulo provides a collection of [View](http://rikulo.org/api/_/view/View.html) subclasses that offer predefined features, such as input controls and grids.

##View Hierarchy

The user interface for each element of your application is defined using a hierarchy of [View](http://rikulo.org/api/_/view/View.html), as shown below. Unless overriden by the subclass, every view can have any number of the child views.

![Tree of Views](view-hierarchy.jpg?raw=true)

To draw the user interface, you can instantiate [View](http://rikulo.org/api/_/view/View.html) and subclasses and then build them to a hierarchy tree of views. This hierarchy tree can be as simple or complex as you need it to be. For example,

    final ScrollView view = new ScrollView();
    view.profile.text =
      "anchor: parent; location: center center; width: 80%; height: 80%";
    view.classes.add("scroll-view");

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

##Show View on the Screen

To show a view on the screen, you have to attach it to one of the hierarchy trees belonging to [the current activity](../Activity.md). For example,

    activity.mainView.addChild(new TextView.html("<h1>Impacts</h1>")); //attach to mainView
    activity.mainView = fooView; //replace the current mainView with fooView
    activity.addDialog(fooDialog); //attach fooDialog as a dialog shown on top of mainView

> [Activity](http://rikulo.org/api/_/app/Activity.html) has one main view, `mainView`, and any number of dialogs. The last added dialog will be displayed on top of the main view and other dialogs. For more information, please refer to [the Activity chapter](../Activity.md) for details.

To remove a view from displaying on the screen, you can detach it by use of `removeFromParent`.

    view.removeFromParent(); //detach the given view from the hierarchy tree
    activity.removeDialog(); //detach the last added dialog

To know if a view is attached to the screen, you can check the `inDocument` property.

    if (view.inDocument) //the view is attached
      doSomething();

> Notice that we interchangeablely use *screen* and *document* to represent the same thing: the visual area of the device that the user interact with.

##Position

The geometry of a view is that of a rectangle. A view has a location, expressed as a pair of left and top coordinates, and two dimensions, expressed as a width and a height.

As shown in the previous example, you can position a view to any location by setting the `left` and `top` properties. You can change the dimensions by setting the `width` and `height` properties.

    child.left = x * 50 + 2;
    child.top = y * 50 + 2;

The coorinates of a view is related to the left-top corner of its parent view. When a view is moved, all of descendant views are moved accordingly. For example, if the `left` property of a view is 100, the view will be positioned at 100 pixels from the left border of the parent view. 

###Border Width

If a view has a border, the coordinates of the child views will start from the inner corner (i.e., the right edge of the left border, and the bottom edge of the top border). For example, if the border's with is 5, then the cooridnates will be 5 pixel away from the view's the left-top corner without the border.

The width and height of a view includes the border. Thus, if the border's width is 5, then the inner space to draw is 10 pixel smaller.

###Inner Offset

Some view subclasses might allow you to define the offset of the coordinates by setting the `innerLeft` and `innerTop` properties. In other words, `innerLeft` and `innerTop` specify the offset from the left-top corner. You can explore [the viewport sample](https://github.com/rikulo/rikulo/tree/master/samples/viewport).

> Notice that View and most subclasses don't allow you to change them, though it is straightforward to extend them to support it.

##Layout

In additions to managing the coordinates and dimensions manually, Rikulo provides the anchored layout, linear layout and others to position the views automatically. It is more convenient if you are targeting different devices with different resloutions. For example, if the user change the orientation of his tablet, all the layout will be re-positioned automatically to fit into the different aspect ratio.

Yet another example: if you don't specify the width and height, they will be calculated automatically to best fit the view.

Yet another example: you can *anchor* a view to its parent to particular position and with particular ratio of dimensions:

    view.profile.text =
      "anchor: parent; location: center center; width: 80%; height: 80%";

For more information, please refer to [the Layouts chapter](../../Layouts/index.md).

##Style

Views support the CSS3 styling. Like a DOM element, you can assign a CSS class to it, or specify the style directly.

    view.classes.add("scroll-view");
    child.style.cssText = "border: 1px solid #553; background-color: $color";

By default, a CSS class named with the name of the Dart class with be assigned. For example, [Switch](http://rikulo.org/api/_/view/Switch.html) is assigned with `"v-Switch"` initially. These CSS classes provide the default theme of a Rikulo application. You can customize them as you need it.

> The prefix, `"v-"`, can be customized by setting [ViewConfig](http://rikulo.org/api/_/view/impl/ViewConfig.html)'s `classPrefix` property.

##Relation with DOM Element

To show itself on the screen, a view is built with one or multiple DOM elements, depending on the complexity that the view offers. However, Rikulo is aimed to encapulate the complexity from the application developers. You generally don't need to know about it.

If you'd like to learn the details, please refer to [the View Development chapter](../../View_Development/index.md).