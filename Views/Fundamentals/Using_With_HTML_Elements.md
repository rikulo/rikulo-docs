#Using With HTML Elements

You can use [TextView](http://rikulo.org/api/_/view/TextView.html) embed web content in your application. To do so, you simply instantiate it with the `html` constructor and then attach it to the hierarchy tree of views.

    view.addChild(new TextView.html('''
      <ul>
      <li>Welcome to Rikulo</li>
      </ul>
    '''));

Furthermore, you can change the content dynamically by setting the `html` property.

##Replace Web Content with Views
