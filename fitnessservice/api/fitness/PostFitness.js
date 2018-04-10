const models = require("../../models/index");

let postFitness = function (req, res) {

    models.Fitness.create(req.body).then((fitness) => {
        res.send(fitness);
    }).catch(function(e) {
        console.log(e);
        res.status(404).send({ reason: "This is fucked up?!" });
    });
};

module.exports = {
    postFitness: postFitness
};
