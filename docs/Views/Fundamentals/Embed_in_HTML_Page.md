#Embed in HTML Page

By default, [Activity.mainView](api:app) will occupy the whole screen. In other words, it assumes the main view is the only UI element that the user will interact with.

However, for a Web application, it is common to have a header, a footer and even several banners. They are usually better to be done in pure HTML and CSS since they are static and can be implemented by a designer who might not be familiar with Dart.

To do so, you can implement with HTML (or any server-side technology, such as JSP) and then embed Rikulo views to the place that rich UI is required. For example,

![Embed in HTML page](embedInHTMLPage.png?raw=true)
