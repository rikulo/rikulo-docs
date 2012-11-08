#The Apply Element

    <Apply [if="a_condition"] [forEach="a_for_statement"]>

The `Apply` element allows you to group a collection of views and control how to generate them. For example, you can generate them repeatedly with [the `forEach` attribute](../Standard_Attributes/forEach.md) such as

    <Apply forEach="fruit in ['apple', 'orange']">
      $fruit: <TextBox/>
    </Apply>

Notice that the `Apply` element itself won't instantiate any view. The views it groups will be added to the nearest parent element specifying a view.

For instance, the button's parent will be the panel in the following example:

    <Panel>
      <Apply if="logined">
        <Button text="Log out"/>
      </Apply>
    </Panel>
