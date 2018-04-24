const models = require("../../models/index");

let getThresshold = function (req, res) {

    models.findOne({ where: { accountId: req.params.accountId } }).then(function(data) {
        if (data == undefined || data == null) {
            res.send({ heartRate: 70, steps: 1000 });
        } else {
            res.send(data);
        }
    }).catch(function(e) {
        console.log(e);
        res.status(400).send({ reason: "This is fucked up!" });
    });
};

module.exports = {
    getThresshold: getThresshold
};
