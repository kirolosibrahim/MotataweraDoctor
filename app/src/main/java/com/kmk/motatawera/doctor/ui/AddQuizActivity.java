package com.kmk.motatawera.doctor.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.Timestamp;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kmk.motatawera.doctor.R;
import com.kmk.motatawera.doctor.databinding.ActivityAddQuizBinding;
import com.kmk.motatawera.doctor.model.QuizModel;
import com.kmk.motatawera.doctor.storage.SharedPrefManager;

public class AddQuizActivity extends AppCompatActivity {

    ActivityAddQuizBinding binding;
    FirebaseFirestore db;
    String Title, subject_id, doctor_id, subject_name, dep, lvl, branch, QuizID;
    int subject_branch, subject_department, subject_grad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_quiz);
        db = FirebaseFirestore.getInstance();

        //Get Subject Data From Adapter


        binding.BtnCancleMakeQuiz.setOnClickListener(v -> finish());

        //set subject data
        binding.txtBranch.setText(getString(R.string.place) + branch);
        binding.txtSubject.setText(getString(R.string.subject) + ":" + subject_name);
        getSubjectdata();
        binding.BtnContinueMakeQuiz.setOnClickListener(v -> val());

    }

    private void val() {
        Title = binding.editTextQuizTitle.getText().toString();

        DocumentReference documentReference = db.collection("quiz").document();

        QuizID = documentReference.getId();


        QuizModel quizModel = new QuizModel();
        quizModel.setId(QuizID);
        quizModel.setTitle(Title);
        quizModel.setDoctor_id(doctor_id);
        quizModel.setSubject_id(subject_id);
        quizModel.setQuizActive(false);
        quizModel.setQuizFinished(false);

        if (Title.isEmpty()) {
        } else {
            documentReference.set(quizModel).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, QuizID, Toast.LENGTH_SHORT).show();
                    Toast.makeText(AddQuizActivity.this, R.string.quiz_added_uccessfully, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddQuizActivity.this, QuestionsListActivity.class)
                            .putExtra("QuizID", QuizID)
                            .putExtra("title", Title)
                            .putExtra("subject_name", subject_name)
                            .putExtra("subject_branch", subject_branch);
                    startActivity(intent);
                    finish();
                }
            });


        }
    }


    //get Subject Data from Adapter
    private void getSubjectdata() {
        subject_id = getIntent().getStringExtra("subject_id");
        doctor_id = SharedPrefManager.getInstance().getUser(this).getId();
        subject_name = getIntent().getStringExtra("subject_name");
        subject_branch = getIntent().getIntExtra("subject_branch", 0);
        subject_department = getIntent().getIntExtra("subject_department", 0);
        subject_grad = getIntent().getIntExtra("subject_grad", 0);


        if (subject_department == 1)
            dep = "Management Information System";
        else if (subject_department == 2)
            dep = "Commerce";
        if (subject_grad == 1)
            lvl = "Level 1";
        else if (subject_grad == 2)
            lvl = "Level 2";
        else if (subject_grad == 3)
            lvl = "Level 3";
        else if (subject_grad == 4)
            lvl = "Level 4";
        if (subject_branch == 1)
            branch = "Haram";
        else
            branch = "Qtamia";
    }


}