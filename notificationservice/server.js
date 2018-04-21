const routes = require("./Routes");
const models = require("./models/index");

const forced = false;

models.FarmNotificationKeyCouple.sync({forced: forced});
models.QuestionNotificationKeyCouple.sync({forced: forced});

routes.listen(process.env.PORT, function () {
    console.log("notification server running at: http://localhost:" + process.env.PORT + "/");
});
