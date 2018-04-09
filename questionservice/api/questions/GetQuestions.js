const models = require("../../models/index");

let getQuestions = function (req, res) {

    models.Question.findAll({ include: [ models.PossibleAnswerOnQuestion ] }).then((questions) => {
        res.send(questions);
    }).catch(function(e) {
        console.log(e);
        res.status(404).send({ reason: "This is fucked up?!" });
    });
};

module.exports = {
    getQuestions: getQuestions
};
