const models = require("../../models/index");

let updateMyFarmDeviceId = function (req, res) {

    let objectToSave = {
        accountId: req.me.id,
        deviceId: req.body.deviceId
    };

    models.FarmNotificationKeyCouple.findOne({ where: { accountId: req.me.id } }).then(function(couple) {
        if (couple !== undefined && couple !== null) {
            couple.deviceId = req.body.deviceId;
            return couple.save();
        } else {
            return models.FarmNotificationKeyCouple.create(objectToSave);
        }
    }).then((couple) => {
        res.send(couple);
    }).catch(function(e) {
        console.log(e);
        res.status(404).send({ reason: "This is fucked up?!" });
    });
};

module.exports = {
    updateMyFarmDeviceId: updateMyFarmDeviceId
};
