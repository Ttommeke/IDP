const routes = require("./Routes");
const models = require("./models/index");

models.Question.sync({forced: false});
models.PossibleAnswerOnQuestion.sync({forced: false});
models.Answer.sync({forced: false});

routes.listen(process.env.PORT, function () {
    console.log("Account server running at: http://localhost:" + process.env.PORT + "/");
});
