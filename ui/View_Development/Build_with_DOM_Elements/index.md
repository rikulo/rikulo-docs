#Build with DOM Elements

##View and DOM

A [View](api:view) is made up of one or multiple DOM elements. In other words, a view utilizes HTML 5 and CSS 3 to display the content to meet the requirement. In additions, the view interacts with the user by listening the DOM event sent by the DOM elements.

> Though rarely, you can draw the content in the fully customized way with [CanvasElement](dart:html) too.

The structure of the associated DOM elements are usually simple, such [View](api:view), [TextView](api:view) and [ScrollView](api:view), while some can be complex, such as [Switch](api:view).

It all depends on the requirement and whether HTML + CSS matches what you need. In additions, the top element can be found by use of [View.node](api:view).

Here is an example.

![Scroll view and associated DOM](scrollViewVsDOM.jpg?raw=true)

As shown, [ScrollView](api:view) is made of two DIV elements, and the DOM elements of the child views are placed in the inner DIV element. On the other hand, [TextBox](api:view) is made of one INPUT element, and [Button](api:view) made of one BUTTON element.

To interact with the user, [ScrollView](api:view) will listen the touch and mouse event for handling the scrolling.

> [ScrollView](api:view) doesn't listen the DOM event directly. Rather, it leverages [the gesture](../../Gestures/Fundamentals.md) called [Scroller](api:gesture) for easy implementation.

The view is a thin layer on top of [View.node](api:view). Many of [View](api:view) API is a proxy of the underlying [View.node](api:view), such as [View.id](api:view) and [View.style](api:view). Here is a list of differences.

* Two CSS classes are always assigned: `v-` and `v-clsnm` (where `clsnm` is the view's class name [View.className](api:view)).
    * The `v-` CSS class has two important CSS rules that you shall not change: `box-sizing: border-box;` and `position: absolute;`
    * The `v-clsnm` is used to customize the look of a given type of views.
* The position and dimension of a view (i.e., left, top, with and height) can only be measured in pixels. It doesn't support `em` and other units of measurement.

##Build a View with DOM Elements

1. [Extend from View or Its Subclasses](Extend_from_View_or_Its_Subclasses.md)
2. [Render DOM Elements](Render_DOM_Elements.md)
3. [Handle DOM Events](Handle_DOM_Events.md)
4. [An Example](An_Example.md)
5. [Advanced Topics](Advanced_Topics.md)
