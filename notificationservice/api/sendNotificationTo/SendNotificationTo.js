const models = require("../../models/index");
const firebase = require('firebase');
const https = require('https');

let sendNotificationTo = function (req, res) {

    if (req.body.accountId == "ALL") {
        req.body.data.notification = {
            "title": req.body.title,
            "body": req.body.body,
            "sound": req.body.sound
        };

        sendHttpRequestToFirebase({
            "to": "/topics/allMonitoringDevices",
            data: req.body.data
        });

        res.send("Ok!");
    } else {
        getDeviceId(req.body.accountId, req.body.app).then(function(couple) {
            sendHttpRequestToFirebase({
                "to": couple.deviceId,
                data: req.body.data
            });

            res.send("Ok!");
        }).catch(function(err) {
            console.log(err);
            res.status(404).send({ reason: "This is fucked up?!" });
        });
    }


};

let getDeviceId = function(accountId, app) {

    return new Promise(function(resolve, reject) {
        if (app == "FARM") {
            models.FarmNotificationKeyCouple.findOne({ where: { accountId: accountId } }).then(function(couple) {
                resolve(couple);
            });
        } else if (app == "MONITOR") {
            models.QuestionNotificationKeyCouple.findOne({ where: { accountId: accountId } }).then(function(couple) {
                resolve(couple);
            });
        } else {
            reject("'app' property has to be 'MONITOR' of 'FARM'.");
        }
    });

};

let sendHttpRequestToFirebase = function(body) {
    let options = {
        host: 'fcm.googleapis.com',
        port: 443,
        path: '/fcm/send',
        method: 'POST',
        headers: {
            "Content-Type":"application/json",
            "Authorization":"key=" + process.env.FIREBASEAPIKEY
        }
    };

    let req = https.request( options, function(res) {

    });

    req.on('data', function(data) {
        console.log(data);
    });

    req.on('error', (e) => {
        console.log(`problem with request: ${e.message}`);
    });

    req.write(JSON.stringify(body));
    req.end();
}

module.exports = {
    sendNotificationTo: sendNotificationTo
};
