const security = require("../../SecurityFunctions");

let attachRoutes = function (app){

    let getQuestions = require("./GetQuestions");
    app.get("/getquestions", getQuestions.getQuestions);

};

module.exports = {
    attachRoutes: attachRoutes
};
