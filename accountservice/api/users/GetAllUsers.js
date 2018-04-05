const models = require("../../models/index");

let getAllUsers = function (req, res) {

    models.User.findAll().then((users) => {
        res.send(users);
    }).catch(function(e) {
        console.log(e);
        res.status(404).send({ reason: "This is fucked up?!" });
    });
};

module.exports = {
    getAllUsers: getAllUsers
};
