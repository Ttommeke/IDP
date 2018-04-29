package ruben.idpmonitoring.application.questions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private String uuid;
    private QuestionType type;
    private String content;
    private ArrayList<Answer> possible_answers;
    private int score;

    public Question(){

    }

    public Question(String uuid, QuestionType type, String content, ArrayList<Answer> possible_answers){
        this.uuid = uuid;
        this.type = type;
        this.content = content;
        this.possible_answers = possible_answers;
    }

   public String getUuid(){
        return this.uuid;
   }

   public void setUuid(String uuid){
        this.uuid = uuid;
   }

    public QuestionType getType(){
        return this.type;
    }

    public void setType(QuestionType type){
        this.type = type;
    }

    public String getContent(){
        return this.content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public ArrayList<Answer> getPossibleAnswers() {
        return this.possible_answers;
    }

    public void setPossibleAnswers(ArrayList<Answer> possible_answers){
        this.possible_answers = possible_answers;
    }

    public int getScore(){
        return this.score;
    }

    public void setScore(int score){
        this.score = score;
    }

    public static Question fromJson(String json){
        JsonParser parser = new JsonParser();
        JsonObject json_object = parser.parse(json).getAsJsonObject();
        JsonArray possible_answers = json_object.getAsJsonArray("PossibleAnswersOnQuestion");

        String id = json_object.get("id").getAsString();
        String content = json_object.get("question").getAsString();

        QuestionType question_type;
        if(json_object.get("questionType").getAsString().equals("MULTIPLECHOICE")){
            question_type = QuestionType.Radio;
        } else {
            question_type = QuestionType.Rating;
        }

        ArrayList<Answer> possible_answers_on_question = new ArrayList<>();

        if (possible_answers != null) {
            for (int i = 0; i < possible_answers.size(); i++){
                Answer answer = Answer.fromJson(possible_answers.get(i).getAsJsonObject().toString());
                possible_answers_on_question.add(answer);
            }
        }

        Question question = new Question();
        question.setUuid(id);
        question.setContent(content);
        question.setType(question_type);
        question.setPossibleAnswers(possible_answers_on_question);
        return question;
    }
}
