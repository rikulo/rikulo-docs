#Embed in HTML Page

By default, [View.addToDocument()](api:view) will add the view to `document.body`. In other words, it assumes the view can occupy anywhere in the whole browser. It is common for a simple or mobile Web applications. 

On the other hand, a Web application usually has a header, a footer and even several banners. They are usually (and better) implemented in pure HTML and CSS, since they are static and can be implemented by a UI designer.

To do so, you can implement the HTML page with static HTML tags (or any server-side technology, such as JSP and PHP), and then embed the Rikulo views into particular location(s) for handling the rich user interfaces. For example,

![Embed in HTML page](embedInHTMLPage.png?raw=true)

> You can embed any number of Rikulo views into different parts of the HTML page, depending on your requirement

##The HTML Page

To embed views into the HTML page, you have to declare a DOM element where Rikulo views will be placed. To identify the DOM element in Dart code, you can assign an unique ID to it. Here is an example,

    <!DOCTYPE html>
    <html>
      <head>
        <link rel="stylesheet" type="text/css" href="packages/rikulo_ui/resource/css/default/view.css" />
        <link rel="stylesheet" type="text/css" href="your.css"/>
      </head>
      <body>
        <div id="header>Your Header Here</div>
        <div id="container">
          <div id="sidebar">Your Sidebar Here</div>
          <div id="main"><!-- Rikulo view will be embedded here --></div>
        </div>
        <div id="footer">Your Footer Here</div>
        <script type="application/dart" src="Your.dart"></script>
        <script src="packages/browser/dart.js"></script>
      </body>
    </html>

As shown above, we assign the DOM element with an unique ID called `main` that will be used in the Dart application to identify the DOM element.

> Like other HTML pages, you have to specify `view.css`, the dart file, and `dart.js`.

###Dimension of the Element

It is important to notice that you *have to* assign the dimension to the DOM element. For example,

    #main {
      width: 600px;
      min-height: 500px;
    }

Rikulo will take the dimension you assigned to limit where the view can occupy.

##The Dart Code

In the Dart code, you can identify the DOM element to add the views as follows.

    view.addToDocument(ref: document.query("#main"));

The view will be then limited the given DOM element. For example, `view.profile.location = "center center"` will place the view at the center of the DOM element.

> You can add different hierarchy of views to different DOM elements. Here is a more complicated example: [TestEmbed.dart](source:test).

###`v-main`

If the DOM element is not given, [View.addToDocument()](api:view) will look for any DOM element called `v-main` first, and then, if not found, `document.body`.

With this special id, your application can run on different layout without modification. For instance, if the HTML page doesn't have this DOM element, Rikulo views will take over the whole browser. If the DOM element is found, Rikulo views is limited to it.
