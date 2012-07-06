#Attributes of Layout and Profile

Here we discuss the important attributes of the `layout` and `profile` properties of [View](http://rikulo.org/api/_/rikulo_view/View.html). For a complete list, please refer to [LayoutDeclaration](http://rikulo.org/api/_/rikulo_view/LayoutDeclaration.html) and [ProfileDeclaration](http://rikulo.org/api/_/rikulo_view/ProfileDeclaration.html), respectively.

##The layout property

###type

Syntax: `type: none | linear | stack | tiles | table`  
Default: an empty string (i.e., `none`)

It specifies the type of the layout to use. It must be one of layouts managed by [LayoutManager](http://rikulo.org/api/_/rikulo_layout/LayoutManager.html).

If not specified (i.e., `none`), the position of the child views won't be arranged. However, the dimensions (width and height) will, if you don't specify it explicitly.

> Currently, only none and linear are available

###width and height

###spacing and gap

##The profile property
