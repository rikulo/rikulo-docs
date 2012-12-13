#The on.*event* Attribute

>on.*event_name*="*command_name*"  
on.*event_name*="*controller_name*.*command_name*"  

The on.*event* attribute binds an event with a command handler.

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

###Implement Command Handlers

The function of a command handler depends on your requirement. Typically, it
manipulates the data and then updates UI accordingly.

For example, in response to a delete command, you can delete the data and then remove the view directly as follows:

    void delete(ViewEvent event) {
      model.delete(something);
      view.query("#foo").remove(); //update only the part of UI being affected
    }

 Alternatively, if there are a lot of views to modify, you can invoke [Control.render()](uxl:uxl) to re-render the whole hierarchy of views starting at [Control.view](uxl:uxl) as follows:

     void reload(ViewEvent event) {
       model.reload();
       render(); //re-render the view
     }

For better separation of data and UI, you can implement your data model by extending from [DataModel](api:model), and then send a data event to indicate the data has been changed. Thus, you don't have to update UI directly in a command handler, since it is done automatically by the listener of the data events.

For example,

    class YourModel extends Model {
      void add(something) {
        ...//modify the model
        sendEvent(new YourDataEvent(this, 'add', something));
      }
    }

    class YourControl extends Control {
      YourControl(YourModel model) {
        model.on.add.add((YourDataEvent e) {
          ...//alter UI accordingly
        });
      }
      void add(e) {
        model.add(something);
        //no need to update UI here since the listener above will handle it
      }
    }

Please refer to [Control](uxl:uxl) for how to seperate the UI updating from the model altering further with [DataModel](api:model).

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
