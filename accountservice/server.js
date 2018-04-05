const routes = require("./Routes");
const models = require("./models/index");

models.User.sync({forced: false});

routes.listen(process.env.PORT, function () {
    console.log("Account server running at: http://localhost:" + process.env.PORT + "/");
});
