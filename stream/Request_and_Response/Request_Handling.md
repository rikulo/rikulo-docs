#Request Handling

##Request Handlers

A *request handler* is a function that processes a particular phase of a request. A request handler *must* have one argument typed [HttpConnect](api:stream). For example,

    void currentTime(HttpConnect connect) {
      connect.response
        .write("<html><body>It is now ${new Date.now()}.</body></html>");
    }

> [HttpConnect](api:stream) encapsulates all information of a HTTP connection, including the request ([HttpRequest](dart:io)) and the response ([HttpResponse](dart:io)).

###Return `void` if completed immediately

If a request handler finishes immediately, it doesn't have to return anything. For example,

    void serverInfo(HttpConnect connect) {
      final info = {"name": "Rikulo Stream", "version": connect.server.version};
      connect.response
        ..headers.contentType = contentTypes["json"]
        ..write(Json.stringify(info));
    }

> Tip: For sake of consistency, you can always return [Future](dart:async) by use of `new Future.value()`. Furthermore, you can specify `futureOnly: true` when starting [StreamServer](api:stream), such that an error will be thrown if an event handler or filter doesn't return a Future object.

###Return `Future` if handled asynchronously

If a request handler processes the request asynchronously, it *must* return an instance of [Future](dart:async) to indicate when the processing will be completed. For example,

    Future loadFile(HttpConnect connect)
    => return new File("some_file").exists().then((exists) {
          if (exists)
            return connect.response.addStream(file.openRead());
          throw new Http404("some_file");
        });

> It is for illustration. You generally don't have to load a file, since Stream server will handle it automatically.

The returned `Future` object can carry any type of objects, such as `Future<bool>`. The type is application specific (as long as your caller knows how to handle it). Stream server simply ignores it.

If you're using [Completer](dart:async), remember to *wire* the error. For example,

    Future waitUntilReady(HttpConnect connect) {
      final completer = new Completer();
      doSomething(
        onDone: () => completer.complete(),
        onError: (error) => completer.completeError(error));
      return completer.future;
    }

> Tip: before using [Completer](dart:async), it is always a good idea to find if a method returns [Future](dart:async) for saving the effort, such as [StreamSubscription.asFuture](dart:async) and [IOSink.done](dart:io).

###Provide additional named arguments

A request handler can have any number of named arguments too. They are usually used to pass data models for applying the [MVC](http://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller) design pattern. For example,

    Future getUser(HttpConnect connect) { //controller
      User order = new User(connect); //retrieve user from URI or query parameters...
      return showUser(connect, order: order);
    }
    Future showUser(HttpConnect connect, {User order}) { //model
      //...display the user
    }

> For how to apply MVC, please refer to [the MVC Design Pattern section](MVC_Design_Pattern.md).

> For how to process a form submit, please refer to [the Form Handling section](Form_Handling.md).

###Chain the request handlers

Chaining the request handlers is straightforward: there is no difference from chaining [Future](dart:async) objects:

    Future foo(HttpConnect connect) {
      //assume you have two other renders: header and footer
      return header(connect).then((_) {
        connect.response.write("write the body...");
        return footer(connect);
      });
    }

If you'd like to wrap [HttpConnect](api:stream) to, say, preventing HTTP headers from updates or to redirecting the output to a buffer, you can use [HttpConnect.chain](api:stream), [HttpConnect.buffer](api:stream), or [HttpConnectWrapper](api:stream).

###Chain to another URI

To chain to another URI, you can use [HttpConnect.include](api:stream) or [HttpConnect.forward](api:stream):

    Future foo(HttpConnect connect) {
      return connect.include("/webapp/header").then((_) {
        connect.response.write("<p>This is the body</p>");
        return connect.include("/webapp/footer");
      });
    }

> For information, please refer to [Request Forwarding and Inclusion](Request_Forwarding_and_Inclusion.md) and [Templating - Composite View Pattern](../RSP/Fundamentals/Templating-_Composite_View_Pattern.md).

##Request Routing

To map a URI to a request handler, you can specify a URI mapping when instantiating [StreamServer](api:stream). For example,

    void main() {
      new StreamServer(uriMapping: {
        "/server-info": (connect) {
          connect.response.write("<html><body>${getServerInfo()}.</body></html>");
        },
        "/order/.*": placeOrder,
        "/user/.*": user
      }).start();
    }

As shown, the URI is a regular expression staring with '/'. Furthermore, you can specify the HTTP method for RESTful services, name the matched group and so on. For more information, please refer to [the Request Routing section](Request_Routing.md).
