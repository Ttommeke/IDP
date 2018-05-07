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

        return getThresshold(req.me.id);
        console.log(questions);
    }).then(function(thresshold) {

        let subjectifWhat = false;
        let objWhatFactor = 1;
        let feeling = true;
        let happy = false;
        let objectifWhat = false;
        //subjectief what: "hoeveel heb je bewogen de laatste twee uur?"
        if (parseInt(getAnswerWithQuestionId(questions, "11e121bb-46b6-4f8f-acec-5280c4f1e4ac").PossibleAnswerOnQuestion.answer) > 3) {
            subjectifWhat = true;
        }
        //objectifWhatFactor: "Waar ben je op dit moment?"
        if (getAnswerWithQuestionId(questions, "76fdc098-dda6-4e20-b392-7cd793995359").PossibleAnswerOnQuestion.answer != "Thuis") {
            objWhatFactor = 1.2;
        }
        //hoe moe voelt hij/zij zich: "Hoe moe ben je, op een schaal van 1 tot 5?"
        if (parseInt(getAnswerWithQuestionId(questions, "42d264f9-a6d1-4b6f-b21f-6389cc264879").PossibleAnswerOnQuestion.answer) > 3) {
            feeling = false;
        }
        //are they happy: "Hoe gelukkig ben je, op een schaal van 1 tot 5?"
        if (parseInt(getAnswerWithQuestionId(questions, "4e782b0e-132d-4999-9420-d6470601aabb").PossibleAnswerOnQuestion.answer) > 3) {
            happy = true;
        }

        let deltaSteps = calculateDeltaSteps(fitness);

        if (objWhatFactor*deltaSteps > thresshold.steps || objWhatFactor*fitness[fitness.length - 1].heartrate > thresshold.heartRate) {
            objectifWhat = true;
        }


        //nu wordt het feest
        //heel veel ifkes

        if (objectifWhat) {
            if (subjectifWhat) {
                if (happy) {
                    if (feeling) {
                        //Perfect. Motiveer om door te doen zoals hij/zij bezig is & toon activiteit.
                        sendNotification({
                        	"accountId": req.me.id,
                        	"title": "Goe bezig!",
                        	"body": "Jij bent goed bezig! ;) Ga zo door!",
                        	"sound": "true",
                        	"app": "MONITOR",
                        	"data": {
                        		"just": "an object..."
                        	}
                        });
                    } else {
                        //Aanraden om te rusten. Toon de hoeveel de persoon al actief is geweest.
                        sendNotification({
                        	"accountId": req.me.id,
                        	"title": "Rust een beetje!",
                        	"body": "Jij bent goed bezig! ;) Rust een beetje en kijk maar eens hoeveel je al hebt gedaan vandaag.",
                        	"sound": "true",
                        	"app": "MONITOR",
                        	"data": {
                        		"action": "OPEN HISTOGRAM"
                        	}
                        });
                    }
                } else {
                    if (feeling) {
                        // Geluk heeft met de vermoeidheid niets te maken. (optie opmerking toevoegen)
                    } else {
                        //Geluk kan met vermoeidheid te maken hebben. Aanraden om te rusten.
                        sendNotification({
                        	"accountId": req.me.id,
                        	"title": "Rust een beetje!",
                        	"body": "Rust een beetje. Je bent al heel actief geweest vandaag! Daarna voek je je meteen beter.",
                        	"sound": "true",
                        	"app": "MONITOR",
                        	"data": {
                        		"action": "OPEN HISTOGRAM"
                        	}
                        });
                    }
                }
            } else {
                if (happy) {
                    if (feeling) {
                        //Toon aan hoe actief hij/zij werkelijk is geweest.
                        sendNotification({
                        	"accountId": req.me.id,
                        	"title": "Rust een beetje!",
                        	"body": "Jij bent goed bezig! ;) Rust een beetje en kijk maar eens hoeveel je al hebt gedaan vandaag.",
                        	"sound": "true",
                        	"app": "MONITOR",
                        	"data": {
                        		"action": "OPEN HISTOGRAM"
                        	}
                        });
                    } else {
                        //Aanraden om te rusten. Toon aan hoe actief hij/zij werkelijk is geweest.
                        sendNotification({
                        	"accountId": req.me.id,
                        	"title": "Rust een beetje!",
                        	"body": "Jij bent goed bezig! ;) Rust een beetje en kijk maar eens hoeveel je al hebt gedaan vandaag.",
                        	"sound": "true",
                        	"app": "MONITOR",
                        	"data": {
                        		"action": "OPEN HISTOGRAM"
                        	}
                        });
                    }
                } else {
                    if (feeling) {
                        //Gelukkig proberen te maken door aan te tonen hoe actief hij/zij is geweest.
                        sendNotification({
                        	"accountId": req.me.id,
                        	"title": "Jij bent goed bezig!",
                        	"body": "Jij bent goed bezig! ;) Kijk maar eens hoeveel je al hebt gedaan vandaag.",
                        	"sound": "true",
                        	"app": "MONITOR",
                        	"data": {
                        		"action": "OPEN HISTOGRAM"
                        	}
                        });
                    } else {
                        //Gelukkig proberen te maken door aan te tonen hoe actief hij/zij is geweest. Aanraden om te rusten.
                        sendNotification({
                        	"accountId": req.me.id,
                        	"title": "Rust een beetje!",
                        	"body": "Jij bent goed bezig! ;) Rust een beetje en kijk maar eens hoeveel je al hebt gedaan vandaag.",
                        	"sound": "true",
                        	"app": "MONITOR",
                        	"data": {
                        		"action": "OPEN HISTOGRAM"
                        	}
                        });
                    }
                }
            }
        } else {
            if (subjectifWhat) {
                if (happy) {
                    if (feeling) {
                        //Engaged houden aan spel
                    } else {
                        //Vermoeidheid heeft mentale oorzaak.
                    }
                } else {
                    if (feeling) {
                        //Pushen om uit de zetel te komen en te gaan wandelen. Push notificatie van wat er te vinden is in de buurt. Gelukkig proberen te maken aan de hand van de farmville. Extra in-game rewards
                        sendNotification({
                        	"accountId": req.me.id,
                        	"title": "Rust een beetje!",
                        	"body": "Jij bent goed bezig! ;) Rust een beetje en kijk maar eens hoeveel je al hebt gedaan vandaag.",
                        	"sound": "true",
                        	"app": "MONITOR",
                        	"data": {
                        		"action": "OPEN MAP"
                        	}
                        });
                    } else {
                        //Vermoeidheid/geluk heeft mentale oorzaak.
                    }
                }
            } else {
                if (happy) {
                    if (feeling) {
                        //Pushen om uit de zetel te komen en te gaan wandelen. Push notificatie van wat er te vinden is in de buurt.
                        sendNotification({
                        	"accountId": req.me.id,
                        	"title": "Rust een beetje!",
                        	"body": "Jij bent goed bezig! ;) Rust een beetje en kijk maar eens hoeveel je al hebt gedaan vandaag.",
                        	"sound": "true",
                        	"app": "MONITOR",
                        	"data": {
                        		"action": "OPEN MAP"
                        	}
                        });
                    } else {
                        //Pushen om uit de zetel te komen en te gaan wandelen. Push notificatie van wat er te vinden is in de buurt en een "bait" leggen.
                        sendNotification({
                        	"accountId": req.me.id,
                        	"title": "Rust een beetje!",
                        	"body": "Jij bent goed bezig! ;) Rust een beetje en kijk maar eens hoeveel je al hebt gedaan vandaag.",
                        	"sound": "true",
                        	"app": "MONITOR",
                        	"data": {
                        		"action": "LAY BAIT"
                        	}
                        });
                    }
                } else {
                    if (feeling) {
                        //Pushen om uit de zetel te komen en te gaan wandelen. Push notificatie van wat er te vinden is in de buurt. Gelukkig proberen te maken aan de hand van de farmville.
                        sendNotification({
                        	"accountId": req.me.id,
                        	"title": "Rust een beetje!",
                        	"body": "Jij bent goed bezig! ;) Rust een beetje en kijk maar eens hoeveel je al hebt gedaan vandaag.",
                        	"sound": "true",
                        	"app": "MONITOR",
                        	"data": {
                        		"action": "OPEN MAP"
                        	}
                        });
                    } else {
                        //Pushen om uit de zetel te komen en te gaan wandelen. Push notificatie van wat er te vinden is in de buurt en een "bait" leggen. Gelukkig proberen te maken aan de hand van de farmville.
                        sendNotification({
                        	"accountId": req.me.id,
                        	"title": "Rust een beetje!",
                        	"body": "Jij bent goed bezig! ;) Rust een beetje en kijk maar eens hoeveel je al hebt gedaan vandaag.",
                        	"sound": "true",
                        	"app": "MONITOR",
                        	"data": {
                        		"action": "LAY BAIT"
                        	}
                        });
                    }
                }
            }
        }


    }).catch(function(e) {
        console.log(e);
    });
};

