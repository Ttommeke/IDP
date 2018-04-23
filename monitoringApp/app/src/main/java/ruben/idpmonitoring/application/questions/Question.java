package ruben.idpmonitoring.application.questions;

import java.util.ArrayList;

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
}
