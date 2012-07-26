#Handle DOM Events

To interact with the user, you can listen to the DOM events you care and response accordingly. To do so, you can register a DOM event listener in [View.mount_()](api:view). [View.mount_()](api:view) will be called for each view after the DOM element is created and attached to the document.

  void mount_() {
    super.mount_();

    node.on.change.add((e) {
      //handle the change event here
      });
  }

The DOM elements will be discarded when the view is detached. In other words, they won't be reused when the view is attached again. Thus, you generally don't need to remove the DOM event listener in [View.unmount_()](api:view). However, it is always a good practice to clean it up in [View.unmount_()](api:view), if you want.

##The Default Handling of DOM Events

[View](api:view) handles many DOM events automatically, such as click, focus, blur, mouseOver and etc. The default handling is to proxy a DOM event with [ViewEvent](api:event) and then send back to the view itself. In other words, you don't have to handle them if you just want to proxy it with [ViewEvent](api:event). For example, the following snippet works well without any assistant code.

    view.on.click.add((e) {
      //do something
      });

###[View.getDOMEventDispatcher_()](api:view)

The event proxy is actually forwarded to [View.getDOMEventDispatcher_()](api:view) for handling. Thus, if you'd like to intercept the default handling, you can override it.
