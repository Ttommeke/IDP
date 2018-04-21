package ruben.idpmonitoring.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import java.util.ArrayList;

import ruben.idpmonitoring.Application;
import ruben.idpmonitoring.R;
import ruben.idpmonitoring.application.communication.Callback;
import ruben.idpmonitoring.application.questions.Answer;
import ruben.idpmonitoring.application.questions.Question;
import ruben.idpmonitoring.application.questions.QuestionType;
import ruben.idpmonitoring.application.questions.Session;

public class QuestionActivity extends AppCompatActivity {
    private RadioGroup radio_options;
    private RatingBar rating_options;
    private LinearLayout view_rating_options, view_radio_options;
    private Button btn_action;
    private TextView txt_progress, txt_question, txt_scale_low, txt_scale_high;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        loadQuestions();
        loadElements();
        startSession();
    }

    private void loadQuestions(){


        /*
        Application.getServerConnection().getQuestions(new Callback() {
            @Override
            public void taskCompleted(String results) {
                // Set session data
            }
        });
        */

        //Temp hardcoded questions
        this.session = new Session();

        ArrayList<String> q1Answers = new ArrayList<String>();
        q1Answers.add("Thuis");
        q1Answers.add("Werk/School");
        q1Answers.add("Onderweg");
        q1Answers.add("Ergens anders");
        Question q1 = new Question("1",QuestionType.Radio, "Waar ben ik?", q1Answers);
        ArrayList<String> q2Answers = new ArrayList<String>();
        q2Answers.add("Weinig");
        q2Answers.add("Veel");
        Question q2 = new Question("2", QuestionType.Rating, "Hoe actief was ik?", q2Answers);
        ArrayList<String> q3Answers = new ArrayList<String>();
        q3Answers.add("Partner");
        q3Answers.add("Familie");
        q3Answers.add("Vrienden");
        q3Answers.add("Collega's");
        q3Answers.add("Bekenden");
        Question q3 = new Question("3", QuestionType.Radio, "Met wie ben ik?", q3Answers);
        ArrayList<String> q4Answers = new ArrayList<String>();
        q4Answers.add("Weinig");
        q4Answers.add("Veel");
        Question q4 = new Question("4", QuestionType.Rating, "Hoe vermoeid ben ik?", q4Answers);
        ArrayList<String> q5Answers = new ArrayList<String>();
        q5Answers.add("Slecht");
        q5Answers.add("Goed");
        Question q5 = new Question("5", QuestionType.Rating, "Hoe voel ik me?", q5Answers);

        this.session.addQuestion(q1);
        this.session.addQuestion(q2);
        this.session.addQuestion(q3);
        this.session.addQuestion(q4);
        this.session.addQuestion(q5);
    }

    private void loadElements(){
        this.radio_options = (RadioGroup)this.findViewById(R.id.radio_options);
        this.rating_options = (RatingBar)this.findViewById(R.id.rating_options);
        this.view_rating_options = (LinearLayout) this.findViewById(R.id.view_rating_options);
        this.view_radio_options = (LinearLayout) this.findViewById(R.id.view_radio_options);
        this.btn_action = (Button)this.findViewById(R.id.btn_action);
        this.txt_progress = (TextView) this.findViewById(R.id.txt_progress);
        this.txt_question = (TextView) this.findViewById(R.id.txt_question);
        this.txt_scale_low = (TextView) this.findViewById(R.id.txt_scale_low);
        this.txt_scale_high = (TextView) this.findViewById(R.id.txt_scale_high);
    }

    private void startSession(){
        if(this.session.getTotalQuestions() != 0){
            this.setActiveQuestion();
        }
    }

    private void setActiveQuestion(){
        Question q = this.session.getCurrentQuestion();
        this.txt_question.setText(q.getContent());
        this.radio_options.removeAllViews();
        if(q.getType() == QuestionType.Radio){
            if(q.getPossibleAnswers() != null && q.getPossibleAnswers().size() != 0){
                for(int i = 0; i < q.getPossibleAnswers().size(); i++){
                    RadioButton rbtn = new RadioButton(this);
                    rbtn.setText(q.getPossibleAnswers().get(i));
                    rbtn.setChecked(false);
                    rbtn.setPadding(0, 50, 0, 50);
                    this.radio_options.addView(rbtn);
                }
            }

            this.view_rating_options.setVisibility(View.GONE);
            this.view_radio_options.setVisibility(View.VISIBLE);
        } else {
            this.txt_scale_low.setText(q.getPossibleAnswers().get(0));
            this.txt_scale_high.setText(q.getPossibleAnswers().get(1));
            this.rating_options.setRating(0);

            this.view_radio_options.setVisibility(View.GONE);
            this.view_rating_options.setVisibility(View.VISIBLE);
        }

        this.txt_progress.setText(Integer.toString(this.session.getQuestionIndex()+1).concat("/").concat(Integer.toString(this.session.getTotalQuestions())));
    }

    public void btnActionOnClick(View view){
        Question current_question = this.session.getCurrentQuestion();
        Answer answer = new Answer();
        if(current_question.getType() == QuestionType.Rating){
            answer.setContent(Float.toString(this.rating_options.getRating()));
        } else {
            int radio_button_id = radio_options.getCheckedRadioButtonId();
            View radio_button = radio_options.findViewById(radio_button_id);
            int index = radio_options.indexOfChild(radio_button);
            RadioButton rbtn = (RadioButton)  radio_options.getChildAt(index);
            answer.setContent(rbtn.getText().toString());
        }
        this.session.addAnswer(answer);

        this.session.nextQuestion();

        if(this.session.getCurrentQuestion() != null){
            if(session.isLastQuestion()){
                this.btn_action.setText("Klaar");
            }
            setActiveQuestion();
        } else {

            /*
            Application.getServerConnection().postAnswer("", "", new Callback() {
                @Override
                public void taskCompleted(String results) {
                    //Implement correct upload
                }
            });
            */

            Intent returnIntent = new Intent();
            returnIntent.putExtra("result","ditishetresultaat");
            this.setResult(Activity.RESULT_OK,returnIntent);
            this.finish();
        }
    }
}
