const models = require("../../models/index");

let getAnswersOfPerson = function (req, res) {

    models.Answer.findAll({ include: [{ model: models.PossibleAnswerOnQuestion, include: [models.Question] }] }).then((answers) => {
        res.send(answers);
    }).catch(function(e) {
        console.log(e);
        res.status(404).send({ reason: "This is fucked up?!" });
    });
};

module.exports = {
    getAnswersOfPerson: getAnswersOfPerson
};
