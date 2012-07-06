#Style with CSS

##Assign Style directly

You can assign the CSS style to change the look of a view.

    view.style.backgroundColor = "blue";
    view.style.cssText = "font-size: 15px; text-align: center";

[View](http://rikulo.org/api/_/view/View.html)'s `style` is an instance of [CSSStyleDeclartion](http://api.dartlang.org/html/CSSStyleDeclaration.html). It will correct the CSS name with a proper prefix for the browser automatically. For example, the following statement will assign the value to `-webkit-transform` when running on Chrome.

    view.style.transform = "translate3d(-10px, -20px, 0)";

> We don't do the correction for the CSS rules specified in a CSS file, or in [Style](http://rikulo.org/api/_/view/Style.html).

### Don't Assign Coorinates and Dimensions

Notice that it is strongly suggested **not** to assign the coordinates and the dimensions to the `style` property. Rather, you shall assign them directly with the `left`, `top`, `width` and `height` properties of [View](http://rikulo.org/api/_/view/View.html). For example,

    view.style.left = "13px"; //Wrong!!
    view.left = 13; //Correct
    view.width = 200; //Correct
    view.profile.width = "flex"; //Correct (it will be handled by layout manager later)

##Assign CSS Classes

You can assign any number of CSS classes to views.

    view.classes.add("v-dialog");

By default, when a view is instantiated, two special CSS classes are assigned. One is called `"v-"`, which defines the basic rules for a view. The other is named with the view's Dart class, and defines the look of views. For example, it is called `"v-Switch"` for [Switch](http://rikulo.org/api/_/view/Switch.html), and `"v-TextView"` for [TextView](http://rikulo.org/api/_/view/Switch.html). These CSS classes provide the default theme of a Rikulo application. You can customize them as you need it.

In additions, you can remove the default CSS classes too. The default styling of the view will be gone (and probably not what you want):

    switch.classes.remove("v-Switch"); //the switch look will be lost!

> The prefix, `"v-"`, can be customized by setting [ViewConfig](http://rikulo.org/api/_/view/impl/ViewConfig.html)'s `classPrefix` property.

##Package CSS Rules in the Style View

[Style](http://rikulo.org/api/_/view/Style.html) is a special UI object used to hold CSS rules. You can assign CSS rules to directly.

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
