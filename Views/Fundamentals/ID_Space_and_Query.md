#ID Space and Query

##CSS-Selector-based Query

The `query` and `queryAll` methods of [View](http://rikulo.org/api/_/view/TextView.html) allow you to look up view(s) based on CSS selector. For example,

    view.query("#foo"); //look for a view with ID = foo
    view.queryAll("TextBox:firstChild"); //look for all TextView which is the first child
    view.queryAll(".foo Switch.v-small"); //look  for all Switch with the specified CSS

##ID Space

ID is used to identify a view uniquely. However, it is common to decompose the user interface into several modules. Invidual model is usually designed seperatedly. The same module can be replicated into different parts of the user interface (aka., the macro view). Therefore, it is tedious, if not impossible, to maintain the uniquess of all IDs across all these modules for a sophisticated application.

The concept of ID space is hence introduced. An ID space is a subset of a hierarchy tree that all ID assigned are unique. With ID spaces, you can assign ID uniqueuely in one ID space (of a module) without worrying if there is any conflict with other modules.

> Unlike DOM, any ID in a given ID space must be unqiue in the whole ID space. The rule was introduced to minimize the chance to assign the same ID accidentaly to different views.

If a view implements [IdSpace](http://rikulo.org/api/_/view/IdSpace.html), we call the branch starting from the view is an ID space, and the view is the space owner. [Section](http://rikulo.org/api/_/view/Section.html) is a typical example implementing [IdSpace](http://rikulo.org/api/_/view/IdSpace.html). You can use it to partition a hierarchy tree into several ID spaces.

![ID Spaces](idspace.jpg?raw=true)

As shown above, let us assume `View A` and `View E` both implement [IdSpace](http://rikulo.org/api/_/view/IdSpace.html). Then, they both form an ID space seperatedly that includes their descendant views. The ID space of `View A` includes `A`,  `B`, `C`, `D` and `E. The Id space of `View E` includes `E`, `F` and `G`. Notice that `View E` belongs to both ID spaces.

    A.query("#D"); //D found
    A.query("#E"); //E found
    A.query("#E #F"); //F found
    A.query("#E").query("#G"); //G found
    A.query("#F"); //nothing found (return null)
    A.query("#A"); //A found
