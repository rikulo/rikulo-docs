#The Template Element

    <Template name="template_name" [args="a_list_of_arguments"] [description="a_description"]>

The `Template` element defines a template. A template will be compiled to a Dart function. The signature is as follows:

    List<View> tempalte_name({View parent, a_list_of_arguments});

The `name` attribute will become the function's name. The list of arguments specified in the `args` attribute will become to the function's arguments. Notice that you shall not specify `parent` in the `args` argument. It is always generated.

The function will return a list of the top-level views being instantiated. In other words, it has the same content as `parent.children`, if `parent` is given.

For example,

    <Template name="Simple" args="user">
      ${user.name} <Button text="Profile"/>
    </Template>

will generate

    List<View> Simple({View parent, user}) {
      ...
    }
