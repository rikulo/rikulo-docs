#Anchor Layout

Anchor layout is used to place a view based on the offset of another view. Unlike all other layouts, it doesn't depend on the `layout` property of the parent view. Rather, it is specified by use of the anchor and location attributes of [ProfileDeclaration](http://rikulo.org/api/_/rikulo_view/ProfileDeclaration.html) (the type of [View's profile](http://rikulo.org/api/_/rikulo_view/View.html#set:profile)).

##Anchor

The profile's anchor attribute specifies the *so-called* anchor view. For example,

    foo.profile.text = "anchor: parent";

Then, the position of the given view, `foo` in the previous example, will depend on the anchor view, `foo.parent` in the previous example. How the given view is exactly positioned depends on another attribute called `location`. Please refer to the next section for details.

The anchor view must be the parent or one of the sibling views of the given view. You can specify the object directly with `anchorView` as follows.

    foo.profile.anchorView = foo.previousSibling;

You can specify a CSS selector too. Again, it must be the parent or one of its sibling views.

    foo.profile.anchor = "#$someId";

##Location

You can specify how the given view is positioned related to the anchor view by use the location attribute. For example, the following statement positioned `foo` at the center of its parent.

    foo.profile.text = "anchor: parent; location: center center";

The value can be one of `"north start"`, `"north center"`, `"north end"`, `"south start"`, `"south center"`, `"south end"`, `"west start"`, `"west center"`, `"west end"`, `"east start"`, `"east center"`, `"east end"`, `"top left"`, `"top center"`, `"top right"`, `"center left"`, `"center center"`, `"center right"`, `"bottom left"`, `"bottom center"`, and `"bottom right"`. If not speicified, `"top left"` is assumed.

Here is the locations of all these values specify.

![Location](location.jpg?raw=true)

As shown above, the border doesn't count when positioning a view based on the given location.

> Rikulo is smart enough to detect the keywords, so `"north start"` is the same as `"start north" and so on.

##Anchor Layout Nested in Other Layout

As described at the beginning, Anchor Layout is independent of the layout property specified in the parent view. It also means the parent view will ignore them when handling the layout specified in the layout property. In other words, Anchor Layout has the higher priority than other layouts.

For example, in the following example, `view1` will be positioned to the left-top corner of `hlayout`, while `view2` will be the first view of the linear layout of `hlayout`.

    View hlayout = new View();
    hlayout.layout.type = "linear";

    View view1 = new View();
    view1.profile.anchor = hlayout; //anchor layout
    hlayout.addChild(view1);

    View view2 = new View(); //handled by linear layout
    hlayout.addChild(view2);
