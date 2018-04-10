const models = require("../../models/index");

let getAnswersOfPerson = function (req, res) {

    models.Answer.findAll({
        where: { accountId: req.params.personid },
        include: [{
            model: models.PossibleAnswerOnQuestion,
            include: [{
                model: models.Question,
                include: [{
                    model: models.PossibleAnswerOnQuestion,
                    as: 'PossibleAnswersOnQuestion'
                }] 
            }]
        }]
    }).then((answers) => {
        res.send(answers);
    }).catch(function(e) {
        console.log(e);
        res.status(404).send({ reason: "This is fucked up?!" });
    });
};

module.exports = {
    getAnswersOfPerson: getAnswersOfPerson
};
