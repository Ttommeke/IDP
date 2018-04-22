const models = require("../../models/index");

let updateMyQuestionDeviceId = function (req, res) {

    let objectToSave = {
        accountId: req.me.id,
        deviceId: req.body.deviceId
    };

    models.QuestionNotificationKeyCouple.findOne({ where: { accountId: req.me.id } }).then(function(couple) {
        if (couple !== undefined && couple !== null) {
            console.log(req.body);
            couple.deviceId = req.body.deviceId;
            return couple.save();
        } else {
            return models.QuestionNotificationKeyCouple.create(objectToSave);
        }
    }).then((Questioncouple) => {
        res.send(Questioncouple);
    }).catch(function(e) {
        console.log(e);
        res.status(404).send({ reason: "This is fucked up?!" });
    });
};

module.exports = {
    updateMyQuestionDeviceId: updateMyQuestionDeviceId
};
