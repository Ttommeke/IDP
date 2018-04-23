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
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

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
        loadQuestions();
        setContentView(R.layout.activity_question);
    }

    private void loadQuestions(){

        this.session = new Session();

        Application.getServerConnection().getQuestions(new Callback() {
            @Override
            public void taskCompleted(int status_code, String json) {
                switch(status_code){
                    case 0:
                        Toast.makeText(getApplicationContext(), "Fout bij het maken van verbinding.", Toast.LENGTH_SHORT).show();
                        break;
                    case 200:
                        session.convertFromJson(json);
                        loadElements();
                        startSession();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "Onbekende fout.", Toast.LENGTH_SHORT).show();
                        finish();
                }
            }
        });

        //this.session.convertFromJson("[{\"id\":\"11e121bb-46b6-4f8f-acec-5280c4f1e4ac\",\"questionType\":\"MULTIPLECHOICE\",\"question\":\"whoopwhoop!\",\"createdAt\":\"2018-04-09T22:23:40.921Z\",\"updatedAt\":\"2018-04-09T22:23:40.921Z\",\"PossibleAnswersOnQuestion\":[{\"id\":\"d873b204-3a06-48b6-9fd6-f8afa3427806\",\"answer\":\"1\",\"createdAt\":\"2018-04-09T22:23:40.960Z\",\"updatedAt\":\"2018-04-09T22:23:40.960Z\",\"questionId\":\"11e121bb-46b6-4f8f-acec-5280c4f1e4ac\"},{\"id\":\"b26931e2-4519-402b-966c-fc5f3770845b\",\"answer\":\"2\",\"createdAt\":\"2018-04-09T22:23:40.962Z\",\"updatedAt\":\"2018-04-09T22:23:40.962Z\",\"questionId\":\"11e121bb-46b6-4f8f-acec-5280c4f1e4ac\"},{\"id\":\"1a7ac5d1-1928-44ca-90a7-d2bb4f49eb93\",\"answer\":\"4\",\"createdAt\":\"2018-04-09T22:23:40.963Z\",\"updatedAt\":\"2018-04-09T22:23:40.963Z\",\"questionId\":\"11e121bb-46b6-4f8f-acec-5280c4f1e4ac\"},{\"id\":\"a7df01a3-b94a-475a-918a-db998a0284c0\",\"answer\":\"3\",\"createdAt\":\"2018-04-09T22:23:40.962Z\",\"updatedAt\":\"2018-04-09T22:23:40.962Z\",\"questionId\":\"11e121bb-46b6-4f8f-acec-5280c4f1e4ac\"},{\"id\":\"7142af5d-0c82-4ffe-89ed-01e3f0b7a04a\",\"answer\":\"5\",\"createdAt\":\"2018-04-09T22:23:40.964Z\",\"updatedAt\":\"2018-04-09T22:23:40.964Z\",\"questionId\":\"11e121bb-46b6-4f8f-acec-5280c4f1e4ac\"}]}]");

        /*ArrayList<String> q1Answers = new ArrayList<String>();
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
        this.session.addQuestion(q5);*/
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
                    rbtn.setText(q.getPossibleAnswers().get(i).getContent());
                    rbtn.setChecked(false);
                    rbtn.setPadding(0, 50, 0, 50);
                    this.radio_options.addView(rbtn);
                }
            }

            this.view_rating_options.setVisibility(View.GONE);
            this.view_radio_options.setVisibility(View.VISIBLE);
        } else {
            this.txt_scale_low.setText(q.getPossibleAnswers().get(0).getContent());
            this.txt_scale_high.setText(q.getPossibleAnswers().get(1).getContent());
            this.rating_options.setRating(0);

            this.view_radio_options.setVisibility(View.GONE);
            this.view_rating_options.setVisibility(View.VISIBLE);
        }

        this.txt_progress.setText(Integer.toString(this.session.getQuestionIndex()+1).concat("/").concat(Integer.toString(this.session.getTotalQuestions())));

        if(this.session.isLastQuestion()){
            this.btn_action.setText("Klaar");
        }
    }

    public void btnActionOnClick(View view){
        Question current_question = this.session.getCurrentQuestion();
        Answer chosen_answer;
        if(current_question.getType() == QuestionType.Rating){
            chosen_answer = new Answer();
            chosen_answer.setContent(Float.toString(this.rating_options.getRating()));
        } else {
            int radio_button_id = radio_options.getCheckedRadioButtonId();

            View radio_button = radio_options.findViewById(radio_button_id);
            int index = radio_options.indexOfChild(radio_button);
            chosen_answer = current_question.getPossibleAnswers().get(index);
        }
        this.session.addAnswer(chosen_answer); //Not used at the moment

        Application.getServerConnection().postAnswer(Application.getUser().getId(), chosen_answer.getUuid(), new Callback() {
            @Override
            public void taskCompleted(int statusCode, String response) {
                switch(statusCode) {
                    case 200:
                        session.nextQuestion();

                        if(session.getCurrentQuestion() == null){
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("result","ditishetresultaat");
                            setResult(Activity.RESULT_OK,returnIntent);
                            finish();
                        } else {
                            setActiveQuestion();
                        }
                        break;
                    case 0:
                        Toast.makeText(getApplicationContext(), "Fout bij het maken van verbinding met de server.", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "Onbekende fout.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
}
