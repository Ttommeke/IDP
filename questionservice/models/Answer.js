"use strict";
module.exports = function(sequelize, DataTypes) {
    let Answer = sequelize.define("Answer", {
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
        }
    });

    Answer.prototype.toJSON = function() {
        let values = Object.assign({}, this.get());
        return values;
    };

    Answer.associate = function(models) {
        Answer.PossibleAnswerOnQuestion = Answer.belongsTo(models.PossibleAnswerOnQuestion);
    };

    return Answer;
};
