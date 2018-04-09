"use strict";
module.exports = function(sequelize, DataTypes) {
    let PossibleAnswerOnQuestion = sequelize.define("PossibleAnswerOnQuestion", {
        id: {
            type: DataTypes.UUID,
            defaultValue: DataTypes.UUIDV4,
            primaryKey: true
        },
        answer: {
            type: DataTypes.STRING,
            validate: {
                notEmpty: true
            }
        }
    });

    PossibleAnswerOnQuestion.prototype.toJSON = function() {
        let values = Object.assign({}, this.get());
        return values;
    };

    PossibleAnswerOnQuestion.associate = function(models) {

    };

    return PossibleAnswerOnQuestion;
};
