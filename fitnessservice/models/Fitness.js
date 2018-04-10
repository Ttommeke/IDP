"use strict";
module.exports = function(sequelize, DataTypes) {
    let Fitness = sequelize.define("Fitness", {
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
        measurementDate: {
            type: DataTypes.DATE,
            defaultValue: sequelize.NOW,
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

    Fitness.prototype.toJSON = function() {
        let values = Object.assign({}, this.get());
        return values;
    };

    Fitness.associate = function(models) {

    };

    return Fitness;
};
