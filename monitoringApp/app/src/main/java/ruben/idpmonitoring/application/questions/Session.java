package ruben.idpmonitoring.application.questions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Keeps track of current question session
 */
public class Session {
    private ArrayList<Question> questions;
    private ArrayList<Answer> answers;
    private int current_question;
    private boolean last_question = false;

    public Session(){
        this.questions = new ArrayList<Question>();
        this.answers = new ArrayList<Answer>();
        this.current_question = 0;
    }

    public Session(ArrayList<Question> questions){
        this.questions = questions;
        this.answers = new ArrayList<Answer>();
        this.current_question = 0;
    }

    public void addQuestion(Question question){
        this.questions.add(question);
    }

    public void addAnswer(Answer answer){
        this.answers.add(answer);
    }

    public void nextQuestion(){
        this.current_question += 1;
    }

    public Question getCurrentQuestion(){
        if(this.current_question < this.questions.size()){
            if(this.current_question + 1 == this.questions.size()) this.last_question = true;
            return this.questions.get(this.current_question);
        } else {
            return null;
        }
    }

    public boolean isLastQuestion(){
        return this.last_question;
    }

    public int getQuestionIndex(){
        return this.current_question;
    }

    public int getTotalQuestions(){
        if(this.questions != null){
            return this.questions.size();
        } else {
            return 0;
        }
    }

    public void convertFromJson(String json) {
        Gson gson = new Gson();
        List<JsonConverterQuestion> questions = gson.fromJson(json, new TypeToken<List<JsonConverterQuestion>>() {
        }.getType());

        if (questions != null && questions.size() > 0) {
            for (JsonConverterQuestion json_question : questions) {
                Question question = new Question();
                question.setUuid(json_question.getId());
                if (json_question.getQuestionType().equals("MULTIPLECHOICE")) {
                    question.setType(QuestionType.Radio);
                } else {
                    question.setType(QuestionType.Rating);
                }

                question.setContent(json_question.getQuestion());

                ArrayList<Answer> possible_answers = new ArrayList<Answer>();
                if (json_question.getPossibleAnswersOnQuestion() != null && json_question.getPossibleAnswersOnQuestion().size() > 0) {
                    for (JsonConverterPossibleAnswer json_possible_answer : json_question.getPossibleAnswersOnQuestion()) {
                        Answer answer = new Answer();
                        answer.setUuid(json_possible_answer.getId());
                        answer.setContent(json_possible_answer.getAnswer());
                        if (json_question.getQuestionType().equals("MULTIPLECHOICE")) {
                            answer.setType(QuestionType.Radio);
                        } else {
                            answer.setType(QuestionType.Rating);
                        }

                        this.answers.add(answer);
                        possible_answers.add(answer);
                    }
                }

                question.setPossibleAnswers(possible_answers);
                this.questions.add(question);
            }
        }
    }

    private class JsonConverterQuestion {
        private String id;
        private String questionType;
        private String question;
        private String createdAt;
        private String updatedAt;
        private List<JsonConverterPossibleAnswer> PossibleAnswersOnQuestion;

        public String getId() {
            return this.id;
        }

        public String getQuestionType() {
            return this.questionType;
        }

        public String getQuestion() {
            return this.question;
        }

        public String getCreatedAt() {
            return this.createdAt;
        }

        public String getUpdatedAt() {
            return this.updatedAt;
        }

        public List<JsonConverterPossibleAnswer> getPossibleAnswersOnQuestion() {
            return this.PossibleAnswersOnQuestion;
        }
    }

    private class JsonConverterPossibleAnswer {
        private String id;
        private String answer;
        private String createdAt;
        private String updatedAt;
        private String questionId;

        public String getId() {
            return this.id;
        }

        public String getAnswer() {
            return this.answer;
        }

        public String getCreatedAt() {
            return this.createdAt;
        }

        public String getUpdatedAt() {
            return this.updatedAt;
        }

        public String getQuestionId() {
            return this.questionId;
        }
    }
}
