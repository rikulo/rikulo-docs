#Build Native Mobile Application

With the help of Rikulo Gap, Rikulo leverages and integrates with Cordova/PhoneGap platform allowing you to create native mobile applications and get access to native device resources in Dart and HTML5.

>Apache Cordova/PhoneGap is a platform for building native mobile applications using HTML, CSS and JavaScript.

>Rikulo Gap is the library which bridge Dart program to Apache Cordova/PhoneGap's JavaScript APIs.

##Install Rikulo Gap

Add this to `pubspec.yaml` (or create it) of your application:

    dependencies:
      rikulo_gap:

Then run the [Pub Package Manager](http://pub.dartlang.org/doc) (comes with the Dart SDK):

    pub install

##Download PhoneGap

You can download Cordova/PhoneGap **[here](http://phonegap.com/download)**.

##Steps

Cordova/PhoneGap supports multiple mobile platforms including Android, iOS and others. Each platform needs its own specific libraries, SDKs, and development environment.

>Though each supported mobile platform in Cordova/PhoneGap needs its own specific libraries, SDKs, and development environment, however, only one Rikulo application is required to run on across all mobile platforms.

For convenience, following we use the Android platform and the [Hello World](Hello_World.md) example mentioned in previous section to show you the steps of building a native Rikulo mobile application.
>For other platforms such as iOS, you can check the document [here](http://docs.phonegap.com/en/2.1.0/guide_getting-started_index.md.html#Getting%20Started%20Guides).

1. Compile your Rikulo application from Dart to JavaScript. 
	* Open your application folder in [Dart Editor](http://www.dartlang.org/docs/editor/).
	* Select the dart file with `void main()` function from your application folder; e.g. `HelloWorld.dart`.
	* Select `Tools/Generate JavaScript` from the DartEditor menubar. Two new files with extension `.dart.js` and `.dart.js.map` will be generated in the same folder. e.g. For `HelloWorld.dart` will generate `HelloWorld.dart.js` and `HelloWorld.dart.js.map` two files

	>DartEditor actually calls `dart2js` in dart-sdk to do the compiling job.

2. Install and setup a new `HelloCordova` project in the Cordova/PhoneGap Android developing environment as specified [here](http://docs.phonegap.com/en/2.1.0/guide_getting-started_android_index.md.html#Getting%20Started%20with%20Android).

	Now you shall have had a working `HelloCordova` sample application running in Cordova/PhoneGap environment. The entry point of the `HelloCordova` application in Cordova/PhoneGap environment is the `index.html` file under `assets/www` folder.

3. Replace the `index.html` under `assets/www` with the one from our Rikulo program.
	* Copy `index.html` of our Rikulo example onto `assets/www` folder and replace the old one.
	* Copy all resource files needed for the Rikulo example onto `assets/www` folder. e.g. the `resources` folder and files under.
	* Copy the compiled xxx.dart.js file onto `assets/www` folder. e.g. the `HelloWorld.dart.js`.

4. Remove the version number from the file name of the cordova JavaScript file under `assets/www` folder. e.g. rename `cordova-2.1.0.js` to `cordova.js`.

5. Now you are ready to deploy and run the Rikulo program as a native mobile application.

![Cordova Android Development Environment](cordova-env.png?raw=true)

# Access native mobile devices' resources
Sometimes you might want to access the mobile devices' native resources such as `Camera`, `Accelerometer`, `Compass`, `Geolocation`, `Contacts` and so on. Rikulo Gap integrates with Cordova/PhoneGap and provide these features seamlessly.

Following we show you the basic elements of how to access device native resources in Rikulo by enhancing the simple `Hello World` example application.

##The Dart Code

    import 'package:rikulo/view.dart';
    import 'package:rikulo_gap/device.dart';
    import 'package:rikulo_gap/accelerometer.dart';

    showAcceleration() {
      //prepare a text view to show the acceleration information
      TextView welcome = new TextView("Hello Accelerometer!");
      welcome.profile.text = "anchor:  parent; location: center center";
      new View()..addToDocument()
                ..addChild(welcome);
		
      //watch accelerometer and show the acceleration in x, y and z axis.
      accelerometer.watchAcceleration(
        (accel) {
          welcome.text = "${accel.timestamp},\n"
                       "x:${accel.x},\n"
                       "y:${accel.y},\n"
                       "z:${accel.z}";
	        welcome.requestLayout(true); },
        () => print("Fail to get acceleration"),
        new AccelerometerOptions(frequency:1000)
      );
    }

    void main() {
      //enable the device
      Future<Device> enable = enableDeviceAccess();

      //when device is enabled and ready
      enable.then((device) => showAcceleration());

      //if failed to enable the device and/or timeout!
      enable.handleException((ex) {
        print("Fail to enable the device.");
        return true;
      });
    }

As shown above:

1. Import [device](gap:) and [accelerometer](gap:) libraries along with others.
2. Call global method [enableDeviceAccess()](gap:device) in the `main` method which will return a `Future<Device>`.
3. Use the native resource `accelerometer` via the global variable called [accelerometer](gap:accelerometer) when the device is ready.

###Import [device](gap:) and [accelerometer](gap:) libraries along with others

The [device](gap:) library is the library holding device resources.
The [accelerometer](gap:) library is the library that handle the device motion sensor.

###Enable Device Accessibility

Calling global method [enableDeviceAccess()](gap:device) in the `main` method to enable and initialize the device accessibility. This method returns a `Future<Device>` object. After the device is enabled and ready, the function in the Future's then() method will be called with the enabled device as the function argument. If failed to enable the device or timeout, the onException function in Future's handleException() method will be called with the thrown exception object.

###Access the Device Resource

Via the global mobile facility variables such as [accelerometer](gap:accelerometer) you are able to access all mobile device's native resources. 

In this application, we register a success callback function and an error callback function to [Accelerometer.watchAcceleration()](gap:accelerometer) method with the frequency of the [AccelerometerOptions](gap:accelerometer) set to every 1000 milliseconds. This makes the mobile accelerometer service return the [Acceleration](gap:accelerometer) information via the registered success callback function every 1000 milliseconds.

>Per the different kind of native resources, you can access the device resources by calling directly the APIs or by register proper callback functions.

>For details about what device resources are available, please check [Rikulo Gap](../Rikulo_Gap/index.md).
