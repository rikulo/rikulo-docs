#Request Handling

##Request Handers

A *request handler* is a function that processes a particular phase of a request. A request handler *must* have one argument typed [HttpConnect](api:stream). For example,

    void currentTime(HttpConnect connect) {
      connect.response
        .write("<html><body>It is now ${new Date.now()}.</body></html>");
    }

[HttpConnect](api:stream) encapsulates all information of a HTTP connection, including the request ([HttpRequest](dart:io)) and the response ([HttpResponse](dart:io)).

###Return `void` if completed immediately

If a request handler finishes immediately, it doesn't have to return anything. For example,

    void serverInfo(HttpConnect connect) {
      final info = {"name": "Rikulo Stream", "version": connect.server.version};
      connect.response
        ..headers.contentType = contentTypes["json"]
        ..write(Json.stringify(info));
    }

###Return `Future` if handled asynchronously

If a request handler processes the request asynchronously, it *must* return an instance of `Future` to indicate when the processing will be completed. For example,

    Future loadFile(HttpConnect connect) {
      final completer = new Completer();
      final res = connect.response;
      new File("some_file").openRead().listen((data) {res.writeBytes(data);},
        onDone: () => completer.complete(),
        onError: (err) => completer.completeError(err));
      return completer.future;
    }

Also notice that, as shown above, the error has to be *wired* to the `Future` object being returned, so [the default error handling](../Configuration/Error_Handling.md) will be applied.

The returned `Future` object can carry any type of objects, such as `Future<bool>`. It is application specific (as long as your caller knows how to handle it). Stream server simply ignores it.

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

> For how to process a form submit, please refer to [Form Handling](Form_Handling.md).

###Chain the request handlers

You can *chain* the request handlers easily with [HttpConnect.include](api:stream). For example,

    Future foo(HttpConnect connect) {
      return connect.include("/webapp/header").then((_) {
        connect.response.write("<p>This is the body</p>");
        return connect.include("/webapp/footer");
      });
    }

In additions to [HttpConnect.include](api:stream), you can invoke another request handlers directly depending on your requirements.

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

As shown, the URI is a regular expression staring with '/'. Furthermore, you can specify the HTTP method for RESTful services, name the matched group and so on. For more information, please refer to [Request Routing](Request_Routing.md) section.
