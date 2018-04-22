const security = require("../../SecurityFunctions");

let attachRoutes = function (app){

    let sendNotificationTo = require("./SendNotificationTo");
    app.post("/sendnotificationto", sendNotificationTo.sendNotificationTo);

};

module.exports = {
    attachRoutes: attachRoutes
};
