#Implicit Object: `_this_`

>[View](api:view) _this_;

It references to the view being instantiated. It can be used in the attributes. For example,

    <TextBox type="password" value="${_this_.type}">

Since `_this_` remains valid before the next instantiation of views, you can reference it in the consecutive [dart directive](../Standard_Directives/dart.md):

    <View id="v1"> <!-- _this_ references to v1 since here -->
      <? dart
      _this_.on.clock.add((event) {
        doSomething();
        });
      ?>
      <View id="v2"/> <!-- _this_ references to v2 since here -->
    </View>

##Notes

It is not applicable when the element describes the instantiation of a template. For example, assume `FooTemplate` is a template, then the following is incorrect since a template is actually a Dart closure.

    <FooTemplate foo="${_this_.something}"/> <!-- Incorrect since FooTemplate is not a view -->
