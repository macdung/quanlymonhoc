package com.dungmac.quanlymonhoc;

import android.content.Intent;
import android.os.Bundle;
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

        btnAdd = findViewById(R.id.btnAdd);
        btnDel = findViewById(R.id.btnDel);
        lvGrade = findViewById(R.id.lvClass);

        handleButton();

        listGrade = new ArrayList<>();
        listGrade = mysqlitedb.getAllGrade();
        //Tạo Adapter để đặt dữ liệu cho listview
        listGradeAdapter = new MyGradeAdapter(this, listGrade);
        //Gắn Addapter vào cho listview
        lvGrade.setAdapter(listGradeAdapter);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 400) {
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