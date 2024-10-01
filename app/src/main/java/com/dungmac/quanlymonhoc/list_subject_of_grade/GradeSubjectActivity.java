package com.dungmac.quanlymonhoc.list_subject_of_grade;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dungmac.quanlymonhoc.MyDB;
import com.dungmac.quanlymonhoc.R;
import com.dungmac.quanlymonhoc.model.Subject;

import java.util.ArrayList;

public class GradeSubjectActivity extends AppCompatActivity {

    private ListView lvSubjects;
    private EditText etSearchSubject;
    private Button btnAddSubject, btnDeleteSubject;

    private ArrayList<Subject> subjectList;
    private MySubjectAdapter subjectAdapter;
    private MyDB myDB;
    private int gradeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_grade_subject);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.grade_subject), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Toolbar toolbar = findViewById(R.id.toolbar_grade_subject);

        setSupportActionBar(toolbar);

        // Enable the back button in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        gradeId = getIntent().getIntExtra("GradeId", -1);
        String gradeName = getIntent().getStringExtra("GradeName");

        toolbar.setTitle(gradeName);

        toolbar.setNavigationOnClickListener(v -> {
            setResult(-1);
            finish();
        });


        lvSubjects = findViewById(R.id.lvSubjects);
        etSearchSubject = findViewById(R.id.etSearchSubject);
        btnAddSubject = findViewById(R.id.btnAddSubject);
        btnDeleteSubject = findViewById(R.id.btnDeleteSubject);

        myDB = new MyDB(this, "QuanLyMonHoc", null, 3);

        // Load subjects for the grade
        loadSubjects();

        // Add new subject
        btnAddSubject.setOnClickListener(v -> {
            Intent intent = new Intent(GradeSubjectActivity.this, AddUpdateSubjectActivity.class);
            intent.putExtra("GradeId", gradeId);
            startActivityForResult(intent, 101); // 101 for adding new subject
        });

        // Delete selected subjects
        btnDeleteSubject.setOnClickListener(v -> deleteSelectedSubjects());

        // Handle search
        etSearchSubject.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                subjectAdapter.getFilter().filter(s.toString());
                subjectAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Handle long press for edit/delete
        lvSubjects.setOnItemLongClickListener((parent, view, position, id) -> {
            Subject selectedSubject = subjectList.get(position);
            showEditDeleteMenu(selectedSubject);
            return true;
        });
    }

    private void loadSubjects() {
        subjectList = myDB.getAllSubjectsForGrade(gradeId);
        subjectAdapter = new MySubjectAdapter(this, subjectList);
        lvSubjects.setAdapter(subjectAdapter);
    }

    private void deleteSelectedSubjects() {
        ArrayList<Subject> removeList = new ArrayList<Subject>();
        for (int i = 0; i < subjectList.size(); i++) {
            if (subjectList.get(i).getStatus()) {
                myDB.deleteSubject(subjectList.get(i).getId());
                removeList.add(subjectList.get(i));
            }
        }

        for (int i = 0; i < removeList.size(); i++) {
            subjectList.remove(removeList.get(i));
        }

        subjectAdapter.notifyDataSetChanged();
    }

    private void showEditDeleteMenu(Subject subject) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Options");
        builder.setItems(new CharSequence[]{"Edit", "Delete"}, (dialog, which) -> {
            if (which == 0) {
                // Edit subject
                Intent intent = new Intent(GradeSubjectActivity.this, AddUpdateSubjectActivity.class);
                intent.putExtra("Subject", subject);
                startActivityForResult(intent, 102); // 102 for updating subject
            } else if (which == 1) {
                // Delete subject
                myDB.deleteSubject(subject.getId());
                loadSubjects();
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            loadSubjects();
        }
    }

}