let sendNotification = function( data) {
    return new Promise(function(resolve, reject) {
        const postData = JSON.stringify(data);

        const options = {
            hostname: 'localhost',
            port: 80,
            path: '/api/notificationservice/sendnotificationto/',
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
                    let parsedData = JSON.parse(rawData);
                    resolve(parsedData);
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

let calculateDeltaSteps = function(fitnesses) {
    if (fitnesses.length >= 2) {
        let delta = fitnesses[fitnesses.length - 1].steps - fitnesses[fitnesses.length - 2].steps;

        let differenceTimeInSeconds = (new Date(fitnesses[fitnesses.length - 1].measurementDate).getTime()) - (new Date(fitnesses[fitnesses.length - 2].measurementDate).getTime());

        return delta * 1000*60*60 / differenceTimeInSeconds;
    } else {
        return 0;
    }
}

let getAnswerWithQuestionId = function(questions, questionId) {
    for (let i = 0; i < questions.length; i++) {
        if (questions[i].PossibleAnswerOnQuestion.questionId == questionId) {
            return questions[i];
        }
    }
}

let getThresshold = function(accountId) {

    return new Promise(function(resolve, reject) {
        models.Thresshold.findOne({ where: { accountId: accountId } }).then(function(data) {
            if (data == undefined || data == null) {
                resolve({ heartRate: 70, steps: 1000 });
            } else {
                resolve(data);
            }
        }).catch(function(e) {
            reject(e);
        });
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
