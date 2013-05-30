#Directory Structure

Rikulo Stream allows you to organize the directory structure of your application any way you like.

##The Root Directory of the Application

The root directory is assumed to the parent directory of the `webapp` directory if it exists. Otherwise, it is assumed to be the directory where the main Dart file executes.

For example, if you execute `dart /foo1/web/myapp.dart`, the root is assumed to be `/foo1/web`. On the other hand, if executing `dart /foo2/web/app2/webapp/myapp.dart`, then the root is `/foo2/web/app2`.

###The `web` Directory

It is worth to notice that you have to put your application under a directory called `web`, since [pub](http://pub.dartlang.org/doc/package-layout.html) installs packages only under the `web`, `test` and `example` directories.

##Default Directory Structure

Since Rikulo Stream will detect the `webapp` directory automatically, the easiest way to organize your application is:

1. Put server-side Dart files and resources under the `webapp` directory.
2. Put client-side files and resources under the root directory.

For example, here is a sample application:

    myapp/
      pubspec.yaml
      web/
        index.html
        css/
          theme.css
        webapp/
          rsp/
            signView.rsp.html
          main.dart
          config.properties

Both `index.html` and `css/theme.css` are resources that the client can access directly. On the other hand, Rikulo Stream will prevent the client from accessing the resources under the `webapp` directory, so it is safe to put sensitive data there.

##Alternative Directory Structures

If you prefer to put the client-side resources under a particular directory rather than the root directory, you can specify the path in the `homeDir` directory when invoking [StreamServer](api:stream).

For example, let us put the static resources under a directory called `client` as follows.

    myapp/
      pubspec.yaml
      web/
        client/
          index.html
          css/
            theme.css
        ...

Then, you can start Stream server as follows:

    new StreamServer(homeDire: "client").start();

Then, if the client accesses `http://yourwebsite.com/css/theme.css`, the file located at `client/css/theme.css` will be retrieved and returned.

Under this configuration, only the resources under the `client` directory is accessible by the client. It also means you can put the server-side Dart files and resource under any folder you like. For example,

    myapp/
      pubspec.yaml
      web/
        client/
          index.html
          css/
            theme.css
        server/
          rsp/
            signView.rsp.html
          config.properties
        main.dart

Then, you can start your application with the following command (assume the current directory is the parent directory of `myapp`):

    dart myapp/web/main.dart
