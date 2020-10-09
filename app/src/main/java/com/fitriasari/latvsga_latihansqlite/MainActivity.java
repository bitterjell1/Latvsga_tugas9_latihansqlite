package com.fitriasari.latvsga_latihansqlite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import com.fitriasari.latvsga_latihansqlite.Adapter.ContactAdapter;
import com.fitriasari.latvsga_latihansqlite.Helper.SQLiteDbHandler;
import com.fitriasari.latvsga_latihansqlite.Model.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.fitriasari.latvsga_latihansqlite.Helper.SQLiteDbHandler.TABLE_CONTACTS;

public class MainActivity extends AppCompatActivity  {
    ListView list_Contact;
    AlertDialog.Builder dialog;
    List<Contact> contactList = new ArrayList<Contact>();
    ContactAdapter contactAdapter;

     static final String KEY_ID = "id";
     static final String KEY_NAME = "name";
     static final String KEY_PH_NO = "phone_number";
     static final String KEY_ADDRESS = "address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //tambah model
        final Contact contact = new Contact();
        //tambah helper SQLITE
        final SQLiteDbHandler dbHandler = new SQLiteDbHandler(this);

        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
        dbHandler.addContact(new Contact("jerry", "09298782782733", "bekasi"));
        dbHandler.addContact(new Contact("Tommy", "95222222223444", "tangerang"));
        dbHandler.addContact(new Contact("Goofy", "03838993799235", "jakarta pusat"));

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        final List<Contact> contacts = dbHandler.getAllContacts();

        for (Contact cn : contacts) {
            String log = "Id: " + cn.get_id() + " ,Name: " + cn.get_name() + " ,Phone: " +
                    cn.get_phone_number() + " ,Address: " + cn.get_address();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //button tambah data
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TambahEdit.class);
                startActivity(intent);
            }
        });
        //tambah adapter dan listview
        contactAdapter = new ContactAdapter(MainActivity.this,contactList);
        list_Contact.setAdapter(contactAdapter);

        //tekan daftar lisview untuk menampilkan edit dan hapus
        list_Contact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
                final SQLiteDbHandler db = new SQLiteDbHandler(MainActivity.this);

                final CharSequence[] dialogItem = {"Edit", "Delete"};
                Context context;
                dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Intent intent = new Intent(MainActivity.this,TambahEdit.class);
                                startActivity(intent);
                                break;
                            case 1:
                                dbHandler.deleteContact(Integer.parseInt(contact));
                                contactList.clear();
                                getAllData();
                                break;
                        }
                    }

                }).show();
                return;
            }
        });
        getAllData();

}

    private void getAllData() {
        List<Contact> contactList = new ArrayList<Contact>();

        for (int i = 0; i < contactList.size(); i++){
            String id = String.valueOf(contactList.get(i).get_id());
            String name = contactList.get(i).get_name();
            String phone_number = contactList.get(i).get_phone_number();
            String address = contactList.get(i).get_address();

            Contact contact = new Contact();

            contact.set_id(id);
            contact.set_name(name);
            contact.set_phone_number(phone_number);
            contact.set_address(address);

            contactList.add(contact);
        }
        contactAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onResume() {
        super.onResume();
        contactList.clear();
        getAllData();
    }
}