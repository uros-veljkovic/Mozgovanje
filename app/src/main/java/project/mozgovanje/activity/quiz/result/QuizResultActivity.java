package project.mozgovanje.activity.quiz.result;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;

import java.util.ArrayList;
import java.util.List;

import project.mozgovanje.R;
import project.mozgovanje.activity.main.MainActivity;
import project.mozgovanje.activity.quiz.wrongquestions.WrongQuestionsActivity;
import project.mozgovanje.db.controller.RepositoryController;
import project.mozgovanje.model.question.Question;

import static project.mozgovanje.util.constants.Constants.ARRAY_LIST_CORRECT_QUESTIONS;
import static project.mozgovanje.util.constants.Constants.ARRAY_LIST_WRONG_QUESTIONS;

public class QuizResultActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Question> wrongQuestions = new ArrayList<>();
    private ArrayList<Question> correctQuestions = new ArrayList<>();
    private AnyChartView acvPieChart;
    private Button btnVidiPogresneOdgovore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        getQuestionsFromPreviousActivity();
        initActivityElements();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.more_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.btnLogout) {
            RepositoryController.getInstance().logout(this);
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(QuizResultActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void getQuestionsFromPreviousActivity() {
        Intent intent = getIntent();
        wrongQuestions = intent.getParcelableArrayListExtra(ARRAY_LIST_WRONG_QUESTIONS);
        correctQuestions = intent.getParcelableArrayListExtra(ARRAY_LIST_CORRECT_QUESTIONS);
    }

    private void initActivityElements() {
        initPieChart();
        initButton();
    }

    private void initButton() {
        btnVidiPogresneOdgovore = findViewById(R.id.activity_quiz_result_btnVidiPogresneOdgovore);
        btnVidiPogresneOdgovore.setOnClickListener(this);
    }

    private void initPieChart() {
        acvPieChart = findViewById(R.id.activity_quiz_result_acvPieChart);

        Pie pie = AnyChart.pie();

        addData(pie);
        configLabels(pie);

        acvPieChart.setChart(pie);
    }

    private void addData(Pie pie) {
        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Pogresni odgovori", wrongQuestions.size()));
        data.add(new ValueDataEntry("Tacni odgovori", correctQuestions.size()));
        pie.data(data);
    }

    private void configLabels(Pie pie) {

        pie.legend().title().enabled(true);
        pie.legend().title().fontSize(24);
        pie.legend().title()
                .text("Rezultat")
                .padding(0d, 0d, 10d, 0d);

        pie.labels().position("outside");
        pie.labels().fontSize(18);
        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);
        pie.legend().fontSize(15);

        configPieSlicesColors(pie);
    }

    private void configPieSlicesColors(Pie pie) {

        String red = "#DB4437";
        String green = "#0F9D58";
        String[] colors = {red, green};

        pie.palette(colors);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.activity_quiz_result_btnVidiPogresneOdgovore) {
            onBtnVidiPogresneOdgovore();
        }
    }

    private void onBtnVidiPogresneOdgovore() {
        Intent intent = new Intent(QuizResultActivity.this, WrongQuestionsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ARRAY_LIST_WRONG_QUESTIONS, wrongQuestions);
        bundle.putParcelableArrayList(ARRAY_LIST_CORRECT_QUESTIONS, correctQuestions);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}