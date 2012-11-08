#The template Directives

    <? template name1 [name2...] ?>

The `template` directive declares a list of templates that are defined in other UXL files or at the end of the same UXL file. UXL compiler won't parse your Dart file or other UXL files, so you have to declares the templates that will be used explicitly.

For example, the following declares two templates called `Header` and `Footer`.

    <? template Header Footer ?>

You can declare multiple templates in a directive, and have multiple template directives.
