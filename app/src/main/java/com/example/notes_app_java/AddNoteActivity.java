package com.example.notes_app_java;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import io.realm.Realm;

public class AddNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_note);
        EditText titleInput = findViewById(R.id.titleInput);
        EditText descInput = findViewById(R.id.titleDescription);
        MaterialButton saveBtn = findViewById(R.id.saveBtn);
        Realm.init(this);
        Realm realm = Realm.getDefaultInstance();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleInput.getText().toString();
                String desc =  descInput.getText().toString();
                long createdTime = System.currentTimeMillis();

                realm.beginTransaction();
                Notes note = realm.createObject(Notes.class);
                note.setTitle(title);
                note.setDescription(desc);
                note.setCreatedTime(createdTime);
                realm.commitTransaction();
                Toast.makeText(getApplicationContext(), "Note Saved",Toast.LENGTH_LONG).show();
                finish();

            }
        });
    }
}