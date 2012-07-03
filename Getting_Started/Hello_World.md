#Hello World

This chapter shows you the basic elements of a Rikulo application by implementing a simple "Hello World" application.

##The Dart Code

First, you have to copy the `client` and `resources` folders to your application. The `client` folder contains the Rikulo source code that you have to import to your application.  The `resource` folder contains the JavaScript and CSS files that you have to add to your HTML page.

Then, you can create the application by extending from [Activity](http://rikulo.org/api/Activity.html) as follows.

```dart
#import('../../client/app/app.dart'); //import Activity
#import('../../client/view/view.dart'); //import TextView

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
