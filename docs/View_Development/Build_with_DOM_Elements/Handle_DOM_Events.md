#Handle DOM Events

To interact with the user, you can listen to the DOM events you care and response accordingly. To do so, you can register a DOM event listener.

Depending on your requirement, you can register it anytime you prefer. For example, you can defer the registration until it is really required.

If you have to handle it for every instance, you can register it in [View.render_()](api:view) after instantiating the DOM element. For example,

    void render_() {
      final node = new Element.html('<div><div class="foo-button"></div></div>');
      node.query("div.foo-button").on.add((event) {
        //handle it
        });
      return node;
    }

##The Default Handling of DOM Events

[View](api:view) handles all DOM events automatically, such as `click`, `focus`, `blur`, `mouseOver` and etc. The default handling is so-called *event proxy*. As its name suggests, it proxies a DOM event with an instance of [ViewEvent](api:event). Then, send the instance to the view, if the user ever registers an event (by using [View.on](api:view)).

In other words, you don't have to handle DOM events, if you just want to proxy them with [ViewEvent](api:event). For example, the following snippet works well *without* any assistant code.

    view.on.click.add((e) {
      //do something
      });

> [TestDragAndDrop.dart](source:test) is an example that handles the drag and drop events transparently. All these events are handled by the default event proxy.

###[View.onEventListened_()](api:view)

The event proxy is actually handled by [View.onEventListened_()](api:view). You can override if you'd like to intercept the default handling. For example,

    void onEventListened_(String type) {
      if (type == "change") {
        //handle it specially
      } else {
        super.onEventListened_(type);
      }
    }
