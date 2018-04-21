const security = require("../../SecurityFunctions");

let attachRoutes = function (app){

    let updateMyQuestionDeviceId = require("./UpdateMyQuestionDeviceId");
    app.put("/updatemyquestiondeviceid", updateMyQuestionDeviceId.updateMyQuestionDeviceId);

};

module.exports = {
    attachRoutes: attachRoutes
};
