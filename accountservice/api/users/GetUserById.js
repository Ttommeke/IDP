const models = require("../../models/index");

let getUserById = function (req, res) {
    let id = req.params.id;

    models.User.findById(id).then((user) => {
        if (user) {
            res.send(user);
        } else {
            res.status(404).send({ reason: "no user with that id" });
        }
    }).catch(function(e) {
        console.log(e);
        res.status(404).send({ reason: "no user with that id" });
    });
};

module.exports = {
    getUserById: getUserById
};
