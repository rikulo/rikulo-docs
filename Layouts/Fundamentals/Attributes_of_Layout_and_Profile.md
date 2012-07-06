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

Syntax: `width: #n | content | flex | flex #n | #n %`  
Syntax: `height: #n | content | flex | flex #n | #n %`

It specifies the default width (and height) for each child view. It is not the width (and height) for the view that `layout` is assigned to. To specify the width and height, use the `profile` property instead.

For more information, please refer to [the profile property](#profile_width) below.

###spacing and gap

Syntax: `spacing: #n1 [#n2 [#n3 #n4]]`  
Default: 2

Syntax: `gap: #n1 [#n2]`  
Default: *empty* (i.e., dependong on spacing)

The spacing attribute specifies the spacing in pixel around the child view. It can be overridden by the child view's profile.

If only a number is specified, it means all four spacing is the same as the given value. If two numbers are specified, the first number specified the top and bottom spacing, while the second the left and right spacing. If all four numbers are specified, they specify the top, right, bottom, left spacing, respectively.

If the spacing at the left and at the right is different, the horizontal spacing of two adjacent views is the maximal value of them. Similarly, the vertical spacing is the maximal value of the spacing at the top and at the bottom.

![Spacing](spacing.jpg?raw=true)

If you prefer a different value, specify it in the gap attribute. The gap attribute specifies the spacing between two adjacent child views.

![Spacing with gap](spacing2.jpg?raw=true)

##The profile property

<a id="profile_width"></a>
##width and height

Syntax: `width: #n | content | flex | flex #n | #n %`  
Syntax: `height: #n | content | flex | flex #n | #n %`

It specifies the width (and height) of the given view.

If not specified and its parent's layout is specified with width (and height), the width (and height) specified in the parent's layout will be used. If neither is specified, it will use [View's width](http://rikulo.org/api/_/rikulo_view/View.html#get:width) if it was assigned a value by the application. If it is not assigned either, it is assumed to be `content`.

Notice that it also means that the width (and height) set in the profile property has the higher priority than [the width property]((http://rikulo.org/api/_/rikulo_view/View.html#set:width)).
