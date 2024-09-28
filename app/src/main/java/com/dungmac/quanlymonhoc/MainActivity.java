package com.dungmac.quanlymonhoc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvContact;
    private EditText etSearch;
    //ArrayList chua du lieu cho listview
    MyDB mysqlitedb;

    //để lưu dữ liệu danh sách các Grade
    //khai báo một ArrayList<Grade>
    ArrayList<Grade> listGrade;

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

        btnAdd = findViewById(R.id.btnAdd);
        btnDel = findViewById(R.id.btnDel);

        handleButton();
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
//            // xoá user nếu giá trị kiểm tra của mỗi phần tử là true
//            for (int i = 0; i < listUser.size(); i++) {
//                if (listUser.get(i).getStatus()) {
//                    listUser.remove(i).getId();
//                    mysqlitedb.deleteContact(listUser.get(i).getId());
//                }
//            }
//            listUser.removeIf(User::getStatus);
//            listUserAdapter.notifyDataSetChanged();
        });
    }
}