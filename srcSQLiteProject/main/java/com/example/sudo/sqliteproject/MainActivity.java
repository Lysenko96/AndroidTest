package com.example.sudo.sqliteproject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnAdd, btnRead, btnClear;
    EditText etName, etEmail;

    DBHelper dbHelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);

        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        switch (v.getId())
        {
            case R.id.btnAdd:
                contentValues.put(DBHelper.KEY_NAME, name);
                contentValues.put(DBHelper.KEY_MAIL, email);

                database.insert(DBHelper.TABLES_CONTACTS, null, contentValues);
                break;

            case R.id.btnRead:
                Cursor c = database.query(DBHelper.TABLES_CONTACTS,null,null,null,null,null,null);

                if(c.moveToFirst())
                {
                    int idIndex = c.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex = c.getColumnIndex(DBHelper.KEY_NAME);
                    int emailIndex = c.getColumnIndex(DBHelper.KEY_MAIL);

                    do
                    {
                        Log.d("mLog", "ID = " + c.getInt(idIndex) +
                        ", name = " + c.getString(nameIndex) +
                        ", email = " + c.getString(emailIndex));
                    } while (c.moveToNext());
                } else
                    Log.d("mLog", "0 rows");

                c.close();
                break;
            case R.id.btnClear:
                database.delete(DBHelper.TABLES_CONTACTS, null, null);
                break;
        }
        dbHelper.close();
    }
}