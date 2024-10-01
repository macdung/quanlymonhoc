package com.dungmac.quanlymonhoc;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AddUpdateSubjectActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SELECT_IMAGE = 1;

    private EditText etSubjectName, etSubjectCount;
    private ImageView ivSubjectImage;
    private Button btnSave, btnSelectImage;

    private MyDB myDB;
    private int gradeId;
    private Subject subjectToUpdate;
    private String selectedImagePath = null;  // Path to the selected image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_update_subject);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add_grade_subject), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etSubjectName = findViewById(R.id.etSubjectName);
        etSubjectCount = findViewById(R.id.etSubjectCount);
        ivSubjectImage = findViewById(R.id.ivSubjectImage);
        btnSave = findViewById(R.id.btnSave);
        btnSelectImage = findViewById(R.id.btnSelectImage);

        myDB = new MyDB(this, "QuanLyMonHoc", null, 3);
        subjectToUpdate = getIntent().getExtras().getParcelable("Subject");
        gradeId = getIntent().getIntExtra("GradeId", -1);

        // Check if it's an update
        if (subjectToUpdate != null) {
            gradeId = subjectToUpdate.getGradeId();
            etSubjectName.setText(subjectToUpdate.getName());
            etSubjectCount.setText(String.valueOf(subjectToUpdate.getCount()));
            selectedImagePath = subjectToUpdate.getImagePath();
            if(selectedImagePath!=null){
                ivSubjectImage.setImageURI(Uri.parse(selectedImagePath));  // Load existing image
            }

        }

        // Select image from gallery
        btnSelectImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
        });

        btnSave.setOnClickListener(v -> {
            String name = etSubjectName.getText().toString();
            int count = Integer.parseInt(etSubjectCount.getText().toString());

            if (subjectToUpdate == null) {
                // Add new subject
                Subject subject = new Subject(name, count, selectedImagePath, gradeId);
                myDB.addSubject(subject, gradeId);
            } else {
                // Update existing subject
                subjectToUpdate.setName(name);
                subjectToUpdate.setCount(count);
                subjectToUpdate.setImagePath(selectedImagePath);
                myDB.updateSubject(subjectToUpdate.getId(), subjectToUpdate);
            }
            setResult(RESULT_OK);
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                // Save the image to the app's storage and get the path
                selectedImagePath = saveImageToStorage(selectedImageUri);
                ivSubjectImage.setImageURI(Uri.parse(selectedImagePath));  // Set image in ImageView
            }
        }
    }

    // Save image to the app's private storage and return the file path
    private String saveImageToStorage(Uri imageUri) {
        try {
            // Get bitmap from URI
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

            // Create a file in the app's storage directory
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File imageFile = new File(storageDir, "subject_" + System.currentTimeMillis() + ".jpg");

            // Save the bitmap to the file
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.close();

            // Return the file path
            return imageFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage permission required", Toast.LENGTH_SHORT).show();
            }
        }
    }

}