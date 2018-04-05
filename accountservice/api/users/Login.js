const models = require("../../models/index");
const bcrypt = require("bcrypt");
const jwt = require("jsonwebtoken");

let login = function (req, res) {
    let email = req.body.email;
    let passwordPlain = req.body.password;
    let userObject = undefined;
    let passwordHash = undefined;

    models.User.findOne({ where: {email: email} }).then((user) => {
        if (user) {
            userObject = user.toJSON();
            passwordHash = user.passwordHash;
            return bcrypt.compare(passwordPlain, passwordHash);
        } else {
            throw "User not found.";
        }
    }).then((matches) => {
        if (matches) {
            let token = jwt.sign(userObject, process.env.JWTSecret);
            res.send({ jwt: token });
        } else {
            throw "Password didn't match for user.";
        }
    }).catch(function(e) {
        console.log(e);
        res.status(401).send({ reason: "Invalid password or email! Your not getting in! You Bitchhhhh!" });
    });
};

module.exports = {
    login: login
};
