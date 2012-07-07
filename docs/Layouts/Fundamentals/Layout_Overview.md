#Layout Overview

While you can specify how to position and size views as complex as you need it, there are common patterns of structuring the user interface, such as placing adjacent views horizontally or positioning a view at the bottom of another.

To simplify the structuring, Rikulo provides several layouts to arrange the user interface based your declaration rather than code. You can use them, customize them, or implement your own.

##Specify the Layout

First, you can specify how to arrange the appearance of the child views of a given view by use of the `layout` property. For example, the following statement tells the layout to organizes the child views of `foo` along the vertical direction.

    foo.layout.text = "type: linear; orient: vertical";

The type specifies which layout to use, while the other parameters specify the details, such as orientation, spacing and so on.

>A layout is an object implementing the [Layout](api:layout) interface. The type specified in `layout` actually refers to one of the layouts managed by the layout manager ([LayoutManager](api:layout)).

The `layout` property is actually an instance of [LayoutDeclaration](api:layout). Thus, you can specify the layout in type-safe way, if you like. The following is equivalent to the previous snippet.

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

![Layout Example 1](layout-ex1.jpg?raw=true)

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

![Layout Example 2](layout-ex2.jpg?raw=true)

###Profile without Layout

Though `profile` is designed to work with the parent view's `layout`, it is OK to use `profile` alone to specify the width and height. For example,

    view.profile.width = "flex"; //take the full space of its parent
    view.profile.height = "content"; //best bit for its content

It is very useful with the so-called [Anchor Layout](Anchor Layout).

##Nested Layout

You can nest one layout into another and so on to create sophisticated layouts. The layout will be processed from the root view recursively into the leaf views. For example, we can place the horizontal linear layout inside the vertical linear layout to create a grid:

    void onCreate_() {
      mainView.layout.text = "type: linear; orient: vertical";

      for (final String type in
      ["text", "password", "multiline", "number", "date", "color"]) {
        View view = new View();
        view.layout.text = "type: linear; spacing: 0 3"; //top and bottom: 0 since nested
        mainView.addChild(view);

        TextView label = new TextView(type);
        label.style.textAlign = "right";
        label.profile.width = "70";
        view.addChild(label);

        TextBox input = new TextBox(type: type);
        view.addChild(input);
      }
    }

![Nested Layout](layout-ex-nested.jpg?raw=true)

##Force to Redo the Layout of a View

The layout will be handled automatically when the application is initialized, the browser's size is changed, and a view's visibility is changed. On the other hand, it doesn't detect and redo the layout, if the change of a view will affect the layout. It is the application to job to notify the change. To do so, you can invoke the `requestLayout` method of the view whose content is changed. For example, assume `textView` is an instance of [TextView](api:view), then we can have its layout to be handled after changing the content:

    textView.html = "<ul><li>This is new content</li></ul>";
    textView.requestLayout(); //schedule textView for handling the layout later

The layout will be handled immediately. Rather, they are queued, optimized and handled later. For better control you can pass the parameters if necessary.

When the layout of a view is handled, the layout of all of its descendant views will handled too. So, it might have some performance penalty for a complicated layout. Basically, the performance is better if you specify the width and height explicitly (with precise numbers), such that there is no need to calculate the dimension (which could be costly).

> Of course, if the view is positioned by the application (by setting the coordinates and dimensions explicitly), you don't have to call `requestLayout`. Rather, you might want to adjust the dimension by yourself.

##Fine-tune Position by Listening the `layout` event

After the layout has done, the `layout` event is fired. It is the moment you can adjust the layout in code if necessary. For example,

    View view = new View();
    view.style.backgroundColor = "#ddb";
    view.profile.anchor = "parent";
    view.profile.location = "center center";
    view.profile.width = "70%";
    view.profile.height = "80%";
    view.on.layout.add((event) {
      TextView txt = new TextView("onLayout: A child at 10%, 10%");
      txt.style.border = "1px solid #663";
      txt.left = view.width ~/ 10;
      txt.top = view.height ~/ 10;
      txt.on.mount.add((event2) {
        TextView txt2 = new TextView("onMount: another child at 20%, 20%");
        txt2.style.border = "1px solid #663";
        txt2.left = view.width ~/ 5;
        txt2.top = view.height ~/ 5;
        view.addChild(txt2);
      });
      view.addChild(txt);
    });
    mainView.addChild(view);
