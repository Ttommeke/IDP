const express = require('express');
const proxy = require('http-proxy-middleware');
const path = require("path");
const configuration = require("./libraries/Configuration");
const { exec, spawn, fork } = require('child_process');

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

app.use("/", express.static(__dirname + "/web"));
app.use("/node_modules", express.static(__dirname + "/node_modules"));

//account service

app.listen(port, function() {
    console.log("proxy server running at " + configuration.BaseURL + port + "/");
});

const accountServiceConfig = require('./accountservice/Configuration');
addToProxy(app, "/api/accountservice/", "accountservice", accountServiceConfig.port, "account_service");
const budgetServiceConfig = require('./budgetservice/Configuration');
addToProxy(app, "/api/budgetservice/", "budgetservice", budgetServiceConfig.port, "budget_service");
const processServiceConfig = require('./processservice/Configuration');
addToProxy(app, "/api/processservice/", "processservice", processServiceConfig.port, "process_service");
const productServiceConfig = require('./productservice/Configuration');
addToProxy(app, "/api/productservice/", "productservice", productServiceConfig.port, "product_service");
addToProxy(app, "/api/rightsservice/", "rightsservice", 3005, "rights_api");
