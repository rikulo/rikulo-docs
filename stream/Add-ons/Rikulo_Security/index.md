#Rikulo Security

[Rikulo Security](https://github.com/rikulo/security) is a lightweight and highly customizable authentication and access-control framework.

##Installation

Add this to your `pubspec.yaml` (or create it):

    dependencies:
      rikulo_security:

Then run the [Pub Package Manager](http://pub.dartlang.org/doc) (comes with the Dart SDK):

    pub install

##Usage

 First, you have to implement [Authenticator](security:security). For sake of description, we use a dummy implementation here called [DummyAuthenticator](security:security_plugin):

    import "package:rikulo_security/security.dart";
    import "package:rikulo_security/plugin.dart" show DummyAuthenticator;
    ...
    final authenticator = new DummyAuthenticator()
      ..addUser("john", "123", ["user"])
      ..addUser("peter", "123", ["user", "admin"]);

 Second, you can use [SimpleAccessControl](security:security_plugin) or implement your own access control
 ([AccessControl](security:security)):

    import "package:rikulo_security/plugin.dart" show SimpleAccessControl;
    ...
    final accessControl = new SimpleAccessControl({
      "/admin/.*": ["admin"],
      "/member/.*": ["user", "admin"]
    });

 Finally, instantiate [Security](security:security) with the authenticator and access control you want:

     final security = new Security(authenticator, accessControl);
     new StreamServer(uriMapping: {
       "/s_login": security.login,
       "/s_logout": security.logout
     }, filterMapping: {
       "/.*": security.filter
     }).start();

The `/s_login` and `/s_logout` paths can be any URI you prefer, as long as they match the URI you specify in your login form. For example,

    <form action="/s_login" method="post" accept-charset="UTF-8">
      Username<br/>
      <input name="s_username" type="text" autofocus="true" size="30"/><br/>
      Password<br/>
      <input name="s_password" type="password" size="30"/>
      <p><button type="submit">Sign in</button></p>
    </form>

Please refer to [this sample application](https://github.com/rikulo/security/tree/master/example/hello) for sample code.
