#The page tag

>[:page name="*closure_name*" args="*a_list_of_arguments*" description="*a_description*" contentType="*a_content_type*"

>  partOf="*a_dart_file_or_a_lib_name*" import="*a_list_of_lib_names*" part="*a_list_of_dart_files*" dart="*dart_code*"]

Specifies the information about this page.

> Notice that you can end with either `]` or `/]`.

##The partOf Attribute

> Optional

You can specify the path of a Dart file in the `partOf` attribute to indicate that the generated Dart file shall be part of the given Dart file. For example,

    [:page partOf="../main.dart"]

Notice that RSP compiler will maintain the given Dart file automatically, including adding the `part` and `import` statements if necessary.

Alternatively, you can specify the library's name in the `partOf` attribute if you prefer to maintain the library manually:

    [:page partOf="my_killer_app"]

If the `partOf` attribute is omitted, the generated Dart file will be generated as an indepedent library. You have to import it manually when accessing it.

##The import Attribute

> Optional

The `import` attribute specifies the libraries to import. For example,

    [:page import="dart:collection, dart:collection show HashMap"]

> Notice that `dart:io`, `dart:async` and `package:stream/stream.dart` will be imported by default. You don't need to specify them.

> If a Dart file is specified in the `partOf` attribute, the given Dart file will be updated automatically with the imported libraries specified in this attribute.

> The `import` attribute is ignored, if a library's name is specified in the `partOf` attribute.

##The part attribute

> Optional

The `part` attribute specifies the dart files to be parted of this library. For example,

    [:part import="dart:collection, dart:convert show JSON" part="part1.dart, part2.dart"]

It will generate something similar to:

    import "dart:collection";
    import "dart:convert" as JSON;
    part "part1.dart";
    part "part2.dart";

##The name and args Attributes

> Optional

The `name` attribute, if specified, will become the closure's name. The `args` attribute, if specified, defines additional named parameters for the closure. For example,

    [:page name="foo" args="from:0, to:100"]

will generate a closure as follows:

    void foo(HttpConnect connect, {from:0, to:100})

##The contentType attribute

> Optional

If the `contentType` attribute is omitted, the content type will be decided based on the file extension, and the encoding is assumed to be `UTF-8`.

You can specify an expression ([[=expression]](=.md)) as the value of the `contentType` attribute.

If you prefer not to set the content type, you can specify an empty string.

##The lastModified attribute

> Optional  
> Allowed Values: `compile` and `start`

It specifies whether the generated Dart code shall set [the Last-Modified header](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html) automatically. If `compile` is specified, the header is set to when the RSP file is compiled. If `start` is specified, the header is set to when the server is started.

If omitted (default), it won't set the Last-Modified header. You can set it with the [:header](header.md) tag with the value you prefer.

##The dart attribute

> Optional

It specifies a snippet of Dart code that will be inserted before the declaration of the request handler.

    [:page dart="
    var globalVariable = 123;
    void globalUtility() {
      //...
    }"]

On the other hand, the [:dart](dart.md) tag specifies the Dart code that will be inserted inside the request handler.
