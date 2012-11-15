#The forEach Attribute

>    forEach="*each_name* in *collection_expression*"  
    forEach="*i = 0; i < 10; i++*"

The `forEach` attribute provides a compact way to iterate over a range of values. For example,

    <Template name="List" args="customers">
      <View forEach="customer in customers">
        $customer
      </View>

Here is a more complicated example:

    <Template name="ScrollViewTemplate" args="rows: 30, cols: 30">
      <ScrollView class="scroll-view"
      profile="location: center center; width: 80%; height: 80%">
        <Apply forEach="r = 0; r < rows; ++r">
          <Apply forEach="c = 0; c < cols; ++c">
            <View style="border: 1px solid #553; background-color: ${CSS.color(250 - r * 4, 250 - c * 4, 200)}"
                left="${r * 50 + 2}" top="${c * 50 + 2}"
                width="${46}" height="${46}">
            </View>
          </Apply>
        </Apply>
      </ScrollView>
    </Template>
