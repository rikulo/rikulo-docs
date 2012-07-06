#Fundamentals of Layouts

While you can specify how to position and size views as complex as you need it, there are common patterns of structuring the user interface, such as placing adjacent views horizontally or positioning a view at the bottom of another.

To simplify the structuring, Rikulo provides several layouts to arrange the user interface based your declaration rather than code. You can use them, customize them, or implement your own.

##Specify the Layout

First, you can specify how to arrange the appearance of the child views of a given view by use of the `layout` property. For example, the following statement tells the layout to organizes the child views of `foo` along the vertical direction.

    foo.layout.text = "type: linear; orient: vertical";

The type specifies which layout to use, while the other parameters specify the details, such as orientation, spacing and so on.

>A layout is an object implementing the [Layout](http://rikulo.org/api/_/rikulo_layout/Layout.html) interface. The type specified in `layout` actually refers to one of the layouts managed by the layout manager ([LayoutManager](http://rikulo.org/api/_/rikulo_layout/LayoutManager.html)).

The `layout` property is actually an instance of [LayoutDeclaration](http://rikulo.org/api/_/rikulo_rikulo_view/Layout.html). Thus, you can specify the layout in type-safe way, if you like. The following is equivalent to the previous snippet.

    foo.layout.type = "lienar";
    foo.layout.orient = "vertical";

Here is a complete example:

    View hlayout = new View();
    hlayout.layout.text = "type: linear; width: 100; height: 70; spacing: 10";
    mainView.addChild(hlayout);

    View view = new View();
    view.style.cssText = "background: yellow; border: 1px solid black";
    hlayout.addChild(view);

    view = new View();
    view.style.cssText = "background: blue; border: 1px solid black";
    hlayout.addChild(view);

Here is the result:

![Layout Example 1](layout-ex1.jpg)

Of course, you can nest one layout into another and so on to create sophisticated layouts.

> If the width or height is not specified, it implies *content*, i.e., it shall take the exactly the size that the content requires.

##Specify the Profile

In additions, you can specify how an individual view shall be arranged by use of the `profile` property. For example, the following statement indicates `fooChild1`'s width shall take as much as it can, i.e., specifying `flex`.

    fooChild1.profile.text = "width: flex;";

In other words, the `layout` property specifies how to arrange the child views, while the `profile` property specifies how to arrange an individual (child) view. For example, you can specify `layout` to align child views at the top, and specify one of the child to align at the bottom.

Here is another example: arrange a linear layout with three child views, and the last child view takes the rest of the space as follows.

    View hlayout = new View();
    hlayout.layout.text = "type: linear; width: 100; height: 70; spacing: 10";
    hlayout.profile.width = "flex";
    mainView.addChild(hlayout);

    View view = new View();
    view.style.cssText = "background: yellow; border: 1px solid black";
    hlayout.addChild(view);

    view = new View();
    view.style.cssText = "background: blue; border: 1px solid black";
    hlayout.addChild(view);

    view = new View();
    view.style.cssText = "background: orange; border: 1px solid black";
    view.profile.width = "flex";
    hlayout.addChild(view);

Here is the result. You can try to resize the browser, and you'll find the layout changed automatically to take over the full space (i.e., what `flex` means).

![Layout Example 2](layout-ex2.jpg)

###Profile without Layout

Though `profile` is designed to work with the parent view's `layout`, it is OK to use `profile` alone to specify the width and height. For example,

    view.profile.width = "flex"; //take the full space of its parent
    view.profile.height = "content"; //best bit for its content

It is very useful with the so-called [Anchored Layout](Anchored Layout).

##Fine-tune Position by Listening the `layout` event
