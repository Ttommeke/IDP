const routes = require("./Routes");
const models = require("./models/index");

const forced = false;

models.Fitness.sync({forced: forced});

routes.listen(process.env.PORT, function () {
    console.log("Fitness server running at: http://localhost:" + process.env.PORT + "/");
});
