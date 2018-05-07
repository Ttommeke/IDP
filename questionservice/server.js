const routes = require("./Routes");
const models = require("./models/index");
const schedule = require('node-schedule');
const http = require('http');

const forced = false;

models.Question.sync({forced: forced}).then(function() {
    return models.PossibleAnswerOnQuestion.sync({forced: forced});
}).then(function() {
    return models.Answer.sync({forced: forced});
}).then(function() {
    return models.Question.findAll();
}).then(function(questions) {
    if (questions.length == 0) {
        const fs = require("fs");

        fs.readFile("./seedQuestions.json", function(err, data) {
            arrayOfQuestions = JSON.parse(data).questions;

            for (var i = 0; i < arrayOfQuestions.length; i++) {
                models.Question.create(arrayOfQuestions[i], { include: [{ model: models.PossibleAnswerOnQuestion, as: 'PossibleAnswersOnQuestion' }] }).catch(function(err) {
                    console.log(err);
                });
            }
        });
    }
});

let sendNotification = function( data) {
    return new Promise(function(resolve, reject) {
        const postData = JSON.stringify(data);

        const options = {
            hostname: 'notification_service',
            port: 80,
            path: '/sendnotificationto/',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Content-Length': Buffer.byteLength(postData)
            }
        };

        const req = http.request(options, (res) => {
            res.setEncoding('utf8');
            let rawData = '';
            res.on('data', (chunk) => { rawData += chunk; });
            res.on('end', () => {
                try {
                    resolve(rawData);
                } catch (e) {
                    reject(e.message);
                }
            });
        });

        req.on('error', (e) => {
            reject(e.message);
        });

        // write data to request body
        req.write(postData);
        req.end();

    });
}

let fillInQuestions = schedule.scheduleJob('*/10 * * * *', function(){
    console.log("send notification");
    let possibleQuestions = [
        "Hoe voel je je op dit moment?",
        "Waar ben je momenteel?",
        "Heb je al veel gedaan vandaag?"
    ];

    let selectedQuestion = possibleQuestions[Math.floor(possibleQuestions.length * Math.random())];

    sendNotification({
        "accountId": "ALL",
        "title": selectedQuestion,
        "body": "Vul een paar van onze vragen in.",
        "sound": "true",
        "app": "MONITOR",
        "data": {
            "action": "QUESTION"
        }
    }).catch(function(err) {
        console.log(err);
    });
});

routes.listen(process.env.PORT, function () {
    console.log("Question server running at: http://localhost:" + process.env.PORT + "/");
});
