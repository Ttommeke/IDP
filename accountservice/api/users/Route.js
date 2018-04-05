const security = require("../../SecurityFunctions");

let isSameUserOrAdmin = (req, res, next) => {
    if (req.me.isAdmin || req.me.id == req.body.id) {
        next();
    } else {
        res.status(401).send({"Reason": "Not allowed to edit users"});
    }
};

let attachRoutes = function (app){

    let create = require("./Create");
    app.post("/create", create.create);

    let login = require("./Login");
    app.post("/login", login.login);

    let getUserById = require("./GetUserById");
    app.get("/getuserbyid/:id", getUserById.getUserById);

    let getAllUsers = require("./GetAllUsers");
    app.get("/getallusers", getAllUsers.getAllUsers);

    let editUser = require("./EditUser");
    app.put("/edituser", security.isLoggedIn);
    app.put("/edituser", isSameUserOrAdmin);
    app.put("/edituser", editUser.editUser);

};

module.exports = {
    attachRoutes: attachRoutes
};
