#The tag and tag-* Attribute

>tag="*tagName*"  
tag-*attr_name*="*attr_value*"

The tag attributes tells UXL compiler to use the [View.View.tag](api:view) constructor instead of the default constructor. In addition, you can prefix an attribute with `tag-` to indicate the attribute shall be assigned to [View.node](api:view) rather than the view itself.

For example,

    <View tag="ul" tag-contentEditable="true">

will generate

    new View.tag("ul")
      ..node.contentEditable = "true";

Notice that, since it is also a view, the absolute position is applied. In other words, to arrange them property, you have specify the coordinates or the linear layout if necessary.
