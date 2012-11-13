#UXL Overview

UXL (User-interface eXtensible language) is a markup language for describing applications' user interfaces. UXL is a simple variant of XML. It allows you to define user interfaces in a similar manner to authoring HTML and XML pages.

> Depending on your preference and your team's skill, you can design UI in Dart, UXL or both.

##How it Works

![How UXL works](how-uxl-works.jpg?raw=true)

First, you define the user interface in a UXL file. Then, run `uc.dart` (UXL compiler) to compile it into the Dart file with [command line interface](http://en.wikipedia.org/wiki/Command-line_interface) as follows:

    dart bin/uc.dart your-uxl-file(s)

UXL compile will generate a Dart file for each UXL file you gave. Then, you can use the generated Dart file to instantiate the user interfaces.

##Install

###Install from Dart Pub repository

Add this to your `pubspec.yaml` (or create it):

    dependencies:
      rikulo_uxl:

###Install from Github for bleeding edge stuff

To install stuff that is still in development, add this to your `pubspec.yam`:

    dependencies:
      rikulo_uxl:
        git: git://github.com/rikulo/rikulo-uxl.git

##UXL Templates

A UXL file defines one or multiple templates. A template will be compiled into a Dart function that you can use to instantiate a composite of views repeatedly.

For example, here we define a `SignIn` template:

    <Template name="SignIn" args="rememberMe: ''">
      <Panel layout="type:linear; orient: vertical; spacing: 4"
        profile="location: center center; width: 180; height: 145">
        Username or Email
        <TextBox id="username" value="$rememberMe" />
        Password
        <TextBox id="password"/>
        <Button text="Sign in" profile="spacing: 12 4 4 4"/>
      </Panel>
    </Template>

UXL compiler will then compile it into a Dart file containing the following function:

    List<View> SignIn({View parent, rememberMe: ''}) {
      List<View> _vcr_ = new List();
      ...
      final _v0_ = (_this_ = new Panel())
      ...
      if (parent != null)
        parent.addChild(_v0_);
      _vcr_.add(_v0_);
      ...
      return _vcr_;
    }

Then, you can instantiate the views defined in this template whatever you want. For example,

    void main() {
      SignIn()[0].addToDocument();
    }

![Sign in](SignIn.jpg?raw=true)

###Binding Dart objects

The binding of Dart objects into views are straightforward. As shown in the above example, you can define as many as arguments you want in the template. Then, you can invoke it by passing the right object to it. For example, you can retrieve the user's name from the cookie and then pass it to the template function:

    SignIn(rememberMe: getRememberMeFromCookie())[0].addToDocument();

You can also reference to Dart objects in the XML attributes. For example,

    <TextBox value="${customer.firstName}, ${customer.lastName}"/>

Notice that if the expression has exactly one `${...}`, it will be generated directly (without converting to a string). It is useful if you'd like to specify a non-string value:

    <View left="${100}"/> <!-- assign 100 rather "100" to left -->
    <Foo user="${currentUser}"/> <!-- you must make sure the types match -->

On the other hand, you have to enforce the string conversion if the type you expect is a string:

    <TextBox value="${foo.toString()}"/>
     <!-- toString required if foo isn't a string since TextBox's value expects -->

> Notice that it is XML, so you can express multiple lines by simply entering LINEFEED:

        <TextView html="
        <ul>
          <li>Now is ${new Date()}</li>
        </ul>" />
          <!-- auto convert to string since it is not a pure ${..} -->

###Embed Dart code into UXL

You can embed Dart code with [the dart directive](../Standard_Directives/dart.md). For example, you can declare the generated dart file as a library or part of a library.

    <?dart
    library foo;
    import "package:rikulo/view.dart";
    ?>

You can even make a UXL file as a standard-alone application:

    <?dart
    import "package:rikulo/view.dart";

    void main() {
      SignIn()[0].addToDocument();
    }
    ?>

    <Template name="SignIn" args="rememberMe: ''">
    ....

###Model-View-Controller (MVC)

Embedding Dart code in a UXL file is convenient. However, for sake of maintenance, it is usually better to separate the code from the view as much as possible. In additions, people who writes UXL files might not know Dart at all.

To do so, you can apply the so-called [Mode-View-Controller (MVC)](../Fundamentals/MVC_Overview.md) design pattern. For more information, please refer to [MVC Overview](MVC_Overview.md).

###Use templates in templates

Each XML element in a UXL can describe no longer how to instantiate a view but also how to invoke a template. For example,

    <Template name="FriendList" args="friends">
      <Template name="Friend" args="friend">
        Name: ${friend.name}
      </Template>
      <Friend friend="$each" forEach="each in friends"/>
    </Template>

> Notice that a template is actually a Dart function, while a view is an object. UXL compiler will generate Dart differently according to their types.

If the template you are going to use is defined in other UXL file, you can declare them with [the template directive](../Standard_Directives/template.md):

    <? template Friend ?>
    <Template name="FriendList" args="friends">
      <Friend friend="$each" forEach="each in friends"/>
    </Template>

###Define multiple templates in a UXL file

You can define multiple templates in the same UXL files:

    <Template name="Foo1">...</Template>
    <Template name="Foo2">...</Template>

It will generate two global functions, `Foo1` and `Foo2`.

> UXL is basically a XML document, but multiple root elements are allowed. Thus, you can declare multiple templates as shown above.

In additions, you can put one template inside another:

    <Template name="Foo1">
      <Template name="Foo2">...</Template>
      ...
    </Template>

It will generate one global function, `Foo1`, and a local function, `Foo2`. `Foo2` is a local variable of `Foo1`. For more information, please refer to [the Template element](../Standard_Elements/Template.md).

