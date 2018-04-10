const models = require("../../models/index");

let postAnswer = function (req, res) {

    models.Answer.create(req.body).then((answer) => {
        res.send(answer);
    }).catch(function(e) {
        console.log(e);
        res.status(404).send({ reason: "This is fucked up?!" });
    });
};

module.exports = {
    postAnswer: postAnswer
};
