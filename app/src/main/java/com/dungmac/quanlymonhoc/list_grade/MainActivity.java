package com.dungmac.quanlymonhoc.list_grade;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dungmac.quanlymonhoc.list_subject_of_grade.GradeSubjectActivity;
import com.dungmac.quanlymonhoc.MyDB;
import com.dungmac.quanlymonhoc.R;
import com.dungmac.quanlymonhoc.model.Grade;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvGrade;
    private EditText etSearch;
    //ArrayList chua du lieu cho listview
    MyDB mysqlitedb;

    //để lưu dữ liệu danh sách các Grade
    //khai báo một ArrayList<Grade>
    ArrayList<Grade> listGrade;
    //Adapter của listview hiển thị danh sách Grade
    MyGradeAdapter listGradeAdapter;

    private Button btnAdd, btnDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.homepage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mysqlitedb = new MyDB(this, "QuanLyMonHoc", null, 3);

        etSearch = findViewById(R.id.etSearchClass);
        btnAdd = findViewById(R.id.btnAdd);
        btnDel = findViewById(R.id.btnDel);
        lvGrade = findViewById(R.id.lvClass);

        listGrade = new ArrayList<>();
        listGrade = mysqlitedb.getAllGrade();
        //Tạo Adapter để đặt dữ liệu cho listview
        listGradeAdapter = new MyGradeAdapter(this, listGrade);
        //Gắn Addapter vào cho listview
        lvGrade.setAdapter(listGradeAdapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                listGradeAdapter.getFilter().filter(s.toString());
                listGradeAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        handleButton();

        lvGrade.setOnItemClickListener((parent, view, position, id) -> {
            // Get the selected grade item
            Grade selectedGrade = listGrade.get(position);

            // Create an intent to start the new activity (GradeSubjectActivity)
            Intent intent = new Intent(MainActivity.this, GradeSubjectActivity.class);

            // Pass the selected grade data to the new activity
            intent.putExtra("GradeId", selectedGrade.getId());
            intent.putExtra("GradeName", selectedGrade.getName());

            // Start the new activity
            startActivity(intent);
        });

    }

    private void handleButton() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tạo đối tượng Intent để gọi tới AddNew
                Intent intent = new Intent(MainActivity.this,
                        GradeActivity.class);
                startActivityForResult(intent, 100);
            }
        });
        btnDel.setOnClickListener(view -> {
            ArrayList<Grade> removeList = new ArrayList<Grade>();
            // xoá user nếu giá trị kiểm tra của mỗi phần tử là true
            for (int i = 0; i < listGrade.size(); i++) {
                if (listGrade.get(i).getStatus()) {
                    mysqlitedb.deleteGrade(listGrade.get(i).getId());
                    removeList.add(listGrade.get(i));
                }
            }

            for (int i = 0; i < removeList.size(); i++) {
                listGrade.remove(removeList.get(i));
            }
            listGradeAdapter.notifyDataSetChanged();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 400 || resultCode == -1) {
            return;
        }
        //lấy dữ liệu từ NewContact gửi về
        Bundle bundle = data.getExtras();
        int id = bundle.getInt("Id");
        String name = bundle.getString("Name");
        if (requestCode == 100 && resultCode == 200) {
            //đặt vào listData
            listGrade.add(new Grade(name));
            mysqlitedb.addGrade(new Grade(name));
        }
        listGradeAdapter.notifyDataSetChanged();
        lvGrade.setAdapter(listGradeAdapter);
    }
}