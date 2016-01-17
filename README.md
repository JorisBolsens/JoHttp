# JoHttp
[![Build Status](https://travis-ci.org/JorisBolsens/JoHttp.svg?branch=master)](https://travis-ci.org/JorisBolsens/JoHttp)

A replacement for Volley with deprecation (apache http) removed. 
Includes fixes and optimizations not present in Volley.

## Usage
Using JoHttp is very simple. Simply instantiate a `RequestQueue`, start the queue, then add a `Request<>` to the queue.

### Sending a simple request
```java
RequestQueue queue = JoHttp.newRequestQueue(context);
queue.start()

JsonObjectRequest request = new JsonObjectRequest(Method.GET, "http://jojolabs.com", null, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            //handle response
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(HttpError error) {
                                            //handle error
                                        }
                                    });
queue.add(request)
```

### Canceling a request
```java
//Method 1: cancel a single request
request.cancel();

//Method 2: cancel by tag
//You need to have set a tag on your request (by calling setTag(object))
//This will cancel all requests with that tag
queue.cancelAll("myTag");
```

### Custom requests
JoHttp already has a few default possible requests, a `JsonObjectRequest` and a `JsonArrayRequest`, a `StringRequest`, and an `ImageRequest`.
It is also fairly simple to create your own custom request. Simply extend the `Request` class, and override a few methods, most importantly `parseNetworkResponse()`, this is what will take the raw response from the server and transform it into the `Object` you want.
