#The page tag

>[page name="*closure_name*" args="*a_list_of_arguments*" description="*a_description*"

>partOf="*a_dart_file_or_a_lib_name*" import="*a_list_of_lib_names*" contentType="*a_content_type*"]

Specifies the information about this page.

##The partOf Attribute

> Optional

You can specify the path of a Dart file in the `partOf` attribute to indicate that the generated Dart file shall be part of the given Dart file. For example,

    [page partOf="../main.dart"]

Notice that RSP compiler will maintain the given Dart file automatically, including adding the `part` and `import` statements if necessary.

Alternatively, you can specify the library's name in the `partOf` attribute if you prefer to maintain the library manually:

    [page partOf="my_killer_app"]

If the `partOf` attribute is omitted, the generated Dart file will be generated as an indepedent library. You have to import it manually when accessing it.

##The import Attribute

> Optional

The `import` attribute specifies the libraries to import. For example,

    [page import="dart:async, dart:collection show HashMap"]

> Notice that `dart:io` and `dart:io` and `package:stream/stream.dart` will be imported by default. You don't need to specify them.

> If a Dart file is specified in the `partOf` attribute, the given Dart file will be updated automatically with the imported libraries specified in this attribute.

> The `import` attribute is ignored, if a library's name is specified in the `partOf` attribute.

##The name and args Attributes

> Optional

The `name` attribute, if specified, will become the closure's name. The `args` attribute, if specified, defines additional named parameters for the closure. For example,

    [page name="foo" args="from:0, to:100"]

will generate a closure as follows:

    void foo(HttpConnect connect, {from:0, to:100})

##The contentType attribute

> Optional

If the `contentType` attribute is omitted, the content type will be decided based on the file extension, and the encoding is assumed to be `UTF-8`.

You can specify an expression ([[=expression]](=.md)) as the value of the `contentType` attribute.