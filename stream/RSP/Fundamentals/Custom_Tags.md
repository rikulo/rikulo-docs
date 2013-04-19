#Custom Tags

In additions to [Standard Tags](../Standard_Tags), you can implement your own custom tags. A RSO tag must implement or extend from [Tag](api:stream).

For real examples, please refer to [tag.dart](source:lib/src/rspc).

##Add custom tags to RSP compiler

RSP tags are used by RSP compiler rather than your Stream server application. To add the customer tags to RSP
compiler, you can modify the `build.dart` file you put int the root directory of your project. For example, you implement two custom tags, `JsonTag` and `DataTag`, then `build.dart could be as follows:

    import 'dart:io';
    import 'package:stream/rspc.dart' show build, tags;
    import 'mytags.dart' show JsonTag, DataTag;

    void main() {
      for (final tag in [new JsonTag(), new DataTag()])
        tags[tag.name] = tag;

      build(new Options().arguments);
    }
