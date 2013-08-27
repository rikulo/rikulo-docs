#The json tag

>[:json *var_name*=*a_Dart_expression*]

Generates a JSON string by evaluating the given Dart expression and serializing it into a JSON string by use of [JSON.encode](http://api.dartlang.org/docs/releases/latest/dart_convert.html#JSON).

For example, assume you have a variable as follows

    result = ["john", "mike"];

then,

    [:json data={"users": result}]

will generates the following to the HTML output:

    <script id="data" type="text/plain">{"users": ["john", "mike"]};</script>

To retrieve it in Dart (running at the browser), you can do as follows:

    import 'dart:convert' show JSON;
    
    void main() {
      serve(JSON.decode(document.query("#data").innerHtml));
    }
    void server(Map<String, List<String>> data) {
      //...
    }

> To generate a JavaScript object that can be used by third-party JavaScript library, please use [[:json-js]](json-js.md) instead.

> Notice: [JSON](http://api.dartlang.org/docs/releases/latest/dart_convert.html#JSON) is used to convert between [String](dart:core) and Dart objects, so you have to make sure it can be serialized into a JSON string. For instance, you have to implement a method called `toJson` for your own classes.
