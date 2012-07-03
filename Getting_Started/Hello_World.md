#Hello World

This chapter shows you the basic elements of a Rikulo application by implementing a simple "Hello World" application.

##Installing Rikulo

To install Rikulo, you have to copy the `client` and `resources` folders to your application.

The `client` folder contains the Rikulo source code that you have to import to your application.  The `resource` folder contains the JavaScript and CSS files that you have to add to your HTML page.

##The Dart Code

Here is a simple "Hello World" application.

    ```dart
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
    ```

As shown above, there are basically four steps to implement a Rikulo application.

1. Import the `app` and `view` packages
2. Implement your activity by extending from [Activity](http://rikulo.org/api/_/Activity.html)
3. Instantiate UI in the `onCreate_` callback.
4. Start up the activity in the `main` method

###Import `app` and `view`

Packages to import really depends on your requirement. `Activity` is part of the `app` package, while UI objects is part of the `view` package. These two are packages that you will import in the most cases.

###Implement an activity

An activity is an application component that provides the user interface to interact with the user.