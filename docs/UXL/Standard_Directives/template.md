#The template Directives

>    <? template *name1* [*name2*...] ?>

The `template` directive declares a list of templates that are defined in other UXL files. UXL compiler won't parse your Dart file or other UXL files, so you have to declares the external templates explicitly.

> Notice that a template is actually a Dart function, while a view is an object. UXL compiler will generate Dart differently according to their types.

For example, the following declares two templates called `Header` and `Footer`.

    <? template Header Footer ?>

You can declare multiple templates in a directive, and have multiple template directives.
