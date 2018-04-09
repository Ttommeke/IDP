const routes = require("./Routes");
const models = require("./models/index");

const forced = true;

models.Question.sync({forced: forced});
models.PossibleAnswerOnQuestion.sync({forced: forced});
models.Answer.sync({forced: forced});

if (forced) {
    const fs = require("fs");

    fs.readFile("./seedQuestions.json", function(err, data) {
        arrayOfQuestions = JSON.parse(data).questions;

        for (var i = 0; i < arrayOfQuestions.length; i++) {
            models.Question.create(arrayOfQuestions[i]);
        }
    });
}

routes.listen(process.env.PORT, function () {
    console.log("Account server running at: http://localhost:" + process.env.PORT + "/");
});
