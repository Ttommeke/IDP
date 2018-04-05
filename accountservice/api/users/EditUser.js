const models = require("../../models/index");

let editUser = function (req, res) {
    let id = req.body.id;

    models.User.findById(id).then((user) => {
        if (user) {
            return user.updateAttributes(req.body);
        } else {
            res.status(404).send({ reason: "no user with that id" });
        }
    }).then(function(updatedUser) {
        res.send(updatedUser);
    }).catch(function(e) {
        console.log(e);
        res.status(404).send({ reason: "no user with that id" });
    });
};

module.exports = {
    editUser: editUser
};
