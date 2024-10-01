package com.dungmac.quanlymonhoc.list_grade;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dungmac.quanlymonhoc.R;

public class GradeActivity extends AppCompatActivity {

    EditText etName;
    Button btnOk, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_grade);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.gradeActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setContentView(R.layout.activity_grade);

        Toolbar toolbar = findViewById(R.id.toolbar_grade);

        setSupportActionBar(toolbar);

        // Enable the back button in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(v -> {
            setResult(-1);
            finish();
        });

        etName = findViewById(R.id.etName);

        btnOk = findViewById(R.id.btnOK);
        btnCancel = findViewById(R.id.btnCancel);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Tạo intent để trở về MainActivity
                Intent intent = new Intent();
                //Tạo bundle là đối tượng để chứa dữ liệu
                Bundle bundle = new Bundle();
                //bundle hoạt động như một Java Map các phần tử phân biệt theo key
                //bundle có các hàm put... trong đó ... là kiểu dữ liệu tương ứng
                bundle.putString("Name", etName.getText().toString());
                intent.putExtras(bundle);
                setResult(200, intent);
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                setResult(400);
                finish();
            }
        });

    }
}