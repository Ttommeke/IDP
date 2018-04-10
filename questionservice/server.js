const routes = require("./Routes");
const models = require("./models/index");

const forced = false;

models.Question.sync({forced: forced}).then(function() {
    return models.PossibleAnswerOnQuestion.sync({forced: forced});
}).then(function() {
    return models.Answer.sync({forced: forced});
}).then(function() {
    return models.Question.findAll();
}).then(function(questions) {
    if (questions.length == 0) {
        const fs = require("fs");

        fs.readFile("./seedQuestions.json", function(err, data) {
            arrayOfQuestions = JSON.parse(data).questions;

            for (var i = 0; i < arrayOfQuestions.length; i++) {
                models.Question.create(arrayOfQuestions[i], { include: [{ model: models.PossibleAnswerOnQuestion, as: 'PossibleAnswersOnQuestion' }] }).catch(function(err) {
                    console.log(err);
                });
            }
        });
    }
});

routes.listen(process.env.PORT, function () {
    console.log("Question server running at: http://localhost:" + process.env.PORT + "/");
});
