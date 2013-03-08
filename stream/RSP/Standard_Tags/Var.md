#The var tag

>[var *var_name*] *...* [/var]

**Usage 1: Defines a variable**

Defines a variable and assigns its value with the content inside the tag. For example,

    [var footer]
    <div>Copyright &copy; Superdog Co.</div>
    [/var]

Then, you can refer to the `footer` variable in any following expressions, such as `[= footer]`.

Like the [[for]](for.md) tag, you can put any content into [[var]](var.md), including [[include]](include.md) and others to generate as sophisticated content as you want.

**Usage 2: Defines an argument**

If [[var]](var.md) is placed as the direct child of [[include]](include.md) and [[forward]](forward.md), it defines an argument  rather than a variable.

For example, the following invokes the `foo` closure with three arguments: `title`, `menu` and `footer`.

    [include foo title="Foo"]
      [var menu]
        [include "/menu.html"/]
      [/var]
      [var footer]
      <div>Copyright &copy; Superdog Co.</div>
      [/var]
    [/include]

For more information, please refer to [Templating: Composite View Pattern](../Fundamentals/Templating-_Composite_View_Pattern.md).