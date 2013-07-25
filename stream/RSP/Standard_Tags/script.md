#The script tag

>[:script src="*a_uri_of_the_script*" bootstrap="*true|false*"]

Generates `SCRIPT` tags to load the given script.

For example,

    [:script src="/script/foo.dart"]

 will generate the following, if the browser supports Dart

    <script type="application/dart" src="/script/foo.dart"></script>
    <script src="/packages/browser/dart.js"></script>

On the other hand, if the browser doesn't support Dart, it will generate

    <script src="/script/foo.dart.js"></script>

If you prefer to force the script tag to generate JavaScript links no matter if the browser supports Dart or not, you can specify true to [Rsp.disableDartScript](api:stream). For example,

    if (yourAppInProduction) //whatever condition you'd like
      Rsp.disableDartScript = true;

##The src Attribute

> Required

It specifies the URI of the script to load. It can be any kind of scripts that the [SCRIPT](http://www.w3schools.com/tags/tag_script.asp) tag can deliver. It is handled specially only if it is a Dart script.

##The bootstrap Attribute

> Optional
> Allowed Values: `true` and `false`
> Default: `true`

It specifies whether to generate an additional `SCRIPT` tag to load the bootstrap JavaScript code: `/packages/browser/dart.js` if necessary.

You can turn it off if you'd like to multiple Dart script files in one page -- one of them requires the bootstrap JavaScript code.

##The Versioning of the JavaScript and Dart Files

To have the browser to reload the JavaScript (and Dart) files automatically, you can insert a version into the URL. It can be done easily by assigning a version number to [StreamServer.uriVersionPrefix](api:stream):

    server.uriVersionPrefix = "/12345678"; //must start with /

Then, the given prefix will be added to the generated URL. Furthermore, it is transparent to the application (the URI mapping needs not to be changed).
