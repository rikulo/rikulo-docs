#The Template Element

    <Template name="template_name" [args="a_list_of_arguments"] [description="a_description"]>

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
