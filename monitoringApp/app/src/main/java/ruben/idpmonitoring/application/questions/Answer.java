package ruben.idpmonitoring.application.questions;

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
}
