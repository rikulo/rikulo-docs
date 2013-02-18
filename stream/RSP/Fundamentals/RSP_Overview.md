#RSP Overview

RSP (Rikulo Stream Page) is a technology that helps developers create dynamically generated web pages based on HTML, XML or other text documents. A RSP page is a text document that contains two types of text: static data, which can be expressed in any text-based format (such as HTML, CSS, and XML), and [RSP tags](../Standard_Tags) which generate the dynamic content.

> Notice that the dynamic content is generated at the server side.

##Example

Here is a JSP page:

    [dart]
    part of hello_template;
    [/dart]
    <!DOCTYPE html>
    <html>
      <head>
        <title>Stream: Hello Templates</title>
        <link href="theme.css" rel="stylesheet" type="text/css" />
      </head>
      <body>
        <h1>Stream: Hello Templates</h1>
        <p>Now is [=new DateTime.now()].</p>
        <p>This page is served by Rikulo Stream [=connect.server.version].</p>
        <p>Please refer to
      <a href="https://github.com/rikulo/stream/tree/master/example/hello-template">Github</a> for how it is implemented.</a>
      </body>
    </html>

where [[dart]](../Standard_Tags/dart.md) and [[= ...]](../Standard_Tags/=.md) are [RSP tags](../Standard_Tags). They generate the dynamic content, while the rest are static data that are output directly.

##How it Works

![How RSP works](how-rsp-works.jpg?raw=true)

Each RSP page will be compiled into a request handler and put into a Dart file. The recommended file extension for a RSP page is `.rsp.html` (or `.rsp.xml`, depending on the file type), and the generated Dart file will be named by changing the file extension to `.rsp.dart`.

You can control the name of the request handler and additional arguments by use of [[page]](../Standard_Tags/page.md). If omitted, the file name will be assumed.

##How to Compile

There are two ways to compile RSP files into dart files: automatic building with Dart Editor or manual compiling.

###Build with Dart Editor

To compile your RSP files automatically, you just need to add a `build.dart` file in the root directory of your project, with the following content:

    import 'package:stream/rspc.dart';
    void main() {
      build(new Options().arguments);
    }

With this `build.dart` script, whenever your RSP is modified, it will be re-compiled.

###Compile Manually

To compile a RSP file manually, run `rspc` (RSP compiler) to compile it into the dart file with [command line interface](http://en.wikipedia.org/wiki/Command-line_interface) as follows:

    dart bin/rspc.dart your-rsp-file(s)

A dart file is generated for each RSP file you gave.

##Put Together

The generated Dart files shall be part of your Dart server application. There are basically two ways to do it. First, you can make it *part of* a library. It can be done by use of [[dart]](../Standard_Tags/dart.md) as follows:

    [dart]
    part of some_of_your_library;
    [/dart]
    ...

Then, you can include the generated Dart file in your library.

Second, you can make it an independent library. It can be done by use of [[dart]](../Standard_Tags/dart.md) as follows:

    [dart]
    library your_rsp_library;

    import "package:stream/stream.dart";
    [/dart]

Then, you can import it in your main library. Notice that you have to import the stream package as shown above.
