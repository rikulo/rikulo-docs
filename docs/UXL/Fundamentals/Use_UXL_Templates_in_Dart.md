#Use UXL Templates in Dart

A template is simply a Dart function and you can pass any number of arguments. You can use it as an element to composite any user interface you want.

Here we list a few patterns worth to mention.

> For more information about the signature of the template function, you can refer to [the Template element](../Standard_Elements/Template.md).

##Create Custom Views

Instead of using a template directly, you can encapsulate it as a view. By doing so, you can provide additional behaviors you want, and the user doesn't need to know the existence of the template.

For example, assume us have a template as follows.

    <Template name="_LabeledInputTemplate" args="label, value">
      ${label} <TextBox value="$value"/>
    </Template>

Then, we can create a custom view with it.

    class LabeledInput extends View {
      LabeledInput(String label, [String value]) {
        _LabeledInputTemplate(label: label, value: value)
      }
      String get label => query("TextView").text;
      void set label(String label) {
        query("TextView").text = label;
      }
      String get value => query("TextBox").value;
      void set value(String value) {
        query("TextBox").value = value;
      }
    }

For more information about creating a custom view, please refer to [Build with Composite of Views](../../View_Development/Build_with_Composite_of_Views.md).