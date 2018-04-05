const models = require("../../models/index");
const bcrypt = require("bcrypt");

let create = function (req, res) {
    return new Promise(function(resolve, reject) {
        bcrypt.hash(req.body.password, 12, function(err, hash) {
            resolve(hash);
        });
    }).then(function(hash) {
        req.body.passwordHash = hash;
        return models.User.create(req.body);
    }).then(function(user) {
        res.send(user);
    }).catch(function(e) {
        console.log(e);
        res.status(400).send({ reason: "Bad input, propably email that already exists or password of less then 6 characters." });
    });
};

module.exports = {
    create: create
};
