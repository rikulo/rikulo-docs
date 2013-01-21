#Request Routing and Handling

##Request Handers

A *request handler* is a function that processes a particular phase of a request. A request handler must have one argument typed [HttpConnect](api:stream).

It can have any number of named arguments. In additions, it can optionally return an URI to forward the request to. Here are some examples:

    void listCustomers(HttpConnect connect);
    void placeOrder(HttpConnect connect, {OrderInfo info});
    String login(HttpConnect connect);

##HttpConnect

##Request Routing
