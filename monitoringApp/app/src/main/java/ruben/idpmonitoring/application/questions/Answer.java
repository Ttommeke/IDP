package ruben.idpmonitoring.application.questions;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Answer {
    private String uuid;
    private QuestionType type;
    private String content;

    public Answer(){

    }

    public void setUuid(String uuid){
        this.uuid = uuid;
    }

    public String getUuid(){
        return this.uuid;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return this.content;
    }

    public void setType(QuestionType type){
        this.type = type;
    }

    public QuestionType getType() {
        return this.type;
    }

    public static Answer fromJson(String json){
        JsonParser parser = new JsonParser();
        JsonObject json_object = parser.parse(json).getAsJsonObject();

        Answer answer = new Answer();
        answer.setUuid(json_object.get("id").getAsString());
        answer.setContent(json_object.get("answer").getAsString());

        return answer;
    }
}
