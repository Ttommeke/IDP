const security = require("../../SecurityFunctions");

let attachRoutes = function (app){

    let postAnswer = require("./PostAnswer");
    app.post("/postanswer", postAnswer.postAnswer);

    let getQuestions = require("./GetQuestions");
    app.get("/getquestions", getQuestions.getQuestions);

    let getAnswersOfPerson = require("./GetAnswersOfPerson");
    app.get("/getanswersofperson/:personid", getAnswersOfPerson.getAnswersOfPerson);

};

module.exports = {
    attachRoutes: attachRoutes
};
