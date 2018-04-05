const express = require("express");
const bodyparser = require("body-parser");
const app = express();
const securityFunctions = require("./SecurityFunctions");

app.use(bodyparser.urlencoded(
    {extended: false}
));

app.use(bodyparser.json());
app.use("/", securityFunctions.appendTokenToRequest);

require("./api/users/Route").attachRoutes(app);
module.exports = app;
