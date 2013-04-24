#ID Space and Query

##CSS-Selector-based Query

[View.query()](api:view) and [View.queryAll()](api:view) allow you to look up view(s) based on CSS selector. For example,

    view.query("#foo"); //look for the view whose ID is foo
    view.queryAll("TextBox:firstChild"); //look for all TextView(s) that are the first child
    view.queryAll(".foo Switch.v-small"); //look for all Switch(es) with the given CSS classes

##ID Space

ID is used to identify a view uniquely, such that the application can retrieve it back easily with [View.query()](api:view) (such as `view.query("#id1")`) or [View.fellows](api:view).

> Notice that you can skip this section unless you want to assign the same ID to several views in the given user interface.

In a sophisticated application, the user interface is usually decomposed into several modules, such as dialogs and sidebars. Individual module is then designed separately. In additions, the same module can be used repeatedly to form a sophisticated UI. Therefore, it is tedious, if not impossible, to maintain the uniqueness of all IDs across all these modules for a sophisticated UI.

The concept of ID space is hence introduced. An ID space is a subset of a hierarchy tree. Within a given ID space, all ID, if ever assigned, must be unique. On the other hand, the same ID can be assigned to multiple views if they are not in the same ID spaces.

> Unlike DOM, the ID of any view in a given ID space must be unique. The rule was introduced to minimize the chance to retrieve the wrong view accidentally. It also speeds up the performance of retrieving a view.

By default, the whole hierarchy is considered as an ID space. You can make a branch of the tree as another ID space. Thus, you can control the uniqueness in the given branch without worrying if there is any conflict with other part of the tree (aka., the user interface).

To make a branch as another ID space, you can put a view that implements [IDSpace](api:view) at the top of the branch. We call the branch starting from this view is an ID space, and the view is called the space owner. [Section](api:view) is a typical example implementing [IDSpace](api:view). You can use it to partition a hierarchy tree into several ID spaces.

![ID Spaces](idspace.jpg?raw=true)

As shown above, let us assume `View A` and `View E` both implement [IDSpace](api:view). Then, they both form an ID space separately that includes their descendant views. The ID space of `View A` includes `A`,  `B`, `C`, `D` and `E. The Id space of `View E` includes `E`, `F` and `G`. Notice that `View E` belongs to both ID spaces.

    A.query("#D"); //D found
    A.query("#E"); //E found
    A.query("#E #F"); //F found
    A.query("#E").query("#G"); //G found
    A.query("#F"); //nothing found (return null)
    A.query("#A"); //A found

###Implements Your Own ID Space Owner

To make a view as the space owner, all it needs to do is to implement as follows:

    class FooView extends View implements IDSpace {
      @override
      final Map<String, View> fellows = new HashMap();
    ...

As shown above, you have to override [View.fellows](api:view).
