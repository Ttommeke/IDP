const routes = require("./Routes");
const models = require("./models/index");

const forced = false;

routes.listen(process.env.PORT, function () {
    console.log("feedback server running at: http://localhost:" + process.env.PORT + "/");
});
