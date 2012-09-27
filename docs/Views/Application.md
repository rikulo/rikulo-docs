#Application

* Package: [app](api:)
* API Reference: [Application](api:app)
* Source: [Application.dart](source:lib/src/app)

An application is the base class for holding global application states.

> Instead of extending [Application](api:app), you can manage global application states in global variables. It is generally more convenient

You can provide your own implementation by instantiating it as follows:

    application = new MyApp();

Notice that you must initialize your custom application, before instantiating
your first activity. For example,

    void main() {
      application = new MyApp();
      new FooActivity().run();
    }

##Make Activity to Wait Until Some Resources Ready

When adding a service to Rikuo, you can have [Activity](api:app) to wait until the service is ready. Therefore, when [Activity.onCreate_()](api:app) is called, everything is ready to access. It simplifies the implementation of [Activity.onCreate_()](api:app) a lot.

To do so, you can add a closure to [Application.addReadyCallback()](api:app).

    application.addReadyCallback((Task then) {
       if (_checkIfReady()) {
         then(); //do it immediately
       } else {
         _doUntilReady(then); //queue then and call it when it is ready.
       }
     });

You have to implement `_checkIfReady` and `_doUntilReady` to check if the resource is ready and invoke `then` when the resource ready. Of course, you can name it as you like.