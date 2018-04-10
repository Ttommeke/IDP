const security = require("../../SecurityFunctions");

let attachRoutes = function (app){

    let postFitness = require("./PostFitness");
    app.post("/postfitness", postFitness.postFitness);

    let getFitnessOfPerson = require("./GetFitnessOfPerson");
    app.get("/getfitnessofperson/:personid", getFitnessOfPerson.getFitnessOfPerson);

};

module.exports = {
    attachRoutes: attachRoutes
};
