#The on.*event* Attribute

>    on.*event_name*="*command_name*"  
    on.*event_name*="*controller_name*.*command_name*"  
    on.*event_name*="*command_name1*, *controller_name2*.*command_name2*..."

The on.*event* attribute binds an event with one or multiple command handlers. If you'd like to bind multiple command handlers, separate them with commas (,).

For example, you can binds the click event of a button with a command handler called `signIn` as follows:

    <Button text="Sign in" on.click="signIn"/>

Then, whenever the button is clicked, the function called `signIn` will be called. The function is called *command handler*. The signature is as follows:

    void signIn(ViewEvent event) {
      //handle the command
    }

As shown, the command handle must take a single argument and the argument type is [ViewEvent](api:event) or one of its subclasses.

##Without Controller

If none of the ancestor view is associated with a controller (i.e., [the control attribute](../Standard_Attributes/control.md) is *not* assigned), the command handler is assumed to be a top-level (aka., global) function.

If you'd like to control which command handler to use, you can pass it through the args attribute of [the Template element](../Standard_Elements/Template.md):

    <Template name="SignIn" args="signIn: signIn">
      ...
      <Button text="Sign in" on.click="signIn"/>

##With Controller (the MVC pattern)

For better structure, you can handle multiple commands in a context, i.e., within an instance of your class. For example, assume you want to handle the commands in a class called `SignInControl`. Then, you can assign this class with [the control attribute](../Standard_Attributes/control.md) as shown below:

    <View control="SignInControl">
      ...
      <Button text="Sign in" on.click="signIn"/>

Then, when the view is instantiated, an instance of `SignInControl` will be instantiated too. In additions, whenever the button is clicked, `SignInControl.signIn(event)` will be called.

As shown, `SignInControl` shall extend from [Control](uxl:uxl). In the simplest form, you don't need to know anything about [Control](uxl:uxl) but add your command handlers there.

    class SignInControl extends Control {
      void signIn(ViewEvent event) {
        //handle the command
      }
    }

###Automatically Re-rendering

After the command handler is called, the user interface will be re-rendered automatically to reflect the latest states. Thus, you don't need to touch UI in command handlers, unless you want the fine-grained control. For example,

    class CustomerControl {
      Customer current;
      ...
      void delete(ViewEvent event) {
        current.remove(); //manipulate data directly
      }
    }

Please refer to [Control](uxl:uxl) for how to control whether to re-rendering automatically.

###Nested Controller

>    on.*event_name*="*controller_name*.*command_name"

If you have multiple controllers, you can control which controller to send the commands to as follows:

    <View control="foo1: FooControl1"> <!-- name it -->
      ...
      <View control="FooControl2">
        ...
        <Button on.click="cmd1"/> <!-- FooControl2.cmd1(e) will be called -->
        <TextBox on.change="foo1.cmd2"/> <!-- FooControl1.cmd2(e) will be called -->

As shown, if the command is not preceded with a controller's name, it is assumed to be the nearest controller. If there is no controller at all, it is assumed to be top-level.
