#The Template Element

>    <Template name="*template_name*" [args="*a_list_of_arguments*"] [description="*a_description*"]>

The `Template` element defines a template. A template will be compiled to a Dart function. The signature is as follows:

    List<View> tempalte_name({View parent, a_list_of_arguments});

The `name` attribute will become the function's name. The list of arguments specified in the `args` attribute will become to the function's arguments. Notice that you shall not specify `parent` in the `args` argument. It is always generated.

The function will return a list of the top-level views being instantiated. In other words, it has the same content as `parent.children`, if `parent` is given.

If you want to add the created views as child views of a particular view, you can pass the view as the `parent` argument. If you don't, you can simply ignore the `parent` argument and retrieve them from the returned list.

For example,

    <Template name="Simple" args="user">
      <Panel layout="type: linear">
        ${user.name} <Button text="Profile"/>
      </Panel>
    </Template>

will generate

    List<View> Simple({View parent, user}) {
      ...
    }

Furthermore, `Simple` will return a list with a single element, an instance of `Panel`, since `Panel` is the only top-level view.

##Nested Templates

you can put one template inside another:

    <Template name="Foo1">
      <Template name="Foo2">...</Template>
      ...
    </Template>

It will generate one global function, `Foo1` and one local function, `Foo2` as follows. 

    List<View> Foo1({parent}) {
      List<View Foo2({parent}) {
        ...
      }
      ...
    }

Notice that `Foo2` is accessible only inside `Foo1`:

    <Template name="Foo1">
      <View data-foo="Foo2"/> <!-- Wrong! Foo2 is not accessible here -->
      <Template name="Foo2">...</Template>
      <View data-foo="Foo2"/> <!-- Correct! Foo2 is accessible here -->
    </Template>
    <Template name="Foo3">
      <View data-foo="Foo2"/> <!-- Wrong! Foo2 is not accessible here -->
      <View data-foo="Foo1"/> <!-- Correct! Foo1 is accessible here -->
    </Template>

##args: beforeChild

By default, the created views will be appended to the given parent if any. If you prefer to allow the caller to control whether to insert, you can specify `beforeChild` as one of your arguments. For example,

    <Tempalte name="Foo" args="beforeChild, whatever">
    ...

Then, the caller can pass a child as `beforeChild` such that all created views will be inserted before it:

    Foo(parent: parent, beforeChild: refChild);

> Since `beforeChild` is interpreted specially, you can use it for other purposes.
