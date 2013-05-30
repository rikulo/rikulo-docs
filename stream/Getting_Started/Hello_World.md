#Hello World

Let's begin by implementing a tiny application that displays a welcome message.

##Create the Directories

First, let us create a directory named `helloworld`. All files for this application reside in this directory. It is also known as the *project* directory.

Inside the `helloworld` directory, create a directory named `web`. It will be the root directory for serving web pages. All Dart files and web resources reside in this directory.

Inside the `web` directory, create a directory named `webapp`. All your server-side Dart code reside in this directory. In additions, any files under `webapp` directory cannot be accessed directly from the clients.

> The application, on the other hand, can access, forward and include any files under `web`.

Here is the directory structure to create in this sample.

    hello-world/
      pubspec.yaml
      web/
        index.html
        webapp/
          main.dart

> Notice that the `web` directory is required since [pub](http://pub.dartlang.org/doc/package-layout.html) installed packages only under `web`, `test` and `example` directories.

> If you prefer to structure it differently, please refer to [Directory Structure](../Configuration/Directory_Structure.md).

##Create pubspec.yaml

Inside the `helloworld` directory, create a `pubspec.yaml` with the following contents:

    name: helloworld
    version: 1.0.0
    description: Hello World Sample Application
    dependencies:
      stream: any

Then, change to the `helloworld` directory, and run [pub install](http://pub.dartlang.org/doc/), either on the command line or through the Dart Editor menu: `Tools > Pub Install`.

##Create the Main Program

Inside the `webapp` directory, we have to prepare a main program that starts the Stream server. Let us name it `main.dart` and give the following contents:

    import "package:stream/stream.dart";

    void main() {
      new StreamServer().start();
    }

##Create the Welcome Message

To display the welcome message, create a HTML file named `index.html`, put it right under the `web` directory, and give it the following contents.

    <!DOCTYPE html>
    <html>
      <head>
        <title>Hello World!</title>
      </head>
      <body>
        <h1>Welcome to Stream!</h1>
      </body>
    </html>

##Start the Application

You can start the application by invoking the main program as follows:

    dart web/webapp/main.dart

The web server is now running, listening for requests on port 8080.  You can test the application by visiting the following URL in your web browser:

[http://localhost:8080/](http://localhost:8080/)
