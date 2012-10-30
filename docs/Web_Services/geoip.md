#Rikulo GeoIP

Rikulo GeoIP is a Dart bridge to the on-line geo-ip JavaScript service provided by smart-ip.net.

##Services

* [SmartIP](geoip:smartip): Geo-IP service.

##Installation

Rikulo GeoIP is not part of [Rikulo package](http://pub.dartlang.org/packages/rikulo).
You have to add this to your `pubspec.yaml` (or create it):

    dependencies:
      rikulo_geoip:

Then run the [Pub Package Manager](http://pub.dartlang.org/doc) (comes with the Dart SDK):

    pub install

##A Simple Example

Following is an example that load the geo information per the caller's IP address.

import 'package:rikulo_geoip/smartip.dart';

    void main() {

      new SmartIP().loadIPGeoInfo()
        .then((Map result) => print(result));
    }