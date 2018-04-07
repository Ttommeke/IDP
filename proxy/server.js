const express = require('express');
const proxy = require('http-proxy-middleware');
const path = require("path");

let port = process.env.PORT || 5000;

let addToProxy = function(appObject, proxyPath, name, serviceUrl) {
    appObject.use(proxyPath, proxy({
            target: "http://" + serviceUrl,
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

addToProxy(app, "/api/accountservice/", "accountservice", "account_service");
addToProxy(app, "/api/savegameservice/", "savegameservice", "savegame_service");
