#UI Overview

The user interface elements in a Rikulo application are built using [View](http://rikulo.org/api/_/rikulo_view/View.html). A view is the basic building block. It occupies a rectangular area on the screen, draws something on it, and interact with the user. 

Rikulo provides a collection of [View](http://rikulo.org/api/_/rikulo_view/View.html) subclasses that offer predefined features, such as input controls and grids.

##View Hierarchy

The user interface for each element of your application is defined using a hierarchy of [View](http://rikulo.org/api/_/rikulo_view/View.html), as shown below. Unless overriden by the subclass, every view can have any number of the child views.

![Tree of Views](view-hierarchy.jpg?raw=true)

To draw the user interface, you can instantiate [View](http://rikulo.org/api/_/rikulo_view/View.html) and subclasses and then build them to a hierarchy tree of views. This hierarchy tree can be as simple or complex as you need it to be. For example,

    final ScrollView view = new ScrollView();
    view.profile.text =
      "anchor: parent; location: center center; width: 80%; height: 80%";
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

##Show View on the Screen

###Attach to `mainView`

When a view is instantiated, it is just like any ordinary object and has no effect on the user interface. To show a view on the screen, you can add it to a node of the main view (`mainView`) of [the current activity](../Activity.md). For example,

    activity.mainView.addChild(new TextView.html("<h1>Impacts</h1>")); //attach to mainView

> `activity` is a global variable referencing to the current activity.

To know if a view is attached to the screen, you can check the `inDocument` property.

    if (view.inDocument) //the view is attached (and then accessible by the user)
      doSomething();

> Notice that we interchangeablely use *screen* and *document* to represent the same thing: the visual area of the device that the user interact with.

> Also notice that when Activity's `onCreate_` is called, `mainView` is *not* attached to the screen yet. You have to wait until another callback named `onMount_` is called.

To remove a view from displaying on the screen, you can detach it by use of `removeFromParent`, if it has a parent.

    view.removeFromParent(); //detach the given view from the hierarchy tree

You can also replace the whole main view with a hierarchy of tree too. Furthermore, the previous main view will be detached.

    activity.mainView = fooView; //replace the current mainView with fooView

###Attach to a dialog of the current activity

In additions to the main view, you can make a view (more precisely, a hierarchy tree) to be a dialog of the current activity:

    View fooDialog = new View();
    fooDialog.addChild(new TextView("A dialog sample"));
    activity.addDialog(fooDialog); //make fooDialog as a dialog shown on top of mainView

After called, `fooDialog` will be attached to the screen, and it will be shown on top of `mainView`. Since it is attached, any child added to it will be attached too.

    fooDialog.addChild(new TextView('Also attached to the screen'));

To remove a dialog, you can invoke the `removeDialog` method. The last added dialog will be detached.

    activity.removeDialog(); //detach the last added dialog

> [Activity](http://rikulo.org/api/_/rikulo_app/Activity.html) has one main view, `mainView`, and any number of dialogs. The last added dialog will be displayed on top of the main view and other dialogs. For more information, please refer to [the Activity chapter](../Activity.md) for details.

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

In additions to managing the coordinates and dimensions manually, Rikulo provides the anchored layout, linear layout and others to position the views automatically. It is useful if you are targeting different devices with different resloutions. For example, if the user change the orientation of his tablet, all the layout will be re-positioned automatically to fit into the different aspect ratio.

Yet another example: if you don't specify the width and height, they will be calculated automatically to best fit the view.

Yet another example: you can *anchor* a view to its parent to particular position and with particular ratio of dimensions:

    view.profile.text =
      "anchor: parent; location: center center; width: 80%; height: 80%";

For more information, please refer to [the Layouts chapter](../../Layouts/index.md).

##Relation with DOM Element

To show itself on the screen, a view is built with one or multiple DOM elements, depending on the complexity that the view offers. However, Rikulo is aimed to encapulate the complexity from the application developers. You generally don't need to know much about it.

If you want, you can retrieve the DOM element that represents the view on the screen by use of the `node` property.

    view.node.nodes();

Notice that you can access the `node` property only if the view is attached to the screen, i.e., the  `inDocument` property is true. Otherwise, it will throws an exception.

Also notice that when Activity's `onCreate_` is called, `mainView` is not attached to the screen yet. It means you can't access the `node` property in `onCreate_`. To access it, you can do in the `onMount_` method. For example, the `context2D` property of [Canvas](http://rikulo.org/api/_/rikulo_view/Canvas.html) depends on `node`, so we have to access it in `onMount_`:

    class Foo extends Activity {
      CanvasRenderingContext2D ctx2d;
      void onCreate_() {
        Canvas canvas = new Canvas();
        canvas.profile.text = "width: flex;height: flex";
        mainView.addChild(canvas);
      }
      void onMount_() { //mainView has been attached to the screen
        ctx2d = canvas.context2D; //it can be called only if attached
      }
    }

If you'd like to learn the details, please refer to [the View Development chapter](../../View_Development/index.md).
