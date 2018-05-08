package ruben.idpmonitoring.activities.questions;

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

import ruben.idpmonitoring.Application;
import ruben.idpmonitoring.R;
import ruben.idpmonitoring.application.communication.Callback;
import ruben.idpmonitoring.application.history.ObjectiveMeasurement;
import ruben.idpmonitoring.application.questions.Answer;
import ruben.idpmonitoring.application.questions.Question;
import ruben.idpmonitoring.application.questions.QuestionType;
import ruben.idpmonitoring.application.questions.Session;

public class QuestionActivity extends AppCompatActivity {
    static final int OPEN_GAME_DIALOG_REQUEST = 200;

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

        if(Application.getServerConnection() != null){
            Application.getServerConnection().getQuestions(new Callback() {
                @Override
                public void taskCompleted(int status_code, String json) {
                    switch(status_code){
                        case 0:
                            Toast.makeText(getApplicationContext(), "Fout bij het maken van verbinding.", Toast.LENGTH_SHORT).show();
                            break;
                        case 200:
                            session.convertQuestionsFromJson(json);
                            loadElements();
                            startSession();
                            break;
                        default:
                            Toast.makeText(getApplicationContext(), "Onbekende fout.", Toast.LENGTH_SHORT).show();
                            finish();
                    }
                }
            });
        } else {

            Toast.makeText(this, "Fout bij het maken van verbinding", Toast.LENGTH_SHORT).show();
        }
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
            this.txt_scale_low.setText("1");

            this.txt_scale_high.setText("5");

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
        this.postAnswer(chosen_answer);
    }

    private void postAnswer(Answer answer){
        Application.getServerConnection().postAnswer(Application.getUser().getId(), answer.getUuid(), new Callback() {
            @Override
            public void taskCompleted(int statusCode, String response) {
                switch(statusCode) {
                    case 200:
                        session.nextQuestion();

                        if(session.getCurrentQuestion() == null){
                            Intent intent = new Intent(QuestionActivity.this, OpenGameDialog.class);
                            intent.putExtra("information","Open nu het spel om van voordelen te genieten.");
                            startActivityForResult(intent, OPEN_GAME_DIALOG_REQUEST);
                            postObjectiveMeasurement();
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

    private void postObjectiveMeasurement(){
        Application.getSmartwatchConnection().getObjectiveMeasurement(new Callback() {
            @Override
            public void taskCompleted(int status_code, String json) {
                Application.getServerConnection().postObjectiveMeasurement(Application.getUser().getId(),
                        ObjectiveMeasurement.fromJson(json), null);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OPEN_GAME_DIALOG_REQUEST) {
            switch(resultCode){
                case RESULT_OK:
                    Intent intent_game = getPackageManager().getLaunchIntentForPackage("com.farm.game");
                    if (intent_game != null) { //check in case package name was not found
                        startActivity(intent_game);
                    }
                    finish();
                    break;
                case RESULT_CANCELED:
                    finish();
                    break;
            }
        }
        Application.getServerConnection().triggerFeedbackProcess(null);
    }
}
