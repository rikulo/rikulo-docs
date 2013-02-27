#Host and Port

By default, Stream server listens on `http://127.0.0.1:8080`. If you prefer to listen on different host or port, you can specify it at [StreamServer.host](api:stream) or [StreamServer.port](api:stream) before invoking [StreamServer.start()](api:stream). For example,

    new StreamServer(uriMapping: fooUriMapping, errorMapping: fooErrorMapping)
      ..port = 80
      ..start();

If you want the server to listen on the socket you provide, you can invoke [StreamServer.startOn()](api:stream) instead:

    new StreamServer(uriMapping: fooUriMapping, errorMapping: fooErrorMapping)
      .startOn(fooSocket);
