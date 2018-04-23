package ruben.idpmonitoring.application.history;

import java.util.ArrayList;
import java.util.List;

public class History {
    private List<Record> records;

    public History(){
        this.records = new ArrayList<Record>();
    }

    public List<Record> getRecords(){
        return this.records;
    }

    public void addRecord(){

    }

    public void convertFromJson() {

    }

    private class JsonConverterAnswer {
        private String id;
        private String accountId;
        private String createdAt;
        private String updatedAt;
        private String PossibleAnswerOnQuestionId;

        public String getId() {
            return this.id;
        }

        public String getCreatedAt() {
            return this.createdAt;
        }

        public String getUpdatedAt() {
            return this.updatedAt;
        }

        public String getAccountId() {
            return accountId;
        }

        public String getPossibleAnswerOnQuestionId() {
            return PossibleAnswerOnQuestionId;
        }
    }

    private class JsonConverterQuestion{

    }
}
