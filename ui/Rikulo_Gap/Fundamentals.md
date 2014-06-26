#Fundamentals of Rikulo Gap

[Rikulo Gap](https://github.com/rikulo/gap) is the package which bridge Dart program to [Apache Cordova](https://cordova.apache.org/)'s JavaScript APIs. With the help of the Rikulo Gap package, Rikulo leverages and integrates with the mobile platform allowing you to create native mobile applications and get access to native device resources and services in Dart and HTML5.

>Apache Cordova is a platform for building native mobile applications using HTML, CSS and JavaScript.

##What Services

Based on Apache Cordova, Rikulo Gap currently supports nine native device features across seven mobile platforms including Android, iOS, and others. Rikulo Gap bridge Dart to Cordova's JavaScript APIs and get accessed to the following nine native device services:

* [Accelerometer](gap:accelerometer): capture device motion in the x, y, and z direction.
* [Camera](gap:camera): provide access to the device's default camera application.
* [Capture](gap:capture): provide access to the device's audio, image, and video capture capabilities.
* [Compass](gap:compass): obtain the direction that the device is pointing.
* [Connection](gap:connection): provide information about the device's cellular and wifi connection.
* [Contacts](gap:contacts): provide access to the device contacts database.
* [Device](gap:device): describe the device's hardware and software.
* [Geolocation](gap:geolocation): provide access to location data based on the device's GPS sensor or inferred from network signals.
* [Notification](gap:notification): visual, audible, and tactile device notifications.

##Installation

Rikulo Gap is not part of [Rikulo package](http://pub.dartlang.org/packages/rikulo).
You have to add this to your `pubspec.yaml` (or create it):

    dependencies:
      rikulo_gap:

Then, If you’re using [Dart Editor](http://www.dartlang.org/docs/editor/), select “Pub Get” from the “Tools” menu. If you’re rocking the command line, do:

    pub get

For more information, please refer to [Pub: Getting Started](http://pub.dartlang.org/doc).

##Use the Services

* At first, download [Apache Cordova](http://cordova.apache.org/) platform and setup the environment.

* Then compile your Rikulo application from Dart to JavaScript such that it can be run on Cordova on native mobile platforms.

    >Please refer to section [Building Native Mobile Application](../Getting_Started/Building_Native_Mobile_Application) for details about how to setup the Cordova environment, compile the Rikulo application, and deploy and run on mobile devices.

* In your Rikulo application, before accessing any specific device services or resources, you have to enable the device accessibility first by calling the global method [Device.init()](gap:device) which will initialize and enable the device services and make your Dart appliction ready to use Cordova's JavaScript APIs.

        import 'package:rikulo_gap/device.dart';
        
        void main() {
          //enable the device
          Device.init()
          .then((Device device) {
             //Access the device
              ...
          })
          .catchError((ex) {
             //Fail to enable the device
              ...
          });
        }

* After enabling the device's accessibility, you can use the global variables such as [device](gap:device) to access nine mobile services directly. For example, you can access the accelerometer by referring [accelerometer](gap:accelerometer). Then you can call the accelerometer's method to access or watch the motion sensor.

    >For details about how to use each supported device service, please refer to [API Reference](http://api.rikulo.org/gap/latest/).


##A Simple Example

Following is an example that watch the [Acceleration](gap:accelerometer) information of the accelerometer motion sensor and shows the acceleration value in x, y and z axis every 1000 milliseconds. 

      import 'package:rikulo_gap/device.dart';
      import 'package:rikulo_gap/accelerometer.dart';
      
      //At a regular interval, get the acceleration along the x, y, and z axis.
      void accessAccelerometer() {
        var id = accelerometer.watchAcceleration(
          (Acceleration acc) {
            print("t:${acc.timestamp}\n"
                 +"x:${acc.x}\n"
                 +"y:${acc.y}\n"
                 +"z:${acc.z}\n");
          },
          () => print("Fail to get acceleration."),
          new AccelerometerOptions(frequency: 1000)
        );
      }
      
      void main() {
        Device.init()
        .then((Device device) {
           accessAccelerometer();
        })
        .catchError((ex) {
           print("Fail to enable the device.");
        });
      }