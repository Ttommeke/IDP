const models = require("../../models/index");

let getFitnessOfPerson = function (req, res) {

    models.Fitness.findAll({
        where: { accountId: req.params.personid }
    }).then((fitnesses) => {
        res.send(fitnesses);
    }).catch(function(e) {
        console.log(e);
        res.status(404).send({ reason: "This is fucked up?!" });
    });
};

module.exports = {
    getFitnessOfPerson: getFitnessOfPerson
};
