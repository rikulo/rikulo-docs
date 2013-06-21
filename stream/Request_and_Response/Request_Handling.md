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

> Tip: For sake of consistency, you can always return [Future](dart:async) by use of `new Future.value()`.

###Return `Future` if handled asynchronously

If a request handler processes the request asynchronously, it *must* return an instance of [Future](dart:async) to indicate when the processing will be completed. For example,

    Future loadFile(HttpConnect connect)
    => new File("some_file").exists().then((exists) {
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

A request handler can have any number of named arguments too. They are usually used to pass data models for applying the [MVC design pattern](MVC_Design_Pattern.md). For example,

    Future userRetriever(HttpConnect connect) { //controller
      //1. prepare the model
      return loadUser(connect.request.uri.queryParameter["user"])
        .then((User user) {
          //2. pass the model to the view
          return userView(connect, user: user);
        });
    }
    Future userView(HttpConnect connect, {User user}) { //view
      //...display the user
    }
    Future<User> loadUser(String name) {
      //...access the user from database
    }

> For how to apply MVC, please refer to [the MVC Design Pattern section](MVC_Design_Pattern.md).

> For how to process a form submit, please refer to [the Form Handling section](Form_Handling.md).

###Chain the request handlers

As shown above, chaining the request handlers is straightforward: there is no difference from chaining [Future](dart:async) objects.

If you'd like to wrap [HttpConnect](api:stream) to, say, collect the output to a buffer for further processing, you can use [HttpConnect.stringBuffer](api:stream) or [HttpConnect.buffer](api:stream).

    Future foo(HttpConnect connect) {
      final buffer = new StringBuffer();
      return another(new HttpConnect(connect, buffer)).then(() {
        //...process buffer and write the result to connect.response
      })
    }

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
