const express = require("express");
const bodyparser = require("body-parser");
const app = express();
const securityFunctions = require("./SecurityFunctions");

app.use(bodyparser.urlencoded(
    {extended: false}
));

app.use(bodyparser.json());
app.use("/", securityFunctions.appendTokenToRequest);

app.get("/", function(req, res) {
    res.send("Has to be implemented...");
});

require("./api/feedback/Route").attachRoutes(app);

module.exports = app;
