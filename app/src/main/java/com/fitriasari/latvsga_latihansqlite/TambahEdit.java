package com.fitriasari.latvsga_latihansqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fitriasari.latvsga_latihansqlite.Helper.SQLiteDbHandler;
import com.fitriasari.latvsga_latihansqlite.Model.Contact;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TambahEdit extends AppCompatActivity {
    EditText txt_ID,txt_Name,txt_Phone_Number,txt_Address;
    Button btnSubmit,btnCancel;
    SQLiteDbHandler sqLiteDbHandler = new SQLiteDbHandler(this);
    Contact contact = new Contact();
    String id,name,phone_number,address;
    List<Contact> contactList = new ArrayList<Contact>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_edit);

        txt_ID = findViewById(R.id.txt_ID);
        txt_Name = findViewById(R.id.txt_Name);
        txt_Phone_Number = findViewById(R.id.txt_Phone_Number);
        txt_Address = findViewById(R.id.txt_Address);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);

        id = getIntent().getStringExtra(MainActivity.KEY_ID);
        name = getIntent().getStringExtra(MainActivity.KEY_NAME);
        phone_number = getIntent().getStringExtra(MainActivity.KEY_PH_NO);
        address = getIntent().getStringExtra(MainActivity.KEY_ADDRESS);

        if (id == null || id == ""){
            setTitle("Add Data");
        }else {
            setTitle("Edit Data");
            txt_ID.setText(id);
            txt_Name.setText(name);
            txt_Phone_Number.setText(phone_number);
            txt_Address.setText(address);
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (txt_ID.getText().toString().equals("")){
                        save();
                    }else {
                        edit();
                    }
                }catch (IOException e){
                    Log.d("Submit", e.toString());
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blank();
                finish();
            }
        });

    }

    private void edit() {
        SQLiteDatabase.updateContact(Integer.parseInt(txt_ID.getText().toString().trim()), txt_Name.getText().toString().trim(),txt_Address.getText().toString().trim());
        blank();
        finish();
    }

    private void save() {
            SQLiteDbHandler.addContact(txt_Name.getText().toString().trim(),txt_Address.getText().toString().trim());
            blank();
            finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                blank();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void blank() {
        txt_Name.requestFocus();
        txt_ID.setText(null);
        txt_Phone_Number.setText(null);
        txt_Address.setText(null);
    }


}