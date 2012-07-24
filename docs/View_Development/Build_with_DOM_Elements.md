#Build with DOM Elements

##View and DOM

A [View](api:view) is made up of one or multiple DOM elements. In other words, a view utilizes HTML 5 and CSS 3 to display the content to meet the requirement.

The structure of the associated DOM elements are usually simple, such [View](api:view), [TextView](api:view) and [ScrollView](api:view), while some can be complex, such as [Switch](api:view). It all depends on the requirement and whether HTML + CSS matches what you need.

> Though rarely, you can draw the content in the fully customized way with [the Canvas element](http://api.dartlang.org/html/CanvasElement.html) too.

Here is an example.

![Scroll view and associated DOM](scrollViewVsDOM.jpg?raw=true)

As shown, [ScrollView](api:view) is made of two DIV elements, and the DOM elements of the child views are placed in the inner DIV element. On the other hand, [TextBox](api:view) is made of one INPUT element.

##When to Create DOM Elements

##How to Create DOM Elements

###Generate the HTML fragment in [View.draw()](api:view)

####Override [View.domTag_](api:view) and [View.domInner_()](api:view) if single DOM element

####The id attribute of the DOM elements

###Initial DOM elements in [View.mount_()](api:view)

###Clean up DOM elements in [View.unmount_()](api:view)

##Other Methods to Override

###[View.className](api:view)

###[View.isViewGroup()](api:view)

###[View.insertChildToDocument_()](api:view)
