"use strict";
module.exports = function(sequelize, DataTypes) {
    let FarmNotificationKeyCouple = sequelize.define("FarmNotificationKeyCouple", {
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

    FarmNotificationKeyCouple.prototype.toJSON = function() {
        let values = Object.assign({}, this.get());
        return values;
    };

    return FarmNotificationKeyCouple;
};
