"use strict";
module.exports = function(sequelize, DataTypes) {
    let User = sequelize.define("User", {
        id: {
            type: DataTypes.UUID,
            defaultValue: DataTypes.UUIDV4,
            primaryKey: true
        },
        firstName: {
            type: DataTypes.STRING,
            validate: {
                notEmpty: true
            }
        },
        lastName: {
            type: DataTypes.STRING,
            validate: {
                notEmpty: true
            }
        },
        email: {
            type: DataTypes.STRING,
            validate: {
                notEmpty: true
            },
            unique: true
        },
        passwordHash: DataTypes.STRING,
        password: {
            type: DataTypes.VIRTUAL,
            validate: {
                isLongEnough: function (val) {
                    if (val.length < 6) {
                        throw new Error("Please choose a longer password.");
                    }
                }
            }
        }
    });

    User.prototype.toJSON = function() {
        let values = Object.assign({}, this.get());
        delete values.passwordHash;
        delete values.createdAt;
        delete values.updatedAt;
        return values;
    };

    return User;
};
