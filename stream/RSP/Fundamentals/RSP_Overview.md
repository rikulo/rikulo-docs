#RSP Overview

RSP (Rikulo Stream Page) is a template technology that helps developers create dynamically generated web pages based on HTML, XML or other text documents. A RSP page is a text document that contains two types of text: static data, which can be expressed in any text-based format (such as HTML, CSS, and XML), and [RSP tags](../Standard_Tags) which generate the dynamic content.

> Notice that the dynamic content is generated at the server side.

##Example

Here is a RSP page:

    [:page partOf="hello.rsp.dart"]
    <!DOCTYPE html>
    <html>
      <head>
        <title>Stream: Hello RSP</title>
        <link href="theme.css" rel="stylesheet" type="text/css" />
      </head>
      <body>
        <h1>Stream: Hello RSP</h1>
        <p>Now is [=new DateTime.now()].</p>
        <p>This page is served by Rikulo Stream [=connect.server.version].</p>
        <p>Please refer to
      <a href="https://github.com/rikulo/stream/tree/master/example/hello-rsp">Github</a> for how it is implemented.</a>
      </body>
    </html>

where [[:page]](../Standard_Tags/page.md) and [[= ...]](../Standard_Tags/=.md) are [RSP tags](../Standard_Tags). They generate the dynamic content, while the rest are static data that are output directly.

##How it Works

![How RSP works](how-rsp-works.jpg?raw=true)

Each RSP page will be compiled into a request handler and put into a Dart file. The recommended file extension for a RSP page is `.rsp.html` (or `.rsp.xml`, depending on the file type), and the generated Dart file will be named by changing the file extension to `.rsp.dart`.

You can control the name of the request handler and additional arguments by use of the [[:page]](../Standard_Tags/page.md) tag. If omitted, the filename will be assumed.

##How to Compile

There are two ways to compile RSP files into Dart files: automatic building with Dart Editor or manual compiling.

###Build with Dart Editor

To compile your RSP files automatically, you just need to add a `build.dart` file in the root directory of your project, with the following content:

    import 'dart:io' show Options;
    import 'package:stream/rspc.dart' show build;

    void main() {
      build(new Options().arguments);
    }

With this `build.dart` script, whenever your RSP is modified, it will be re-compiled automatically.

###Compile Manually

To compile a RSP file manually, you can run `rspc` (RSP compiler) to compile it into the Dart file with [command line interface](http://en.wikipedia.org/wiki/Command-line_interface) as follows:

    dart bin/rspc.dart your-rsp-file(s)

A Dart file is generated for each RSP file you gave.

##File Location

You can put RSP files (`*.rsp.*`) under any folder you'd like. The generated Dart files (`*.rsp.dart`) will be always generated under the `webapp` folder.

For example, let us say we put `foo.rsp.html` under the `abc` folder, then `foo.rsp.dart` will be generated under the `webapp/abc` folder. On the other hand, if it is already under the `webapp` folder (including its sub folders), the generated Dart file will be put into the same folder.

In general, it is easier to manage if we put them under the folder with other static resources, such as CSS and JS files (other than the `webapp` folder).

> Notice that RSP files can't be accessed directly from client, even if it is not put under the `webapp` folder. By default, they are mapped to [Http404](api:stream).

> Also notice that the folder of a RSP file resides has no effect how it behaves. What does matter is the generated render function and the URI it is mapped to.

##Put Together

The generated Dart files shall be part of your Dart server application. There are a few ways to do it.

###Generated as an Independent Library

By default, the generated Dart file is an independent library. All you need to do is to import it in your main program.

    //your_main.dart
    import 'abc/foo.dart';

In the generated Dart file, `dart:io`, `dart:async` and `package:stream/stream.dart` will be imported by default. If you'd like to import others, you can specify them in the `import` attribute with the [[:page]](../Standard_Tags/page.md) tag. For example,

    [:page import="dart:async, dart:collection show HashMap"]
    ...

###Generated as Part of Another Library

Making every RSP file as an independent library can end up with too many libraries. It is usually more convenient to manage if you group several RSP files into a single library.

It can be done by making the generate Dart file as *part of* another library with the [[:page]](../Standard_Tags/page.md) tag as follows:

    [:page partOf="your_lib.dart"]
    ...

As shown, the `partOf` attribute specifies the path of the library file. In this example, it is `your_lib.dart`. RSP compiler will maintain `your_lib.dart` automatically. If it doesn't exists, it will be created. If exists, the `part` statement will be inserted to `your_lib.dart` if necessary.

Furthermore, you can specify the `import` attribute in the [[:page]](../Standard_Tags/page.md) tag too. The libraries specified in the `import` attribute will be updated to `your_lib.dart` too.

Alternatively, if you prefer to maintain the relationship among these Dart files manually, you can specify the library's name (rather than a file path) in the `partOf` attribute. For example,

    [:page partOf="one_of_your_library"]
    ...

Then, you can include the generated Dart file in your library (manually with the `part` statement in Dart).

    //your_lib.dart
    library one_of_your_library;
    part "your.rsp.dart";
