const security = require("../../SecurityFunctions");

let attachRoutes = function (app){

    let updateMyFarmDeviceId = require("./UpdateMyFarmDeviceId");
    app.put("/updatemyfarmdeviceid", updateMyFarmDeviceId.updateMyFarmDeviceId);

};

module.exports = {
    attachRoutes: attachRoutes
};
