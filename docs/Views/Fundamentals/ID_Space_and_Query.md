#ID Space and Query

##CSS-Selector-based Query

[View.query()](api:view) and [View.queryAll()](api:view) allow you to look up view(s) based on CSS selector. For example,

    view.query("#foo"); //look for the view whose ID is foo
    view.queryAll("TextBox:firstChild"); //look for all TextView(s) that are the first child
    view.queryAll(".foo Switch.v-small"); //look for all Switch(es) with the given CSS classes

##ID Space

ID is used to identify a view uniquely, such that the application can retrieve it back easily with [View.query()](api:view) or [View.fellows](api:view).

On the other hand, the user interface is usually decomposed into several modules, such as dialogs and sidebars. Individual module is then designed separately. In additions, the same module can be used repeatedly to form a sophisticated UI. Therefore, it is tedious, if not impossible, to maintain the uniqueness of all IDs across all these modules for a sophisticated UI.

The concept of ID space is hence introduced. An ID space is a subset of a hierarchy tree. Within a given ID space, all ID, if ever assigned, must be unique. On the other hand, the same ID can be assigned to multiple views if they are not in the same ID spaces.

You can, though optional, make a module an ID space. Thus, you can control the uniqueness in the given module without worrying if there is any conflict with other modules.

> Unlike DOM, any ID in a given ID space must be unique. The rule was introduced to minimize the chance to retrieve the wrong view accidentally.

If a view implements [IdSpace](api:view), we call the branch starting from the view is an ID space, and the view is the spa ce owner. [Section](api:view) is a typical example implementing [IdSpace](api:view). You can use it to partition a hierarchy tree into several ID spaces.

![ID Spaces](idspace.jpg?raw=true)

As shown above, let us assume `View A` and `View E` both implement [IdSpace](api:view). Then, they both form an ID space separately that includes their descendant views. The ID space of `View A` includes `A`,  `B`, `C`, `D` and `E. The Id space of `View E` includes `E`, `F` and `G`. Notice that `View E` belongs to both ID spaces.

    A.query("#D"); //D found
    A.query("#E"); //E found
    A.query("#E #F"); //F found
    A.query("#E").query("#G"); //G found
    A.query("#F"); //nothing found (return null)
    A.query("#A"); //A found
