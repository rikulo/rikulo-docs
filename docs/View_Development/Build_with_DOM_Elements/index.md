#Build with DOM Elements

##View and DOM

A [View](api:view) is made up of one or multiple DOM elements. In other words, a view utilizes HTML 5 and CSS 3 to display the content to meet the requirement. In additions, the view interacts with the user by listening the DOM event sent by the DOM elements.

The structure of the associated DOM elements are usually simple, such [View](api:view), [TextView](api:view) and [ScrollView](api:view), while some can be complex, such as [Switch](api:view). It all depends on the requirement and whether HTML + CSS matches what you need.

> Though rarely, you can draw the content in the fully customized way with [the Canvas element](http://api.dartlang.org/html/CanvasElement.html) too.

Here is an example.

![Scroll view and associated DOM](scrollViewVsDOM.jpg?raw=true)

As shown, [ScrollView](api:view) is made of two DIV elements, and the DOM elements of the child views are placed in the inner DIV element. On the other hand, [TextBox](api:view) is made of one INPUT element, and [Button](api:view) made of one BUTTON element.

To interact with the user, [ScrollView](api:view) will listen the touch and mouse event for handling the scrolling.

> [ScrollView](api:view) doesn't listen the DOM event directly. Rather, it leverages [the gesture](../Gestures/Fundamentals.md) called [Scroller](api:gesture) for easy implementation.

##Build a View with DOM Elements

1. [Extend from View or Its Subclasses](Extend_from_View_or_Its_Subclasses.md)
2. [Render DOM Elements](Render_DOM_Elements.md)
3. [Implement Fields](Implement_Fields.md) to hold the states, if any
4. [Handle DOM Events](Handle_DOM_Events.md) to interact with user, if necessary
5. [Override Inherited Methods](Override_Inherited_Methods.md) for fine-grained control, if necessary
