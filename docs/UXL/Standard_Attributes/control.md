#The control Attribute

    control="controllerFunction"

The `control` attributes specifies the controller for mediating the view and model in the so-called [Mode-View-Controller (MVC)](../Fundamentals/MVC_Overview.md) design pattern.

The controller must be a closure that take a single argument typed as [View](api:view). The signature is as follows:

    void controllerName(View view);

For example, assume we have a sign-in form as follows.

    <Template name="SignIn" control="signIn">
      <Panel layout="type:linear; orient: vertical; spacing: 4"
        profile="location: center center; width: 180; height: 145">
        Username or Email
        <TextBox id="username" />
        Password
        <TextBox id="password"/>
        <Button text="Sign in" profile="spacing: 12 4 4 4"/>
      </Panel>
    </Template>

Then, we can implement the `signIn` method as follows:

    void signIn(View view) {
      view.query("Button").on.click.add((event) {
        final username = view.query("#username").value,
          password = view.query("#password").value;
        authenticate(username, password);
        });
    }
