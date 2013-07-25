#Host and Port

By default, Stream server listens on `http://127.0.0.1:8080` ([InternetAddress.ANY_IP_V4](dart:io)). If you prefer to listen on different host or port, you can specify it when invoking [StreamServer.start()](api:stream). For example,

    new StreamServer(uriMapping: fooUriMapping, errorMapping: fooErrorMapping)
      .start(port: 80);

If you want the server to listen on the socket you provide, you can invoke [StreamServer.startOn()](api:stream) instead:

    new StreamServer(uriMapping: fooUriMapping, errorMapping: fooErrorMapping)
      .startOn(fooSocket);

##Multiple Channels

In additions, you can have the same Stream server to listen to different addresses and/or ports:

    new StreamServer(uriMapping: fooUriMapping, errorMapping: fooErrorMapping)
      ..start(port: 80)
      ..start(address: "11.22.33.44", port: 80);

The combination of the address and port is called a channel. You can retrieve it from [HttpConnect.channel](api:stream).
