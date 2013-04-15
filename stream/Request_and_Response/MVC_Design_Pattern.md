#MVC Design Pattern

[MVC (Model-View-Controller)](http://en.wikipedia.org/wiki/Model_2) is an architecture pattern that separates the representation of information from the logic used to obtain and manipulate the content.

![MVC](mvc.jpg?raw=true)

This separation of concerns helps to organize code that is easier to develop and cheaper to maintain.

Under Stream's asynchronous programming model, it can be more valuable: Only the controller needs to deal with the underlying data asynchronously, while the view can read and render the data synchronously.

> Depending the nature of your data model, you can implement the view asynchronously by chaining the access of the data model (in a series of `Future` objects). For sake of description, we discuss only the data model that can be read synchronously.

##Controller

The controller provides the glue logic between the model and the view. It is [a request handler](Request_Handling.md) that Stream server dispatches to. In other words, it is the handler specified in [the URI mapping](Request_Routing.md).

The typical pattern is to access the underlaying data asynchronously based on the request, and then prepare it into Dart objects that the view can access. For example,

    Future getUser(HttpConnect connect) {
      return Database.loadUser(getUsername(connect)).then((User user) { //load data model
        return userView(connect, user: user); //forward to view
      });
    }

where we assume `Database.loadUser(String username)` is a utility to load the `User` object asynchronous, and `getUsername(HttpConnect connect)` to retrieve the user's name from the request (usually part of URI or a query parameter depending your requirement).

We also assume `userView(HttpConnect connect,{User user})` is the view that will be discussed in the following section.

##View

The view provides the visual representation shown at the client. It is also [a request handler](Request_Handling.md) that usually has additional named argument(s) to carry the data model prepared by the controller.

For easy implementation, the view is usually implemented with the template technology called [RSP](../RSP/Fundamentals/RSP_Overview.md). For example,

    [:page args="User user"]
    <html>
      <title>User: [user.name]</title>
      ...

As shown, [[:page]](../RSP/Standard_Tags/page.md) specifies the named argument (`{User user}`) and then the content of the given user.

##Model

The model represents the underlaying data used by an application. Stream doesn't provides a model layer of its own. Depending on your requirement, you can choose the database and object mapping layer that best fits your needs.

> For a runnable example, you can refer to the [hello-mvc](source:example) example.
