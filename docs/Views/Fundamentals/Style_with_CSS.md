#Style with CSS

##Assign Style directly

You can assign the CSS style to change the look of a view.

    view.style.backgroundColor = "blue";
    view.style.cssText = "font-size: 15px; text-align: center";

[View](api:view)'s `style` is an instance of [CSSStyleDeclartion](http://api.rikulo.org/rikulo/latest/html/CSSStyleDeclaration.html). It will correct the CSS name with a proper prefix for the browser automatically. For example, the following statement will assign the value to `-webkit-transform` when running on Chrome.

    view.style.transform = "translate3d(-10px, -20px, 0)";

> We don't do the correction for the CSS rules specified in a CSS file, or in [Style](api:view).

### Don't Assign Coorinates and Dimensions with CSS

Notice that it is strongly suggested **not** to assign the coordinates and the dimensions to the `style` property. Rather, you shall assign them directly with the `left`, `top`, `width` and `height` properties of [View](api:view). For example,

    view.style.left = "13px"; //Wrong!!
    view.left = 13; //Correct
    view.width = 200; //Correct
    view.profile.width = "flex"; //Correct (it will be handled by layout manager later)

Moreover, it is suggested to use Rikulo's [layout facility](../../Layouts/index.md) to handle the postion and layout of the user interface. It is more convenient, and it will adjust automatically based on the resolution of the device.

### Don't Assign CSS Margin

Don't assign [the CSS margin](http://www.w3schools.com/css/css_margin.asp) to the view, since it will introduce an offset to the view's coorinates (and then might cause the layout manager  positions it wrongly).

On the other hand, it is OK to use [the CSS padding](http://www.w3schools.com/css/css_padding.asp), and it won't have any effect to the coordinate of the child views.

> Of course, you can use the CSS margin in [the web content hold in TextView](Show_Web_Content.md). You can use it in the implementation of a view too. For example, you might implement a view by representing it with multiple DOM elements and you can assign any CSS styles, including margin, position and dimension, to these elements as what you need.

##Assign CSS Classes

You can assign any number of CSS classes to views.

    view.classes.add("v-dialog");

By default, when a view is instantiated, two special CSS classes are assigned. One is called `"v-"`, which defines the basic rules for a view. The other is named with the view's Dart class, and defines the look of views. For example, it is called `"v-Switch"` for [Switch](api:view), and `"v-TextView"` for [TextView](api:view). These CSS classes provide the default theme of a Rikulo application. You can customize them as you need it.

In additions, you can remove the default CSS classes too. The default styling of the view will be gone (and probably not what you want):

    switch.classes.remove("v-Switch"); //the switch look will be lost!

> The prefix, `"v-"`, can be customized by setting [ViewConfig](api:view/impl)'s `classPrefix` property.

##Package CSS Rules in the Style View

[Style](api:view) is a special UI object used to hold CSS rules. You can assign CSS rules to directly.

    mainView.addChild(new Style.content('''
    .blue {
      background: blue; color: white
    }
    '''));

Or, you can load an external CSS file holding the CSS rules.

    mainView.addChild(new Style("foo.css"));

##Package CSS Rules in a File

In general, it is better to package CSS rules in a file, since it isolates the code from the UI design. Furthermore, you can specify the CSS file(s) in the HTML pages (so you don't have to touch the Dart code). For example,

    <!DOCTYPE html>
    <html>
      <head>
        <link rel="stylesheet" type="text/css" href="foo.css" />
    ...
