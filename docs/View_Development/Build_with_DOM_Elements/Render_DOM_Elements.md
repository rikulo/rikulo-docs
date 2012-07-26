#Render DOM Elements

As described in [UI Overview](../../Views/Fundamentals/UI_Overview.md), the DOM element(s) of a view is created when the view is attached to the screen, and removed when the view is detached from the screen.

The creation and removal are done automatically by Rikulo. You need only to override [View.draw()](api:view) to generate the HTML fragment representing the DOM element(s) that are going to created.

##Render the HTML fragment

When the DOM element(s) of a view shall be created, [View.draw()](api:view) will be called to generate the HTML fragment representing the DOM element(s). Rikulo will create the DOM element(s) based on the fragment and insert them to the browser's DOM tree automatically.

For example, here is the default implementation of [View.draw()](api:view):

    void draw(StringBuffer out) {
      final String tag = domTag_;
      out.add('<').add(tag);
      domAttrs_(out);
      out.add('>');
      domInner_(out);
      out.add('</').add(tag).add('>');
    }

As shown, it utilizes a few methods, such as [View.domTag_](api:view) and [View.doInner_()](api:view), to simplify the generation of the HTML fragment. In additions, it is usually easier to override them separately instead of overriding [View.draw()](api:view).


###Override [View.domTag_](api:view)

[View.domTag_](api:view) returns the HTML tag used to enclose the HTML fragment. By default, it is `DIV` and it can override to return a different value you like.

###Override [View.domAttrs_()](api:view)

[View.domAttrs_()](api:view) generates the DOM attributes for the enclosing tags. By default, it generates the `id` attribute to be [View.uuid](api:view), the `class` attribute to represent [View.classes](api:view), the `style` attribute to represent the view's coordinates and visibility, etc.

Though optional, it is suggested to override [View.domAttrs_()](api:view) to generate additional attributes, as you want, rather than overriding [View.draw()](api:view) and generating there. For example,

    void domAttrs_(StringBuffer out,
    [bool noId=false, bool noStyle=false, bool noClass=false]) {
      out.add(' type="').add(type).add('"');
      if (disabled)
        out.add(' disabled="disabled"');
      if (autofocus)
        out.add(' autofocus="autofocus"');
      super.domAttrs_(out, noId, noStyle, noClass);
    }

> The `noId`, `noStyle` and `noClass` arguments are designed if you prefer to generate any of these attributes by yourself, rather than by the superclass.  
However, the generation of the `id` attribute is important since [View.node](api:view) depends on it, so, if you specify `noId` to true, you have to generate it by yourself.

###Override [View.domInner_()](api:view)

[View.domInner_()](api:view) generates the HTML fragment for the content inside the enclosing tag ([View.domTag_](api:view)). By default, it invokes [View.draw()](api:view) for each child view, and then concatenates the result:

  void domInner_(StringBuffer out) {
    for (View child = firstChild; child !== null; child = child.nextSibling) {
      child.draw(out);
    }
  }

If you'd like to add additional layers of DOM elements, you can override it as follows.

    void domInner_(StringBuffer out) {
      out.add('<div class="v-inner">'); //additional layers
      super.domInner_(out); //render child views
      out.add('</div');
    }

If you need to access an additional layer, you can generate the `id` attribute for it. Then, you can retrieve the DOM element back by use of [View.getNode()](api:view). For example,

    void domInner_(StringBuffer out) {
      out.add('<div class="v-inner"><div id="').add(uuid).add('-foo">');
      super.domInner_(out); //render child views
      out.add('</div');
    }

Then, `getNode("foo")` returns the element whose `id` is assigned with `uuid`, dash and `foo`.

If a view doesn't allow child views at all, you can generate the HTML fragment inside the enclosing tag directly. For example,

    void domInner_(StringBuffer out) {
      out.add("Hi, $name");
      //no need to call back super.domInner_ if you're sure no child allowed
    }

##Initial DOM elements

After the DOM elements are created and inserted into the browser's DOM tree, [View.mount_()](api:view) will be called. Therefore, you can initialize DOM elements, if necessary, in [View.mount_()](api:view). For example, you can listen the DOM elements:

    void mount_() {
      super.mount_();

      getNode("foo").on.click.add((_onFooClick));
    }

##Clean up DOM elements

Similarly, [View.unmount_()](api:view) will be called to clean up DOM elements, if necessary, when the DOM elements are detached. For example, you can remove the listener of DOM events:

    void unmount_() {
      getNode("foo").on.click.remove(_onFooClick);

      super.unmount_();
    }
