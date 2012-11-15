#The control Attribute

>    control="*Controller_Class*"  
    control="*controll_name*: *Controller_Class*"  
    control="*an_expression*"  
    control="*controll_name*: *an_expression*"

The `control` attributes specifies the controller for mediating the view and model in the so-called [Mode-View-Controller (MVC)](../Fundamentals/MVC_Overview.md) design pattern.

The controller shall extend from [Control](uxl:uxl). In the simplest form, you don't need to override any method but add [your command handlers](on.event.md) to it:

    class CustomerControl extends Control {
        void delete(ViewEvent event) {
            //...
        }
        void add(ViewEvent event) {
            //...
        }
    }

##Assign a class

>    control="*Controller_Class*"

If a Dart identifier is specified, it is assumed to be a class. When [the template](../Standard_Elements/Template.md) instantiates the view assigned with the control attribute, it also instantiates an instance of the controller. In other words, it is an one-to-one relationship.

##Assign an instance

>    control="*an_expression*"

If you'd like to instantiate it by yourself, you can specify an expression:

    <View control="new FooControl(foo)">

If the identifier is actually an instance of the controller rather than its class, you can enclose it with a parenthesis:

    <View control="(fooController)">

Then, UXL compiler will consider it as an expression that returns a controller instance.

##Assign a name

>    control="*controll_name*: *Controller_Class*"  
    control="*controll_name*: *an_expression*"

If you want to access it in the child views, you can specify a name. For example,

    <View control="brandNew: BrandNew">
        ...
        ${brandNew.lastName}, ${brandNew.firstName}

It is also helpful if you have nested controller:

    <View control="foo1: FooControl1"> <!-- name it -->
      ...
      <View control="FooControl2">
        ...
        <Button on.click="cmd1"/> <!-- FooControl2.cmd1(e) will be called -->
        <TextBox on.change="foo1.cmd2"/> <!-- FooControl1.cmd2(e) will be called -->
