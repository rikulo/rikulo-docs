#Host and Port

By default, Stream server listens on `http://127.0.0.1:8080`. If you prefer to listen on different host or port, you can specify it at [StreamServer.host](api:stream) or [StreamServer.port](api:stream) before invoking [StreamServer.run()](api:stream). For example,

    new StreamServer(uriMapping: fooUriMapping, errorMapping: fooErrorMapping)
      ..port = 80
      ..run();

If you want the server to listen on the socket you provide, you can do:

    new StreamServer(uriMapping: fooUriMapping, errorMapping: fooErrorMapping)
      .run(fooSocket);
