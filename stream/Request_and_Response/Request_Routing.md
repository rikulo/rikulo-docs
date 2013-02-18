#Request Routing

To map a request handler to a URI, you have to specify it in a map passed to the `uriMapping` argument of the constructor of [StreamServer](api:stream). For example,

    void main() {
      new StreamServer(uriMapping: {
        "/server-info": serverInfo,
        "/order/.*": order,
        "/user/.*": user
      }).run();
    }

For sake of management, it is suggested to put the mapping in a separated Dart file named `config.dart` for holding all configurations. For example,

    //config.dart
    var _mapping = {
      "/server-info": serverInfo,
      "/order/.*": order,
      "/user/.*": user
    };

Then, you can start your server as follows:

    //main.dart
    void main() {
      new StreamServer(uriMapping: _mapping).run();
    }

The server's administrator can then change the configuration in `config.dart` without much knowledge of Dart language.

##URI Pattern

A URI pattern is a regular expression. It must start with `'\'`. It is used to match [HttpRequest.uri](dart:html) when a request is received (, included or forwarded).

Notice the regular expression must match the complete URI. For example, `/server-info` doesn't match `/server-info/`.

> For handling exceptions and missed resouces, please refer to [Error Handling](../Configuration/Error_Handling.md).