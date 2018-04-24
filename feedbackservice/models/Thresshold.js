"use strict";
module.exports = function(sequelize, DataTypes) {
    let Thresshold = sequelize.define("Thresshold", {
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
        heartRate: {
            type: DataTypes.INTEGER,
            validate: {
                notEmpty: true
            }
        },
        steps: {
            type: DataTypes.INTEGER,
            validate: {
                notEmpty: true
            }
        }
    });

    return Thresshold;
};
