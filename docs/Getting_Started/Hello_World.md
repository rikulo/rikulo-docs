#Hello World

This chapter shows you the basic elements of a Rikulo application by implementing a simple "Hello World" application.

A Rikulo application consists of

* At least one Dart file to hold the Dart code of your application
* At least one HTML page to load the Dart file(s)

> Please refer to [here](Introduction.md) for installing Rikulo to your application.

##The Dart Code

Here is a simple "Hello World" application.

    #import('client/app/app.dart');
    #import('client/view/view.dart');

    class HelloWorld extends Activity {
      void onCreate_() {
        TextView welcome = new TextView("Hello World!");
        welcome.profile.location = "center center";
        mainView.addChild(welcome);
      }
    }

    void main() {
      new HelloWorld().run();
    }

As shown above, there are basically four steps to implement a Rikulo application.

1. Import [app](api:) and [view](api:) packages
2. Implement your activity by extending from [Activity](api:app)
3. Draw your user interface in [Activity.onCreate_()](api:app).
4. Start up the activity in the `main` method

###Import [app](api:) and [view](api:)

Packages to import really depends on your requirement. [Activity](api:app) is part of the [app](api:) package, while UI objects is part of the [view](api:) package. These two are packages that you will import in the most cases.

###Implement an activity

An activity is an application component that provides the user interface to interact with the user. An activity might be paused or resumed due to user's interaction, such as answering a phone.

Implementing an activity is straightforward: extend your class from [Activity](api:app).

> The current activity can be found by use of the global variable called [activity](api:app).

###Draw your user interface

After your activity is started, [Activity.onCreate_()](api:app) will be called. You can create your user interface in this method.

> Naming Convention: we append an underscore to a method to indicate the method is *protected*, i.e., it can be accessed only within the class and its subclasses.

The user interface elements in a Rikulo application are built using [View](api:view). A view is the basic building block. It draws something on the screen that the user can interact with.

In this application, we instantiate an instance of [TextView](api:view) to show the greeting message.

    TextView welcome = new TextView("Hello World!");

We also specify the layout information in [View.profile](api:view), such that the message shall be placed in the center of the parent. The code is as follows. For more information, please refer to [the Layouts chapter](../Layouts/index.md).

    welcome.profile.location = "center center";

All views available in an activity are arranged in a single tree. The root view is called `mainView`. It is instantiated automatically before calling [Activity.onCreate_()](api:app). To make a view available on the screen, you have to add it to a node of the tree by invoking [View.addChild()](api:view).

    mainView.addChild(welcome);

![Tree of Views](view-hierarchy.jpg?raw=true)

###Start up your activity

The `main` method is Dart's entry point. All you need to do is to start up your activity by instantiating it and invoking [Activity.run()](api:app).

    new HelloWorld().run();

###Handle Events

The view will notify the application about the user's interaction with events. You can listen and handle the events with [View.on](api:view). For example, we can rewrite the "Hello World" application to change the greeting message when the user touches it as follows.

    welcome.on.click.add((event) {
      welcome.text = "Welcome to Rikulo.";
      welcome.requestLayout();
    }

> Notice that we also invoke [View.requestLayout()](api:view) to reposition the greeting message, since we change its content and need to reposition it to the center. For more information, please refer to [the Layouts chapter](../Layouts/index.md).

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

    <link rel="stylesheet" type="text/css" href="resources/css/view.css" />

Second, you have to specify your Dart file. In this application, it is called `HelloWorld.dart`.

    <script type="application/dart" src="HelloWorld.dart"></script>

Also notice that Rikulo assumes HTML 5, so you shall specify `<!DOCTYPE html>` at the beginning. In additions, it is, though optional, suggested to specify the `viewpoint` meta tag as follows, if you want to run the application on mobile devices:

    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />

Then, you visit this page to see how it works in live.

> In this example, we don't specify where to show the user interface, and Rikulo will, by default, insert it under the document's `body` tag. However, depending on your requirement (such as in a desktop applications), you can run several Rikulo applications at the same time and assign them to different segments on the screen. Please refer to [Make Activity Being Part of HTML Page](../Views/Activity.md) for details.
