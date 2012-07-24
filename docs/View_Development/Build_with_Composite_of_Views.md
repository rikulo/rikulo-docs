#Build with Composite of Views

The simplest way to implement a view is to compose it with other views like a macro view (aka., a composite view). For example, assume we want to have a view that displays a label and also an input as shown below.

![A Composite View](composite.jpg?raw=true)

> The source code can be found at github: [composite-view](source:samples).

##Composite

You can implement it as a subclass of [View](api:view) as follows.

    class LabeledInput extends View {
      TextView _label;
      TextBox _input;

      LabeledInput(String label, [String value]) {
        layout.type = "linear"; //use horizontal linear layout
        addChild(_label = new TextView(label));
        addChild(_input = new TextBox(value));
      }
      String get label() => _label.text;
      void set label(String label) {
        _label.text = label;
      }
      String get value() => _value.value;
      void set value(String value) {
        _value.value = value;
      }
    }

Then, the application can use it without knowing how it is implemented.

    mainView.layout.text = "type: linear; orient: vertical";
    mainView.addChild(new LabeledInput("username"));
    mainView.addChild(new LabeledInput("password"));

###The className Property

To work with [View.query()](api:view), you have to implement [View.className](api:view) to return the view's class name.

    class LabeledInput extends View {
      String get className() => "LabeledInput";

Therefore, the application can query it with this class name, such as

    mainView.query("LabeledInput");

##Event Handling

You can *propagate* the events sent by the internal views to the caller of the composite view. To do so, you can listen to the event you care and handle it the way you want. For example, in this sample, you can propagate the `change` event as follows.

    LabeledInput(String label, [String value]) {
      ...
      _input.on.change.add((ChangeEvent event) {
        sendEvent(new ChangeEvent(event.value, target: this));
        });
    }

Then, the application can listen the `change` event if necessary.

    for (LabeledInput view in mainView.queryAll("LabeledInput"))
      view.on.change.add((event) {
        print("$event received");
        });
