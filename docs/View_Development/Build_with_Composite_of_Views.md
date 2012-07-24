#Build with Composite of Views

The simplest way to implement a view is to compose it with other views like a macro view (aka., a composite view). For example, assume we want to have a view that displays a label and also an input as shown below.

![A Composite View](composite.jpg)

##Composite

You can implement it as a subclass of [View](api:view) as follows.

    class LabeledInput extends View {
      TextView _label;
      TextBox _input;
      LabelInput(String label, [String value]) {
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

Then, you can use it without knowing how it is implemented.

    mainView.layout.text = "type: linear; orient: vertical";
    mainView.addChild(new LabelInput("username"));
    mainView.addChild(new LabelInput("password"));

#Event Handling
