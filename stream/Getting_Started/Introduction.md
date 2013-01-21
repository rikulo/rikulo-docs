#Introduction

Rikulo Stream is a Dart web server supporting URI mapping, template technology, file-based static resources and MVC design pattern.

##Install from Dart Pub Repository

To install Rikulo Stream from [Dart Pub Repostiory](http://pub.dartlang.org/packages/rikulo), you can add this to your `pubspec.yaml` (or create it):

    dependencies:
      stream:

Then, If you’re using [Dart Editor](http://www.dartlang.org/docs/editor/), select “Pub Install” from the “Tools” menu. If you’re rocking the command line, do:

    pub install

For more information, please refer to [Pub: Getting Started](http://pub.dartlang.org/doc).

##Install from Github for Bleeding Edge Stuff

To get the latest features that haven't been formally released yet, you can specify the Git repository in your `pubspec.yam` as follows.

    dependencies:
      stream:
        git: git://github.com/rikulo/stream.git

For more information, please refer to [Pub: Dependencies](http://pub.dartlang.org/doc/pubspec.html#dependencies).

##Configure Dart Editor to Compile Templates Automatically

[RSP (Rikulo Stream Page)](../RSP/Fundamentals/RSP_Overview.html) is the template technology allowing developers to create dynamically generated web pages based on HTML, XML or other document types.

To compile RSP files into Dart files automatically, you can add a build.dart file in the root directory of your project, with the following content:

    import 'package:stream/rspc.dart';
    void main() {
      build(new Options().arguments);
    }

> If you prefer to compile RSP files manually, you can run `rspc` (RSP compiler) with [a command-line interface](http://en.wikipedia.org/wiki/Command-line_interface) as follows:  
> `dart bin/rspc.dart your-rsp-file(s)`

##Explore Examples

To explore the examples, you can browser the source code directly on [Stream's Git repository here](https://github.com/rikulo/stream).

To run the examples, you can either [click here](https://github.com/rikulo/stream/zipball/master) to download the source code, or run `git` to clone the Git repository to your local drive as follows.

    git clone git://github.com/rikulo/stream.git

Then, you can start the server by running `main.dart` in the example you'd like to test. For example, you can start the *Hello MVC* example as follows.

    dart example/hello-mvc/webapp/main.dart

After started, you can visit [http://localhost:8080](http://localhost:8080) to see the example.

* [Hello Static Resources](https://github.com/rikulo/stream/tree/master/example/hello-static) demonstrates how to use file-based static resources.
* [Hello Dynamic Contents](https://github.com/rikulo/stream/tree/master/example/hello-dynamic) demonstrates how to generate dynamic contents in response to Ajax requests sent from a Dart program running at the client.
* [Hello Templates](https://github.com/rikulo/stream/tree/master/example/hello-template) demonstrates how to use RSP templates.
* [Hello MVC](https://github.com/rikulo/stream/tree/master/example/hello-mvc) demonstrates how to apply MVC design pattern.
