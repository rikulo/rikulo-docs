#The json tag

>[:json *var_name*=*a_Dart_expression*]

Generates a JavaScript object by evaluating the given Dart expression and serializing it into a JSON string by use of [stringify](http://api.dartlang.org/docs/releases/latest/dart_json.html#stringify).

For example,

    [:json data=["john", "mike"]]

Then, it will generates the following to the HTML output:

    <script>data=["john", "mike"];</script>

To retrieve it in Dart (running at the browser), you can use [js-interop](https://github.com/dart-lang/js-interop) as follows:

    import 'package:js/js.dart' as js;
    
    void main() {
      foo(js.context.data);
    }

Since [stringfy](http://api.dartlang.org/docs/releases/latest/dart_json.html#stringify) is used to convert Dart objects into JavaScript, you have to make sure they can be serialized into a JSON string. For instance, you have to implement a method called `toJson` for your own classes.
