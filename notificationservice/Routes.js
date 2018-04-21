const express = require("express");
const bodyparser = require("body-parser");
const app = express();
const securityFunctions = require("./SecurityFunctions");

app.use(bodyparser.urlencoded(
    {extended: false}
));

app.use(bodyparser.json());
app.use("/", securityFunctions.appendTokenToRequest);

require("./api/questionNotificationCouple/Route").attachRoutes(app);
require("./api/farmNotificationCouple/Route").attachRoutes(app);

app.get("/", function(req, res) {
    res.send("Has to be implemented...");
});

module.exports = app;
