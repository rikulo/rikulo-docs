#Custom Tags

In additions to [Standard Tags](../Standard_Tags), you can implement your own custom tags. A RSO tag must implement or extend from [Tag](api:stream).

For real examples, please refer to [tag.dart](source:lib/src/rspc).

##Add custom tags to RSP compiler

RSP tags are used by RSP compiler rather than your Stream server application. To add the customer tags to RSP
compiler, you can modify the `build.dart` file you put int the root directory of your project. For example, you implement two custom tags, `JsonTag` and `DataTag`, then `build.dart could be as follows:

    import 'dart:io' show Options;
    import 'package:stream/rspc.dart' show build, tags;
    import 'mytags.dart' show JsonTag, DataTag;

    void main() {
      for (final tag in [new JsonTag(), new DataTag()])
        tags[tag.name] = tag;

      build(new Options().arguments);
    }

##An example

Here is an example we utilize [SimpleTag](api:stream_rspc). First, assume we have a method for returning a internalization message for a given identifier called `message()` as follows:

    String message(HttpConnect connect, String id, [Map<String, dynamic> args]) {
      //..return the intl message of id
    }

Then, we can add an additional tag called `msg` to invoke `message()` as follows:

    import 'dart:io' show Options;
    import 'package:stream/rspc.dart';

    void main() {
      tags["msg"] = new SimpleTag("msg",
        (TagContext tc, String id, Map<String, String> args) {
          if (id == null)
            throw new ArgumentError("id required");
          tc.write("\n${tc.pre}response.write(message(connect, $id");
          if (args != null && !args.isEmpty) {
            tc.write(", ");
            outMap(tc, args);
          }
          tc.writeln("));");
        });

      build(new Options().arguments,
        imports: ["package:foo/intl.dart"]);
    }

> Also notice that we can specify the package in the import argument when invoking [build](api:stream_rspc).

Then, you can use the `msg` tag in your RSP file:

    [:msg USERNAME]
    [:msg WELCOME name="[=user]"]
