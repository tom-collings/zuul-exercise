This repository is to demonstrate a route filter implementation with zuul.

This route filter will route to `<prefix>-local.<domain>` if a cookie named `controller-version` is not supplied.  If that cookie is supplied, then the request will be routed to `<prefix>-<cookie value>.<domain>`.

The simpleController service will add the cookie to the response.

To run the example, build the simpleController project and deploy to PCF (example routes in PCF dev) using the provided manifest. 

Then set routes for the applications using the `cf create-route` command:

```script
cf create-route zuul-controller local.pcfdev.io --hostname zuul-controller-1
cp create-route zuul-controller local.pcfdev.io --hostname zuul-controller-latest
```

In a terminal, tail the logs for zuul-controller.

Now deploy the simpleRouter application and hit the `zuul-router.local.pcfdev.io/version` endpoint in a browser.  Verify the following:

1.  The logs for zuul-controller show a GET action against the zuul-controller-latest endpoint.
1.  The browser has a controller-version cookie with a value of 1.

Now hit the same endpoint in the same browser and verify:

1.  The logs for zuul-controller show a GET action against the zuul-controller-1 endpoint.

Now modify the application.properties for the simpleController, setting the simple.controller.version property value to 2 and deploy using the manifest-newer.yml file.  Lastly, set some routes:

```script
cf create-route zuul-controller-newer local.pcfdev.io --hostname zuul-controller-2
cp unmap-route zuul-controller local.pcfdev.io --hostname zuul-controller-latest
cp create-route zuul-controller-newer local.pcfdev.io --hostname zuul-controller-latest
```


This action simulates two versions of the same service running in PCF at the same time, where they have routes including the version number and the most recent version has a route including the string `latest`.

In a new terminal, tail the logs from the zuul-controller-newer application.

Now deploy the simpleRouter application and hit the `zuul-router.local.pcfdev.io/version` endpoint in a different browser.  Verify that:

1.  The logs for zuul-controller-newer show a GET action against the zuul-contoller-latest endpoint.
1.  The browser has a controller-version cookie with a value of 2.

Now if you hit the endpoint again (in the second browser) you should see:

1.  The logs for zuul-controller-newer show a GET operation against the zuul-controller-2 endpoint.

And if you hit the endpoint in the first browser:

1.  The logs for zuul-controller show a GET operation against the zuul-controller-1 endpoint.

This entire activity shows that once a session is active against a version of the service, it will continue to hit that same service version as long as its cookie is active.  Further, all new requests (that is, ones without cookies) will be routed to the `latest` version of the service.
