#Fundamentals of Device Services

Rikulo leverages and integrates with [Cordova/PhoneGap](http://phonegap.com/) platform allowing you to create native mobile applications and get access to native device resources and services in Dart and HTML5.

##What Services

Based on Cordova/PhoneGap, Rikulo currently supports nine native device features across seven mobile platforms including Android, iOS, and others. Rikulo bridge Dart to Cordova's JavaScript APIs and get accessed to the following nine native device services:

* [Accelerometer](api:device/accelerometer): access to the device's motion sensor.
* [Camera](api:device/camera): capture a photo with the device camera.
* [Capture](api:device/capture): capture media files using device's media capture application.
* [Compass](api:device/compass): access to the device's compass sensor.
* [Connection](api:device/connection): fetch the device's connection information.
* [Contacts](api:device/contacts): read/update/create/remove the device's contact list.
* [Device](api:device): fetch the device's basic information.
* [XGeolocation](api:device/geolocation): access to the device's geographic location sensor.
* [XNotification](api:device/notification): trigger the device's visual/audible/vibration notifications.

##Use the Services

* At first, download Cordova/PhoneGap platform and setup the environment.

* Then compile your Rikulo application from Dart to JavaScript such that it can be run on Cordova/PhoneGap on native mobile platforms.

    >Please refer to section [Building Native Mobile Application](../Getting_Started/Building_Native_Mobile_Application) for details about how to setup the Cordova/PhoneGap environment, compile the Rikulo application, and deploy and run on mobile devices.

* In your Rikulo application, before accessing any specific device services, you have to enable the device accessibility first by calling the global method [enableDeviceAccess()](api:device) which enables Rikulo device service code to use Cordova/PhoneGap JavaScript APIs.

        #import('client/app/app.dart');
        #import('client/view/view.dart');
        #import('client/device/device.dart');

        class HelloWorld extends Activity {
          void onCreate_() {
            ...
          }
        }

        void main() {
          enableDeviceAccess();
          new HelloWorld().run();
        }

* After enabling the device's accessibility, you can use the global variable [device](api:device) to access nine device services directly. For example, you can access the accelerometer by referring [Device.accelerometer](api:device). Then you can register device event listener or call the accelerometer's method to access the motion sensor. 

    >For details about how to use each supported device service, please refer to [API Reference](http://api.rikulo.org).


##A Simple Example

Following is an example that listens to the AccelerationEvent of the  accelerometer motion sensor and shows the acceleration value in x, y and z axis every 1000 milliseconds. 

    #import('client/app/app.dart');
    #import('client/view/view.dart');
    #import('client/device/device.dart');
    #import('client/device/accelerometer/accelerometer.dart');

    class HelloWorld extends Activity {
      void onCreate_() {
        TextView welcome = new TextView("Hello Accelerometer!");
        welcome.profile.text = "anchor:  parent; location: center center";
        mainView.addChild(welcome);

        //listen to mobile accelerometer event and show the acceleration in x, y and z axis.
        device.accelerometer.on.accelerate.add((AccelerationEvent event) {
          welcome.text = 
            "${event.acceleration.timeStamp},\n"
            "x:${event.acceleration.x},\n"
            "y:${event.acceleration.y},\n"
            "z:${event.acceleration.z}";
          welcome.requestLayout(true);
        }, options: new AccelerometerOptions(frequency:1000));
      }
    }

    void main() {
      enableDeviceAccess();
      new HelloWorld().run();
    }
