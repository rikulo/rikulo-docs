#Implement Fields

##Update DOM Elements

While using the DOM element(s) to interact with the user, a view is also responsible to update the DOM element(s) accordingly. For example, [TextView](api:view) has to update the content of the DOM element when [TextView.text](api:view) has been changed. For example,

    void set text(String text) {
      _text = text;
      if (inDocument) //check if attached
        node.innerHTML = innerHTML_; //update the DOM element
    }

As shown, we have to call [View.inDocument](api:view) to check if a view is attached to the screen, before accessing the DOM element. Then, we update the DOM element such that the user can see the change.

> To avoid mistakes, [View.node](api:view) will throw an exception if it is not attached.

##Example

Here is a simple example for displaying a label.

    class MyLabel extends View {
      String _text = "";

      String get text => _text;
      void set text(String text) {
        _text = text;
        if (inDocument) //check if attached
          node.innerHTML = XMLUtil.encode(_text); //update the DOM element
      }
      void domInner_(StringBuffer out) {
        out.add(XMLUtil.encode(_text));
        super.domInner_(out); //render child views
      }
    }

 As shown, we update the DOM element in the setter and in `domInner_`. In additions, we use [XMLUtil.encode](api:util) to escape the special characters such as <.

##Render with [View.invalidate()](api:view)

The update of the DOM elements, of course, depends on how you use them. In most cases, it can be handled easily by, say, changing an attribute, replacing a DOM element, etc.

If the update is really complex, you can invoke [View.invalidate()](api:view) to ask Rikulo to regenerate the DOM elements. In other words, [View.draw()](api:view) will be called to generate the HTML fragment again.

The DOM elements won't be regenerated immediately, when [View.invalidate()](api:view) is called. Rather, it is put into a queue and handled later for better performance.
