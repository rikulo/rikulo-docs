#MVC Design Pattern

[MVC (Model-View-Controller)](http://en.wikipedia.org/wiki/Model_2) is an architecture pattern that separates the representation of information from the logic used to obtain and manipulate the content.

![MVC](mvc.jpg?raw=true)

This separation of concerns helps to organize code that is easier to develop and cheaper to maintain.

Under Dart's asynchronous programming paradigm, it can be more valuable: Only the controller needs to deal with the underlying data asynchronously, while the view can read and render the data synchronously.

> Depending the nature of your data model, you can implement the view in the asynchronous approach by chaining the access of the data model (with a series of [Future](dart:async) objects). For sake of description, we discuss only the data model that can be read synchronously.

##Controller

The controller provides the glue logic between the model and the view. It is [a request handler](Request_Handling.md) that Stream server dispatches to. In other words, it is the handler specified in [the URI mapping](Request_Routing.md).

The typical pattern is that the controller accesses the underlaying data asynchronously based on the request, and then prepare it into Dart objects for the view to display. For example,

    Future getUser(HttpConnect connect) {
      //1. Load and/or store the data asynchronously
      return Database.loadUser(getUsername(connect)).then((User user) {
        //2. Forward to the view for display
        return userView(connect, user: user);
      });
    }

where we assume `Database.loadUser(String username)` is a utility to load the `User` object asynchronous, and `getUsername(HttpConnect connect)` to retrieve the user's name from the request (usually part of URI or a query parameter depending your requirement). Of course, depending on your requirement, it can be anything posted in the request's body, such as a JSON string. Please refer to [Form Handling](Form_Handling.md).

We also assume `userView(HttpConnect connect,{User user})` is the view to display the data. It will be discussed in the following section.

##View

The view provides the visual representation shown at the client. It is also [a request handler](Request_Handling.md). It usually has additional named argument(s) to carry the data model prepared by the controller.

For easy implementation, the view is usually implemented with the template engine called [RSP (Rikulo Stream Page)](../RSP/Fundamentals/RSP_Overview.md). For example,

    [:page args="User user"]
    <html>
      <title>User: [=user.name]</title>
      ...

As shown, [[:page]](../RSP/Standard_Tags/page.md) specifies the named argument (`{User user}`) and then [[=]](../RSP/Standard_Tags/=.md) retrieves the user's name from the data model. It will be compiled into a request render similar to the following:

    Future userView(HttpConnect connect, {User user}) {
      //...
      connect.response..write("<html>\n  <title>Users: ")..write(user.name);
      //...
    }

##Model

The model represents the underlaying data used by an application. Stream doesn't provides a model layer of its own. You can choose the database and object mapping layer that best fits your needs, such as [CouchClient](https://github.com/rikulo/couchclient) for accessing [Couchbase](http://www.couchbase.com/).

> For a simple yet runnable example, you can refer to the [hello-mvc](source:example) example.
