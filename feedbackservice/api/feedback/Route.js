const security = require("../../SecurityFunctions");

let attachRoutes = function (app){

    let triggerFeedbackProcess = require("./TriggerFeedbackProcess");
    app.post("/triggerfeedbackprocess", triggerFeedbackProcess.triggerFeedbackProcess);

};

module.exports = {
    attachRoutes: attachRoutes
};
