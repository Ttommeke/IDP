const express = require('express');

let port = 80;

const app = express();

app.get("/", function(req, res) {
    res.send("yeezazh");
});

app.listen(port, function() {
    console.log("proxy server running at http://localhost:" + port + "/");
});
