#An Example

Here is a simple example for displaying a label.

    class WebView extends View {
      //@override
      Element render_() => new Element.tag("div");

      String get html => node.innerHTML;
      void set html(String html) {
        node.innerHTML = html;
      }
    }

As mentioned in [the Handle DOM Events section](Handle_DOM_Events.md), the DOM event will be proxied to [ViewEvent](api:event) automatically. Thus, the following code works as expected.

    new WebView()
      ..on.click.add((event) { //Note: it is an instance of ViewEvent
        print("${event.target}"); //event.target is WebView itself
        })
      ..addToDocument();
