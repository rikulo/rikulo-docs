#Activity

* Package: [app](api:)
* API Reference: [Activity](api:app)
* Source: [Activity.dart](source:client/app/src)

##Compose UI

You can extend [Activity](api:app) and override [Activity.onCreate_](api:app) to
compose your UI and attach it to [Activity.mainView](api:app).

    class HelloWorld extends Activity {
      void onCreate_() {
        title = "Hello World!";
    
        TextView welcome = new TextView("Hello World!");
        welcome.profile.text = "anchor:  parent; location: center center";
        mainView.addChild(welcome);
      }
    }

In additions to add views to [Activity.mainView](api:app), you can replace it with your own:

    ScrollView main = new ScrollView();
    main.addChild(new TextView("whatever")); //construct a tree as you want
    mainView = main; //replace it

If you want to play an effect when replacing [Activity.mainView](api:app), you can use [Activity.setMainView](api:app) instead.

##Make Activity Being Part of HTML Page

By default, [Activity.mainView](api:app) will occupy the whole screen. In other words, it assumes it is the only UI element that the user will interact with.

However, sometimes you want it to occupy part of a HTML page. For example, you want to want an activity to take only a part of a blog. Or, you want to have multiple activities in the same HTML page and each of them serves a part of UI.

To do so, you can define an element in the HTML page (that loads the dart
application) and assign it an id called `v-main`. For example,

    <link rel="stylesheet" type="text/css" href="../../resources/css/view.css" />
    ...
    <div id="v-main" style="width:100%;height:200px"></div>
    ...
    <script type="application/dart" src="HelloWorld.dart"></script>
    <script src="../../resources/js/dart.js"></script>

The dimension is optional if you assign the width and height explicitly to the activity's main view ([Activity.mainView](api:app)).

If you want to embed multiple applications in the same HTML page, you can assign
the DOM elements with a different ID, and then invoke [run] with the ID you assigned to the DOM elements.

    <link rel="stylesheet" type="text/css" href="../../resources/css/view.css" />
    ...
    <div id="v-part1"></div>
    ...
    <div id="v-part2"></div>
    ..
    <script type="application/dart" src="Application1.dart"></script>
    <script type="application/dart" src="Application2.dart"></script>
    <script src="../../resources/js/dart.js"></script>
