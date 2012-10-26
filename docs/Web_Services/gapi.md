#Rikulo GAPI

Rikulo GAPI is a Dart bridge to those frequently used on-line JavaScript services provided by Google. 

##Services

* [GAnalytics](gapi:gapi): Google Analytics service.
* [GFeed](gapi:gapi): Google Feed reader service.
* [GLoader](gapi:gapi): Google API loading service.

##Installation

Rikulo GAPI is not part of [Rikulo package](http://pub.dartlang.org/packages/rikulo).
You have to add this to your `pubspec.yaml` (or create it):

    dependencies:
      rikulo_gapi:

Then run the [Pub Package Manager](http://pub.dartlang.org/doc) (comes with the Dart SDK):

    pub install

##A Simple Example

Following is an example that load the feed from the Digg's. with `GFeed` service. 

    import 'package:rikulo_gapi/rikulo_gapi.dart';

    void main() {
      // Create a feed instance that will grab Digg's feed.
      GFeed feed = new GFeed("http://www.digg.com/rss/index.xml");

      // Retrieve feed information as a Map
      Future<Map> info = feed.loadFeedInfo();

      info.then((Map result) => print(result));
    }
