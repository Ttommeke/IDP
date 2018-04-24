const models = require("../../models/index");

let setThresshold = function (req, res) {

    models.Thresshold.findOne({ where: { accountId: req.body.accountId } }).then(function(thresshold) {
        if (thresshold !== undefined && thresshold !== null) {
            thresshold.heartRate = req.body.heartRate;
            thresshold.steps = req.body.steps;
            return thresshold.save();
        } else {
            return models.Thresshold.create(req.body);
        }
    }).then((thresshold) => {
        res.send(thresshold);
    }).catch(function(e) {
        console.log(e);
        res.status(400).send({ reason: "This is fucked up!" });
    });
};

module.exports = {
    setThresshold: setThresshold
};
