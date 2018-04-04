const express = require('express');
const proxy = require('http-proxy-middleware');
const path = require("path");

let port = process.env.PORT || 5000;

let addToProxy = function(appObject, proxyPath, name, port, serviceUrl) {
    appObject.use(proxyPath, proxy({
            target: "http://" + serviceUrl + ":" + port,
            pathRewrite: {
                ['^' + proxyPath] : '/',     // rewrite path
            },
        })
    );
};

const app = express();

app.get("/", function(req, res) {
    res.send("yeezazh boy....");
});

app.listen(port, function() {
    console.log("proxy server running at http://localhost:" + port + "/");
});

addToProxy(app, "/api/accountservice/", "accountservice", 80, "account_service");
