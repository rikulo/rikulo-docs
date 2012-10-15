#Build Native Mobile Application

Rikulo leverages and integrates with Cordova/PhoneGap platform allowing you to create native mobile applications and get access to native device resources in Dart and HTML5.

>Apache Cordova/PhoneGap is a platform for building native mobile applications using HTML, CSS and JavaScript.



##Download

You can download Cordova/PhoneGap **[here](http://phonegap.com/download)**.

##Steps
Cordova/PhoneGap supports multiple mobile platforms including Android, iOS and others. Each platform needs its own specific libraries, SDKs, and development environment.

>Though each supported mobile platform in Cordova/PhoneGap needs its own specific libraries, SDKs, and development environment, however, only one Rikulo application is required to run on across all mobile platforms.

For convenience, following we use the Android platform and the [Hello World](Hello_World.md) example mentioned in previous section to show you the steps of building a native Rikulo mobile application.
>For other platforms such as iOS, you can check the document [here](http://docs.phonegap.com/en/1.9.0/guide_getting-started_index.md.).

1. Compile your Rikulo application from Dart to JavaScript. 
	* Open your application folder in [Dart Editor](http://www.dartlang.org/docs/editor/).
	* Select the dart file with `void main()` function from your application folder; e.g. `HelloWorld.dart`.
	* Select `Tools/Generate JavaScript` from the DartEditor menubar. Two new files with extension `.dart.js` and `.dart.js.map` will be generated in the same folder. e.g. For `HelloWorld.dart` will generate `HelloWorld.dart.js` and `HelloWorld.dart.js.map` two files

	>DartEditor actually calls `dart2js` in dart-sdk to do the compiling job.

2. Install and setup a new `HelloCordova` project in the Cordova/PhoneGap Android developing environment as specified [here](http://docs.phonegap.com/en/1.9.0/guide_getting-started_android_index.md.html#Getting%20Started%20with%20Android).

	Now you shall have had a working `HelloCordova` sample application running in Cordova/PhoneGap environment. The entry point of the `HelloCordova` application in Cordova/PhoneGap environment is the `index.html` file under `assets/www` folder.

3. Replace the `index.html` under `assets/www` with the one from our Rikulo program.
	* Copy `index.html` of our Rikulo example onto `assets/www` folder and replace the old one.
	* Copy all resource files needed for the Rikulo example onto `assets/www` folder. e.g. the `resources` folder and files under.
	* Copy the compiled xxx.dart.js file onto `assets/www` folder. e.g. the `HelloWorld.dart.js`.

4. Remove the version number from the file name of the cordova JavaScript file under `assets/www` folder. e.g. rename `cordova-1.9.0.js` to `cordova.js`.

5. Now you are ready to deploy and run the Rikulo program as a native mobile application.

![Cordova Android Development Environment](cordova-env.png?raw=true)

# Access native mobile devices' resources
Sometimes you might want to access the mobile devices' native resources such as `Camera`, `Accelerometer`, `Compass`, `Geolocation`, `Contacts` and so on. Rikulo integrates with Cordova/PhoneGap and provide these features seamlessly.

Following we show you the basic elements of how to access device native resources in Rikulo by enhancing the simple `Hello World` example application.

##The Dart Code

    import 'package:rikulo/app.dart';
    import 'package:rikulo/view.dart';
    import 'package:rikulo/device.dart';
    import 'package:rikulo/device/accelerometer.dart';

    class HelloWorld extends Activity {
      void onCreate_() {
        TextView welcome = new TextView("Hello Accelerometer!");
        welcome.profile.text = "anchor:  parent; location: center center";
        mainView.addChild(welcome);
		
        //listen to device's accelerometer and show the acceleration in x, y and z axis.
        device.accelerometer.on.accelerate.add((AccelerationEvent event) {
          welcome.text = "${event.acceleration.timeStamp},\n"
            "x:${event.acceleration.x},\ny:${event.acceleration.y},\nz:${event.acceleration.z}";
		  welcome.requestLayout(true);
        }, options: new AccelerometerOptions(frequency:1000));
      }
    }

    void main() {
      enableDeviceAccess();
      new HelloWorld().run();
    }

As shown above:

1. Import [device](api:) and [device/accelerometer](api:) packages along with others.
2. Call global method [enableDeviceAccess()](api:device) in the `main` method.
3. Use the native resource `accelerometer` via the global variable called [device](api:device).

###Import [device](api:) and [device/accelerometer](api:) package

The [device](api:) package is the main package holding all device resources. The [device/accelerometer](api:) package is the package specific for accelerometer resource in mobile device.

###Enable Device Accessibility

Calling global method [enableDeviceAccess()](api:device) in the `main` method to initialize the device accessibility.

###Access the Device Resource

Via the global variable [device](api:device) you are able to access all mobile device's native resources. 

In this application, we register a device event listener and listen to the [AccelerationEvent](api:device/accelerometer) of the [Device.accelerometer](api:device) resource with frequency set to every 1000 milliseconds.

>Per the different kind of native resources, you can access the device resources by calling directly the APIs or register proper device event listeners.

>For details about what device resources are available, please check [Device Services](../Device_Services/index.md).

 