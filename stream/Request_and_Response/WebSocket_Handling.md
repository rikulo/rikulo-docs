#WebSocket Handling

##WebSocket Handlers

A [WebSocket](http://en.wikipedia.org/wiki/WebSocket) handler is a function for processing WebSocket requests. A WebSocket handler *must* have one argument typed [WebSocket](dart:io). The WebSocket protocol is a full-duplex communications channel. Thus, the [WebSocket](dart:io) object implements both [Stream](dart:async) and [StreamSink](dart:async) for reading and writing.

Here is an example:

    //A WebSocket handler
    Future getContentType(WebSocket socket) {
      socket.listen((event) { //receiving the query from the client
        socket.add(contentTypes[event].value); //sending back result to the client
      });
      return socket.done;
    }

Notice that the queries and replies can happen continuously until the connection is closed. Also notice that a WebSocket handler usually returns `socket.done` to indicate the connection is closed when [WebSocket.done](dart:io) completes. If you'd like to process further, you can return another [Future](dart:async) instance instead.

##Map URI to WebSocket Handlers

To map a URI to a WebSocket handler, you have to prefix the URI with `"ws:"`. For example,

    new StreamServer(uriMapping: {
      "ws:/contentType": getContentType
    }).start();

Of course, you can have any number of URI mapping, WebSocket or not.

##Client-side Example

If you implement the client in Dart, you can use [WebSocket](dart:html) to establish the connection. However, for demonstration purpose, we implement the client in JavaScript here:

    <script type="text/javascript">
    var ws, mimeType;
    function getContentType(type) {
      if (ws == null) {
        ws = new WebSocket("ws://localhost:8080/contentType");
        ws.onopen = function() {
          console.log("connection started");
          ws.send(type);
        };
        ws.onmessage = function (evt) { 
          mimeType = evt.data;
          console.log("Echo back: " + mimeType);
        };
        ws.onclose = function() {
          console.log("connection closed");
        };
      } else {
        ws.send(type);
      }
    }
    </script>

> For Dart implementation, you can refer to [Seth's Blog](http://blog.sethladd.com/2012/07/simple-dart-websocket-demo.html).

> Notice that [WebSocket](dart:html) used at the client is under `dart:html` library, while [WebSocket](dart:io) used at the server is under `dart:io` library.
