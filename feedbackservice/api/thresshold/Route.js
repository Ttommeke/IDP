const security = require("../../SecurityFunctions");

let attachRoutes = function (app){

    let setThresshold = require("./SetThresshold");
    app.post("/setthresshold", setThresshold.setThresshold);

    let getThresshold = require("./GetThresshold");
    app.get("/getthresshold/:accountId", getThresshold.getThresshold);

};

module.exports = {
    attachRoutes: attachRoutes
};
