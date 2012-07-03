#Hello World

This chapter shows you the basic elements of a Rikulo application by implementing a simple "Hello World" application.

##Installing Rikulo

To install Rikulo, you have to copy the `client` and `resources` folders to your application.

The `client` folder contains the Rikulo source code that you have to import to your application.  The `resource` folder contains the JavaScript and CSS files that you have to add to your HTML page.

> You can install Rikulo with [the Dart Package Manager](http://www.dartlang.org/docs/pub-package-manager/), and the package name is suggested to start with `rikulo:`, such as  `rikulo:app` and `rikulo:html`.

##The Dart Code

Here is a simple "Hello World" application.

    #import('client/app/app.dart');
    #import('client/view/view.dart');

    class HelloWorld extends Activity {
      void onCreate_() {
        TextView welcome = new TextView("Hello World!");
        welcome.profile.text = "anchor:  parent; location: center center";
        mainView.addChild(welcome);
      }
    }

    void main() {
      new HelloWorld().run();
    }

As shown above, there are basically four steps to implement a Rikulo application.

1. Import the `app` and `view` packages
2. Implement your activity by extending from [Activity](http://rikulo.org/api/_/app/Activity.html)
3. Draw your user interface in the `onCreate_` callback.
4. Start up the activity in the `main` method

###Import `app` and `view`

Packages to import really depends on your requirement. `Activity` is part of the `app` package, while UI objects is part of the `view` package. These two are packages that you will import in the most cases.

###Implement an activity

An activity is an application component that provides the user interface to interact with the user. An activity might be paused or resumed due to user's activities, such as answering a phone.

Implementing an activity is straightforward: extend your class from [Activity](http://rikulo.org/api/_/app/Activity.html).

> The current activity can be found by use of the global variable called `activity`.

###Draw your user interface

After your activity is started, the `onCreate_` method will be called. You can create your user interface in this method.

The user interface elements in a Rikulo application are built using [View](http://rikulo.org/api/_/view/View.html). A view is the basic building block. It draws something on the screen that the user can interact with.

In this application, we instantiate an instance of [TextView](http://rikulo.org/api/_/view/TextView.html) to show the greeting message.

    TextView welcome = new TextView("Hello World!");

We also specify the layout information in the `profile` property, such that the message shall be placed in the center of the parent. It is done by specifying `anchor` as parent, and `location` as `center center`.

    welcome.profile.text = "anchor: parent; location: center center";

All views available in an activity are arranged in a single tree. The root view is called `mainView`, and is instantiated automatically before calling `onCreate_`. To make a view available on the screen, you have to add it the tree rooted at `mainView` by invoking the  `addChild` method.

    mainView.addChild(welcome);

![Tree of Views](view-tree.jpg)

###Start up your activity

The `main` method is Dart's entry point. All you need to do is to start up your activity by instantiating it and invoking the `run` method as shown above.

##The HTML page

To run an application, you need a HTML page to specify the Dart file(s) to load. Here is a typical example:

    <!DOCTYPE html>
    <html>
      <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
        <link rel="stylesheet" type="text/css" href="resources/css/view.css" />
      </head>
      <body>
        <script type="application/dart" src="HelloWorld.dart"></script>
        <script src="resources/js/dart.js"></script>
      </body>
    </html>

First, you have to specify the CSS file to load. Depending on your requirement, you can specify any files you want, as long as including `view.css` (or a customized version of it).

Second, you have to specify your Dart file. Here is called `HelloWorld.dart`.

Also notice that Rikulo assumes HTML 5, so you shall specify `<!DOCTYPE html>` at the beginning. In additions, it is, though optional, suggested to specify the `viewpoint` meta tag if you want to run it on mobile devices:

    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />

Then, you visit this page to see how it works in live.

> In this example, we don't specify where to show the user interface, and Rikulo will insert it under the document's `body` tag. However, depending on your requirement (such as  desktop applications), you can run several Rikulo applications at the same time and assign them to different segments on the screen.