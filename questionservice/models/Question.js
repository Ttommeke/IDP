"use strict";
module.exports = function(sequelize, DataTypes) {
    let Question = sequelize.define("Question", {
        id: {
            type: DataTypes.UUID,
            defaultValue: DataTypes.UUIDV4,
            primaryKey: true
        },
        questionType: {
            type: DataTypes.STRING,
            validate: {
                notEmpty: true
            }
        },
        question: {
            type: DataTypes.STRING,
            validate: {
                notEmpty: true
            }
        }
    });

    Question.prototype.toJSON = function() {
        let values = Object.assign({}, this.get());
        return values;
    };

    Question.associate = function(models) {
        Question.PossibleAnswersOnQuestion = Question.hasMany(models.PossibleAnswerOnQuestion, { foreignKey: 'questionId', as: 'PossibleAnswersOnQuestion' });
    };

    return Question;
};
