#Form Handling

When handling the data of a submitted form, you can use [ObjectUtil.inject()](http://api.rikulo.org/commons/latest/rikulo_mirrors/ObjectUtil.html#inject) to convert the query parameters into an object.

For example, assume we have a form as follows:

    <form action="search">
      <table>
        <tr>
          <td colspan="2">Search</td>
        </tr>
        <tr>
          <td colspan="2"><input name="text" autofocus="true" size="60"/></td>
        </tr>
        <tr>
          <td>Since</td>
          <td><input name="since" type="date"/></td>
        </tr>
        <tr>
          <td>Within</td>
          <td><input name="within" type="number"/> days</td>
        </tr>
        <tr>
          <td colspan="2"><input type="checkbox" name="hasAttachment" id="hatt"/>
            <label for="hatt">Has attachment</label></td>
        </tr>
        <tr>
          <td colspan="2" align="right"><input type="submit"></td>
        </tr>
      </table>
    </form>

Then, you can implement a class called `Criteria` to hold the information as follows:

    class Criteria {
      String text = "";
      DateTime since;
      int within;
      bool hasAttachment = false;
    }

Then, you can implement a request handling for the action called `search` in the above example as follows:

    void search(HttpConnect connect) {
      ObjectUtil.inject(new Criteria(), connect.request.queryParameters, silent: true)
        .then((criteria) {
          searchResult(connect, criteria: criteria);
        }).catchError(connect.error);
    }

> For a runnable example, you can refer to the [features](source:test) example.

> For more complicated examples of injection, you can refer to [these examples](https://github.com/rikulo/commons/blob/master/test/inject.dart).

##Custom Coercion

By default, [ObjectUtil.inject](http://api.rikulo.org/commons/latest/rikulo_mirrors/ObjectUtil.html#inject) will coerce the basic types, such as `int`, `num`, `double`, `String`, `bool`, `DateTime`, and [Color](http://api.rikulo.org/commons/latest/rikulo_util/Color.html) automatically. If you need to coerce the custom types, you can implement the coercion and pass to the `coerce` parameter.

##Validation

You can validate the values in a closure by passing it to the `validate` parameter when calling [ObjectUtil.inject](http://api.rikulo.org/commons/latest/rikulo_mirrors/ObjectUtil.html#inject).

> Notice that the validation is called after the value is coerced successfully, and before it is assigned to the target object.