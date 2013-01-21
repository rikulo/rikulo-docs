#Hello World

Let's begin by implementing a tiny application that displays a welcome message.

##Create the Directories

First, let us create a directory named `helloworld`. All files for this application reside in this directory. It is also known as the *project* directory.

Inside the directory, create a directory named `webapp`. All your server-side Dart code reside in this directory. In additions, any files under `webapp` directory cannot be accessed directly from the clients.

> The application, on the other hand, can access, forward and include any files.

##Create the Main Program

Inside the `webapp` directory, we have to prepare a main program that starts the Stream server. Let us name it `main.dart` and give the following contents:

    import "package:stream/stream.dart";

    void main() {
      new StreamServer().run();
    }

##Create the Welcome Message

To display the welcome message, create a HTML file named `index.html`, put it right under the project directory (`helloworld`), and give it the following contents.

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

    dart helloworld/main.dart

The web server is now running, listening for requests on port 8080.  You can test the application by visiting the following URL in your web browser:

[http://localhost:8080/](http://localhost:8080/)
