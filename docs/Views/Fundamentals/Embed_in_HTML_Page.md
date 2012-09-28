#Embed in HTML Page

By default, [Activity.mainView](api:app) will occupy the whole screen. In other words, it assumes the main view is the only UI element that the user will interact with.

However, for a Web application, it is common to have a header, a footer and even several banners. They are usually better to be done in pure HTML and CSS since they are static and can be implemented by a designer who might not be familiar with Dart.

To do so, you can implement with HTML (or any server-side technology, such as JSP) and then embed Rikulo views to the place that rich UI is required. For example,

![Embed in HTML page](embedInHTMLPage.png?raw=true)

> Of course, you can embed any number of Rikulo views into different parts of the HTML page, depending on your requirement

##The HTML Page

To embed views into the HTML page, you have to declare an element where the rich UI will take place (while the content really depends what you want). To access the element in Dart, an unique ID shall be assigned. Here is an example,

    <!DOCTYPE html>
    <html>
      <head>
        <link rel="stylesheet" type="text/css" href="packages/rikulo/resource/css/view.css" />
        <link rel="stylesheet" type="text/css" href="your.css"/>
      </head>
      <body>
        <div id="header>Your Header Here</div>
        <div id="container">
          <div id="sidebar">Your Sidebar Here</div>
          <div id="v-main"><!-- Rikulo view will be embedded here --></div>
        </div>
        <div id="footer">Your Footer Here</div>
        <script type="application/dart" src="Your.dart"></script>
        <script src="packages/rikulo/lib/resource/js/dart.js"></script>
      </body>
    </html>

As shown above, we assign the element with an unique ID called `v-main` that will be used in the Dart application to identify the element.

> Like other HTML pages, you have to specify `view.css`, the dart file, and `dart.js`.

###Dimension of the Element

The CSS position of every Rikulo view is default to absolute. Thus, if you don't assign the dimension to the element, it might overlap with the other elements, such as footers. To avoid the overlapping, you can specify in CSS, such as:

    #main {
      width: 600px;
      min-height: 500px;
    }

##The Dart Code

If you assign `v-main` as the element's ID, you don't need to modify your Dart application. In other words, the application can be run on different layout without modification.

However, if you prefer to assign a different ID to the element, you have to specify the same ID explicitly when calling [Activity.run](api:app). For example, if the ID is called `part1`, then you can do as follows:

    void main() {
      new FooActivity().run("part1");
    }

It is useful if you have multiple Dart applications to replace multiple parts of the HTML page.