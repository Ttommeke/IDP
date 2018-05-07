const models = require("../../models/index");
const Sequelize = require("sequelize");

let getQuestions = function (req, res) {

    models.Question.findAll({ include: [{ model: models.PossibleAnswerOnQuestion, order: Sequelize.col('answer'), as: 'PossibleAnswersOnQuestion' }] }).then((questions) => {
        res.send(questions);
    }).catch(function(e) {
        console.log(e);
        res.status(404).send({ reason: "This is fucked up?!" });
    });
};

module.exports = {
    getQuestions: getQuestions
};
