#Separate Data from UI with Model

You can separate the underlying data structure containing the data from the UI that displays the data. It is generally suggested, since you can construct and even change complex UI easier. 

To do so, you first implement a model and then assign the model to a view that supports it. For example, to display a list of options, you have to implement [ListModel](api:model). Then, you can assign it to a view that supports [ListModel](api:model), such as [RadioGroup](api:view), [DropDownList](api:view), and [ListView](api:view) depending on your need.

##Use Default Implementation

Though [ListModel](api:model) is simple, you can use the default implementation, [DefaultListModel](api:model) directly. For example,

    new DropDownList(model: new ListModel(["apple", "orange", "lemon", "juice"]));

##Selection

Some views allow the user to select one or multiple data in the list. To work with them, your implementation of [ListModel](api:model) shall also implement [Selection](api:model) too. Alternatively, you can use [DefaultListModel](api:model) since it implements [Selection](api:model). For example,

    final DefaultListModel<String> model
      = new DefaultListModel(["apple", "orange", "lemon", "juice"]);
    model.addToSelection("orange"); //select one of them
    model.addToDisables("juice"); //you can disable some items
    model.on.select.add((event) {
      //do something when the user selects an item
    });
    new DropDownList(model: model);

As shown, [DefaultListModel](api:model) also implements [Disables](api:model), so you can disable items that the user can't select.

##Sharing the Selection

You can have two or more views to share the same model.

    new DropDownList(model: model);
    new RadioGroup(model: model);

Since the model is shared, the selection is shared too. If the user selects an item in one of views, all other views will reflect it.

##Not to Share the Selection

If you prefer to share the data but not to share the selection, you can implement a list model that maintains the selection but *delegate* [ListModel.operator[ ]](api:model) to another list model holding the data.

    class ProxyListModel<E> extends AbstractListModel<E> {
      final ListModel<E> origin;
      PorxyListModel(ListModel<E> this.origin);

      E operation[](int index) => origin[index];
      int get length() => origin.length;
    }

Then, you can split the selection as you want it.

    new DropDownModel(model: model);
    new DropDownModel(model: new ProxyListModel(model)); //shared data but not selection

##Data Altering

If you have to change the data after assigning to UI, you have to invoke one of the alerting method in [DefaultListModel](api:model), such as [DefaultListModel.operator[ ]=](api:model) and [DefaultListModel.add()](api:model). For example,

    model.add("flower"); //UI will be updated automatically to show "flower"
    model.addToSelection("flower"); //UI will be updated to select "flower"

You shall *not* modify the original passed to the list model's constructor. Otherwise, UI won't be changed to reflect the data.

##TreeModel

You can implement [TreeModel](api:model) to represent a hierarchy data structure. Similarly, there is a default implementation called [DefaultTreeModel](api:model), which assumes each node is an instance of [TreeNode](api:model). Here is an example:

    DefaultTreeModel<String> model = new DefaultTreeModel(nodes: [
      "Wonderland",
      new TreeNode("Australia",
        ["Sydney", "Melbourne", "Port Hedland"]),
      new TreeNode("New Zealand",
        ["Cromwell", "Queenstown"])]);
    new DropDownList(model: model); //DropDownList supports TreeModel too

[DefaultTreeModel](api:model) also implements [Selection](api:model), so you can handle as well.

    model.on.select.add((event) {
      //do something when some of items is selected
    });

Notice that the selected item in [DefaultTreeModel](api:model) is an instance of [TreeNode](api:model). So you have to use the instance of [TreeNode](api:model) to select it manually if you want. For example, the following select the second child's third child.

    model.addToSelection(model.root[1][2]);

##Renderer

By default, views that support model will convert the data to a string and display it. If you want to customize it, you have to implement a renderer to generate the content to display. Unlike models, different views have different renderers. For example, the signature of [DropDownList](api:view) is as follows.

    typedef String DropDownListRenderer(
      DropDownList dlist, var data, bool selected, bool disabled, int index);
