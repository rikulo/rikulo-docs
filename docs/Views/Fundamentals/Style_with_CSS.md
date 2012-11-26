#Style with CSS

##Assign Style directly

You can assign the CSS style to change the look of a view.

    view.style.backgroundColor = "blue";
    view.style.cssText = "font-size: 15px; text-align: center";

[View.style](api:view) is an instance of [CSSStyleDeclaration](dart:html).

### Don't Assign CSS Margin

Don't assign [the CSS margin](http://www.w3schools.com/css/css_margin.asp) to the view, since it will introduce an offset to the view's coordinates (and then might cause the layout manager positions it wrongly).

On the other hand, it is OK to use [the CSS padding](http://www.w3schools.com/css/css_padding.asp), and it doesn't have any effect to the coordinate of the child views.

> Of course, you can use the CSS margin in [the web content hold in TextView](Show_Web_Content.md). You can use it in the implementation of a view too. For example, you might implement a view by representing it with multiple DOM elements and you can assign any CSS styles, including margin, position and dimension, to these elements as what you need.

##Assign CSS Classes

You can assign any number of CSS classes to views.

    view.classes.add("v-dialog");

By default, when a view is instantiated, two special CSS classes are assigned. One is called `"v-"`, which defines the basic rules for a view. The other is named with the view's Dart class, and defines the look of views. For example, it is called `"v-Switch"` for [Switch](api:view), and `"v-TextView"` for [TextView](api:view). These CSS classes provide the default theme of a Rikulo application. You can customize them as you need it.

In additions, you can remove the default CSS classes too. The default styling of the view will be gone (and probably not what you want):

    switch.classes.remove("v-Switch"); //the switch look will be lost!

> The prefix, `"v-"`, can be customized by setting [ViewConfig](api:view/impl)'s `classPrefix` property.

##Package CSS Rules in a File

In general, it is better to package CSS rules in a file, since it isolates the code from the UI design. Furthermore, you can specify the CSS file(s) in the HTML pages, such that you can customize the look without modifying any Dart code. For example,

    <!DOCTYPE html>
    <html>
      <head>
        <link rel="stylesheet" type="text/css" href="foo.css" />
    ...

##Package CSS Rules in the Style View

If you want to add CSS rules in Dart code, you can use [Style](api:view). It is a special UI object used to define CSS rules. For example,

    new Style(content: '''
    .blue {
      background: blue; color: white
    }
    ''').addToDocument();

In additions, you can load an external CSS file to include a collection of CSS rules as follows.

    new Style(src: "foo.css").addToDocument();
