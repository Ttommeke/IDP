"use strict";
module.exports = function(sequelize, DataTypes) {
    let Answer = sequelize.define("Answer", {
        id: {
            type: DataTypes.UUID,
            defaultValue: DataTypes.UUIDV4,
            primaryKey: true
        },
        questionId: {
            type: DataTypes.UUID,
            validate: {
                notEmpty: true
            }
        },
        accountId: {
            type: DataTypes.UUID,
            validate: {
                notEmpty: true
            }
        },
        answerId: {
            type: DataTypes.UUID,
            validate: {
                notEmpty: true
            }
        }
    });

    Answer.prototype.toJSON = function() {
        let values = Object.assign({}, this.get());
        return values;
    };

    return Answer;
};
