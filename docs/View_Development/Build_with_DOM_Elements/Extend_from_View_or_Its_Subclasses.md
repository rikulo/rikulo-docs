#Extend from View or Its Subclasses

A view must be a subclass of [View](api:view). To implement a view, you shall extend from [View](api:view) or its subclasses.

Here are a few existent subclasses that you might extend from:

* [View](api:view): the most fundamental class.
* [ScrollView](api:view): a view container that allows the user to scroll the content in a smaller viewport.
* [Section](api:view): a view that also implements [IdSpace](api:view). It is also a good example to see how a view implements [IdSpace](api:view).
* [TextView](api:view): a view for displaying text and Web content. You can use it if you'd like to display Web content in a part of your view.

> In additions to extending from them (the *is-a* relation), you can use them as a member (the *has-a* relation). For more information, please refer to the [Build with Composite of Views](../Build_with_Composite_of_Views.md) chapter.