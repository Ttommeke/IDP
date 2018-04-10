let jwt = require("jsonwebtoken");

let appendTokenToRequest = function(req, res, next) {
    let leMe = {};

    try {
        leMe = jwt.verify(req.headers.token, process.env.JWTSecret);
        req.me = leMe;
    } catch (err) {
        leMe = { "error" : err };
        req.me = undefined;
    }

    next();
};

let isLoggedIn = function(req, res, next) {
    if (req.me !== undefined) {
        next();
    } else {
        res.status(403).send({"reason": "Not logged in"});
    }
};

module.exports = {
    appendTokenToRequest: appendTokenToRequest,
    isLoggedIn: isLoggedIn
};
