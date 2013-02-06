#Introduction

##What is Rikulo

Rikulo is a cross-platform framework for creating amazing Web and native mobile applications
in Dart and HTML 5. You can access your application directly with a modern Web browser without
any plug-in. You can also build it as a native mobile application accessing the device's resources
transparently.

##Install from Dart Pub Repository

To install Rikulo from [Dart Pub Repostiory](http://pub.dartlang.org/packages/rikulo), you can add this to your `pubspec.yaml` (or create it):

    dependencies:
      rikulo_ui:

Then, If you’re using [Dart Editor](http://www.dartlang.org/docs/editor/), select “Pub Install” from the “Tools” menu. If you’re rocking the command line, do:

    pub install

For more information, please refer to [Pub: Getting Started](http://pub.dartlang.org/doc).

##Install from Github for Bleeding Edge Stuff

To get the latest features that haven't been formally released yet, you can specify the Git repository in your `pubspec.yam` as follows.

    dependencies:
      rikulo:
        git: git://github.com/rikulo/ui.git

For more information, please refer to [Pub: Dependencies](http://pub.dartlang.org/doc/pubspec.html#dependencies).

##Explore Examples

To explore the examples, you can browser the source code directly on [Rikulo's Git repository here](https://github.com/rikulo/ui).

To run the examples, you can either [click here](https://github.com/rikulo/ui/zipball/master) to download the source code, or run `git` to clone the Git repository to your local drive as follows.

    git clone git://github.com/rikulo/ui.git

Then, you can browser the HTML files under the `example` folder directly with [Dartium](http://www.dartlang.org/dartium/), or run them in [Dart Editor](http://www.dartlang.org/docs/editor/).

* [helloworld](source:example) - Hello, World!
* [free-layout](source:example) - A layout sample illustrating 21 built-in anchor points.
* [linear-layout](source:example) - A linear layout placing views vertically and horizontally.
* [custom-layout](source:example) - A simple sample app showing how to position views in any arbitary position.
* [input](source:example) - A list of inputs of different types.
* [scroll-view](source:example) - Three simple sample apps showing the use of [ScrollView](api:view), including a list view and a grid view.
* [viewport](source:example) - A simple sample app demostrating the implementation of a view supporting the *viewport*.
* [circle](source:example) - Two animation samples illustrated with views and convas, respectively.
* [snake](source:example) - Snake game showing the use of animation, canvas, drag-gesture and many more.
* [composite-view](source:example) - A sample illustrating how to implement a view with a composite of other views.
