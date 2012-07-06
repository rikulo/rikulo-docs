#Fundamentals of Layouts

While you can specify how to position and size views as complex as you need it, there are common patterns of structuring the user interface, such as placing adjacent views horizontally or positioning a view at the bottom of another.

To simplify the structuring, Rikulo provides several layouts to arrange the user interface based your declaration rather than code. You can use them, customize them, or implement your own.

##Specify the Layout

First, you can specify how to arrange the appearance of the child views of a given view by specifying it in the `layout` property. For example, the following statement tells the layout to organizes the child views of `foo` along the vertical direction.

    foo.layout.text = "type: linear; orient: vertical";

The type specifies which layout to use, while the other parameters specify the details, such as orientation, spacing and so on.

>A layout is an object implementing the [Layout](http://rikulo.org/api/_/rikulo_layout/Layout.html) interface. The type specified in `layout` actually refers to one of the layouts managed by the layout manager ([LayoutManager](http://rikulo.org/api/_/rikulo_layout/LayoutManager.html)).

The `layout` property is actually an instance of [LayoutDeclaration](http://rikulo.org/api/_/rikulo_rikulo_view/Layout.html). Thus, you can specify the layout in type-safe way, if you like:

    foo.layout.type = "lienar";
    foo.layout.orient = "vertical";

##Specify the Profile


##Fine-tune Position by Listening the `layout` event
