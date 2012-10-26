#Rikulo YAPI

Rikulo YAPI is a Dart bridge to those frequently used on-line JavaScript services provided by Yahoo Inc. 

##Services

* [YPlaceFinder](yapi:yapi): load geo information per the specified location.
* [YWeather](yapi:yapi): Load current/forecast weather information per the specified woeid(Where On Earth ID).

##Installation

Rikulo YAPI is not part of [Rikulo package](http://pub.dartlang.org/packages/rikulo).
You have to add this to your `pubspec.yaml` (or create it):

    dependencies:
      rikulo_yapi:

Then run the [Pub Package Manager](http://pub.dartlang.org/doc) (comes with the Dart SDK):

    pub install

##A Simple Example

Following is an example that load the weather inforamation of the San Francisco city with `YPlaceFinder` and `YWeather` service. 

    import 'package:rikulo_yapi/rikulo_yapi.dart';
    
    void main() {
    	//get geoinfo of San Francisco
    	Future<Map> geoinfo = 
    		new YPlaceFinder().loadGeoInfo({'location' : 'San+Francisco,+CA'});

      geoinfo.then((Map result) {
        //retrieve woeid of San Francisco
        String woeid = result['ResultSet']['Result']['woeid']; 

        //use the woeid to retrieve the wheather info of San Francisco
        new YWeather(woeid).loadWeatherInfo()
          .then((Map result) => print(result));
      });
    }
