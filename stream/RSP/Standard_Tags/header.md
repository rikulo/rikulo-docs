#The header tag

>[:header *name1*="*value1*" *name2="*value2*"]

Specifies any number of HTTP response headers.

You can specify an expression ([[=expression]](=.md)) as the attribute's value. For example,

    [:header age="129" accept-ranges="[=foo.acceptRanges]"]

> Notice that you can end with either `]` or `/]`.
