"use strict";
module.exports = function(sequelize, DataTypes) {
    let QuestionNotificationKeyCouple = sequelize.define("QuestionNotificationKeyCouple", {
        id: {
            type: DataTypes.UUID,
            defaultValue: DataTypes.UUIDV4,
            primaryKey: true
        },
        accountId: {
            type: DataTypes.UUID,
            validate: {
                notEmpty: true
            }
        },
        deviceId: {
            type: DataTypes.STRING,
            validate: {
                notEmpty: true
            }
        }
    });

    QuestionNotificationKeyCouple.prototype.toJSON = function() {
        let values = Object.assign({}, this.get());
        return values;
    };

    return QuestionNotificationKeyCouple;
};
