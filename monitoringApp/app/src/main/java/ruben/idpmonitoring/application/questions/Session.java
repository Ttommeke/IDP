package ruben.idpmonitoring.application.questions;

import java.util.ArrayList;

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
}
