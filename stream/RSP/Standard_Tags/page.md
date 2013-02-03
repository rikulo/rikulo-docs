#The page tag

>[page name="*closure_name*" content-type="*a_content_type*" args="*a_list_of_arguments*" description="*a_description*"]

Specifies the information about this page.

The `name` attribute, if specified, will become the closure's name. The `args` attribute, if specified, defines additional named parameters for the closure. For example,

    [page name="foo" args="from:0, to:100"]

will generate a closure as follows:

    void foo(HttpConnect connect, {from:0, to:100})

If the `content-type` attribute is omitted, the content type will be decided based on the file extension, and the encoding is assumed to be `UTF-8`.

You can specify an expression ([[=expression]](=.md)) as the value of the `content-type` attribute.