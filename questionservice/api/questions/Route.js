const security = require("../../SecurityFunctions");

let attachRoutes = function (app){

    let getQuestions = require("./GetQuestions");
    app.get("/getquestions", getQuestions.getQuestions);

    let getAnswersOfPerson = require("./GetAnswersOfPerson");
    app.get("/getanswersofperson", getAnswersOfPerson.getAnswersOfPerson);

};

module.exports = {
    attachRoutes: attachRoutes
};
