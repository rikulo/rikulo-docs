#The dart Directive

    <? dart any_valid_dart_code ?>

The `dart` directive allows you to write blocks of Dart code inside UXL.

##Make a UXL file a library

It is typically used to declare a library and import other libraries. For example,

    <?dart
    library foo;

    import "package:rikulo/view.dart"
    ?>

##Make a UXL file part of a library

It can also be used to declare the generated Dart file as part of a library.

    <?dart
    part of foo;
    ?>

##Make a UXL file as a complete application

Technically, you can do whatever you want. For example, you can make a UXL file as an application by embedding the `main` function. For example,

    <?dart
    import "package:rikulo/view.dart";

    void main() {
      SignIn()[0].addToDocument();
    }
    ?>

    <Template name="SignIn">
      <Panel layout="type:linear; orient: vertical; spacing: 4"
        profile="location: center center; width: 180; height: 145">
        Username or Email
        <TextBox id="username" />
        Password
        <TextBox id="password"/>
        <Button text="Sign in" profile="spacing: 12 4 4 4"/>
      </Panel>
    </Template>
