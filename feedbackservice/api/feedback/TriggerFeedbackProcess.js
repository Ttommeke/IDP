const models = require("../../models/index");
const http = require("http");

let triggerFeedbackProcess = function (req, res) {

    res.send("Ok!");

    let fitness = undefined;
    let questions = undefined;

    makeHttpGetRequest("http://fitness_service/getfitnessofperson/" + req.me.id).then((data) => {
        fitness = data;

        return makeHttpGetRequest("http://question_service/getanswersofperson/" + req.me.id);
    }).then(function(data) {
        questions = data;
        console.log(questions);
    }).catch(function(e) {
        console.log(e);
    });
};

let makeHttpGetRequest = function(options, body) {

    return new Promise(function(resolve, reject) {

        let req = http.get( options, function(res) {

            res.setEncoding('utf8');
            let rawData = '';
            res.on('data', (chunk) => { rawData += chunk; });
            res.on('end', () => {
                try {
                    let parsedData = JSON.parse(rawData);
                    resolve(parsedData);
                } catch (e) {
                    reject(e.message);
                }
            });
        }).on('error', (e) => {
            reject('problem with request: ${e.message}');
        });
    });

}

module.exports = {
    triggerFeedbackProcess: triggerFeedbackProcess
};
