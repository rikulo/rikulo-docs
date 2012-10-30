#Fundamentals of Rikulo Gap

[Rikulo Gap](https://github.com/rikulo/rikulo-gap) is the package which bridge Dart program to [Apache Cordova/PhoneGap](http://phonegap.com/)'s JavaScript APIs. With the help of the Rikulo Gap package, Rikulo leverages and integrates with the mobile platform allowing you to create native mobile applications and get access to native device resources and services in Dart and HTML5.

>Apache Cordova/PhoneGap is a platform for building native mobile applications using HTML, CSS and JavaScript.

##What Services

Based on Cordova/PhoneGap, Rikulo Gap currently supports nine native device features across seven mobile platforms including Android, iOS, and others. Rikulo Gap bridge Dart to Cordova's JavaScript APIs and get accessed to the following nine native device services:

* [Accelerometer](gap:accelerometer): access to the device's motion sensor.
* [Camera](gap:camera): capture a photo with the device camera.
* [Capture](gap:capture): capture media files using device's media capture application.
* [Compass](gap:compass): access to the device's compass sensor.
* [Connection](gap:connection): fetch the device's connection information.
* [Contacts](gap:contacts): read/update/create/remove the device's contact list.
* [Device](gap:device): fetch the device's basic information.
* [Geolocation](gap:geolocation): access to the device's geographic location sensor.
* [Notification](gap:notification): trigger the device's visual/audible/vibration notifications.

##Installation

Rikulo Gap is not part of [Rikulo package](http://pub.dartlang.org/packages/rikulo).
You have to add this to your `pubspec.yaml` (or create it):

    dependencies:
      rikulo_gap:

Then run the [Pub Package Manager](http://pub.dartlang.org/doc) (comes with the Dart SDK):

    pub install

##Use the Services

* At first, download Cordova/PhoneGap platform and setup the environment.

* Then compile your Rikulo application from Dart to JavaScript such that it can be run on Cordova/PhoneGap on native mobile platforms.

    >Please refer to section [Building Native Mobile Application](../Getting_Started/Building_Native_Mobile_Application) for details about how to setup the Cordova/PhoneGap environment, compile the Rikulo application, and deploy and run on mobile devices.

* In your Rikulo application, before accessing any specific device services or resources, you have to enable the device accessibility first by calling the global method [enableDeviceAccess()](gap:device) which will initialize and enable the device services and make your Dart appliction ready to use Cordova's JavaScript APIs.

      import 'package:rikulo/view.dart';
      import 'package:rikulo_gap/device.dart';

      void main() {
        //enable the device
        Future<Device> enable = enableDeviceAccess();

        //when device is enabled and ready
        enable.then((device) { ... });

        //if failed to enable the device and/or time out!
        enable.handleException((ex) { ... }); 
      }

* After enabling the device's accessibility, you can use the global variables such as [device](gap:device) to access nine mobile services directly. For example, you can access the accelerometer by referring [accelerometer](gap:accelerometer). Then you can call the accelerometer's method to access or watch the motion sensor.

    >For details about how to use each supported device service, please refer to [API Reference](http://api.rikulo.org/rikulo-gap/latest/).


##A Simple Example

Following is an example that watch the [Acceleration](gap:accelerometer) information of the accelerometer motion sensor and shows the acceleration value in x, y and z axis every 1000 milliseconds. 

      import 'package:rikulo/view.dart';
      import 'package:rikulo_gap/device.dart';
      import 'package:rikulo_gap/accelerometer.dart';

      showAcceleration() {
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
