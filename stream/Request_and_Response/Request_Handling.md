#Request Handling

##Request Handers

A *request handler* is a function that processes a particular phase of a request. A request handler must have one argument typed [HttpConnect](api:stream). For example,

    void currentTime(HttpConnect connect) {
      connect.response
        .outputStream.write("<html><body>It is now ${new Date.now()}.</body></html>");
      connect.close(); //see HttpConnect described below
    }

A request handler can have any number of named arguments too. They are usually used to pass data models for processing. For example,

    void placeOrder(HttpConnect connect, {OrderInfo info}) {
      //...handle the order
    }

> For how to pass data to a request handler, please refer to [the MVC Design Pattern section](MVC_Design_Pattern.md).

A request handler can optionally return an URI to forward the request to. For example,

    String authorize(HttpConnect connect) {
      if (!isLogin(connect.request))
        return "/login"; //forward to /login for authentication

      //...authorize it
      return null; //null means done
    }

##HttpConnect

[HttpConnect](api:stream) encapsulates all information of a HTTP connection including the request ([HttpRequest](dart:io)) and the response ([HttpResponse](dart:io)).

###HttpConnect's close and error fields

Please notice that a connection might be included by another connections, and connections are usually handled asynchronously (the nature of a scalable Rikulo Stream server). It is not easy to find out the right moment to close the output stream in a complex environment.

To minimize the effort, [HttpConnect.close](api:stream) and [HttpConnect.error](api:stream) are introduced. All you have to do is to invoke [HttpConnect.close](api:stream) when a handler completes the rendering successfully. Rikulo Stream knows when to close the output stream. For example,

    void currentTime(HttpConnect connect) {
      connect.response
        .outputStream.write("<html><body>It is now ${new Date.now()}.</body></html>");
      connect.close(); //instead of connect.response.outputStream.close()
    }

###Handling Request Asynchronously

If a connection is handled asynchronously, set the success callback with [HttpConnect.close](api:stream) and the error callback with [HttpConnect.error](api:stream), such that the output stream will be closed, if necessary, and [the error handling](../Configuration/Error_Handling.md) will take place, if an error occurs. For example,

    void copyFile(HttpConnect connect, {File file}) {
      file.openInputStream()
        ..onError = connect.error //forward to connect.error for error handling
        ..onClosed = connect.close //forward to connect.close upon completion
        ..pipe(connect.response.outputStream, close: false);
    }

###Future.catchError

Rikulo Stream will catch and handle exceptions thrown by request handlers. However, if the request handler is processed asynchronously, you have to forward the error to [HttpConnect.error](api:stream). Therefore, the connection will be closed, if necessary, and [the error handling](../Configuration/Error_Handling.md) will take place, if an error occurs.

    file.exists().then((exists) {
      if (exists) {
        doSomething(); //any exception will be caught and handled
        connect.close(); //forward to connect.close upon completion
        return;
      }
      throw new Http404();
    }).catchError(connect.error); //forward to connect.error for error handling

