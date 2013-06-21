#The json-js tag

>[:json-js *var_name*=*a_Dart_expression*]

Generates a JavaScript object by evaluating the given Dart expression and serializing it into a JSON string by use of [stringify](http://api.dartlang.org/docs/releases/latest/dart_json.html#stringify).

The json-js tag is designed to generate a JavaScript object that will be used in third-party JavaScript library. If you'd like to use it in Dart, please use [[:json]](json.md) instead.

For example, assume you have a variable as follows

    result = ["john", "mike"];

then,

    [:json-js data={"users": result}]

will generates the following to the HTML output:

    <script>data={"users": ["john", "mike"]};</script>

If you really want to retrieve it in Dart (running at the browser), you can use [js-interop](https://github.com/dart-lang/js-interop) as follows:

    import 'package:js/js.dart' as js;
    
    void main() {
      foo(js.context.data);
    }

Notice that the object returned by `js-interop` is an instance of [Proxy](http://dart-lang.github.io/js-interop/docs/js/Proxy.html). You can't handle it straightforward as `Map` or `List`.

> Notice: [stringfy](http://api.dartlang.org/docs/releases/latest/dart_json.html#stringify) is used to convert Dart objects into JavaScript, so you have to make sure it can be serialized into a JSON string. For instance, you have to implement a method called `toJson` for your own classes.
