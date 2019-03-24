package com.example.reg_waiting_castilloa15_list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.app.AlertDialog;
import android.database.Cursor;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName,editSurname,editTextId;
    Button btnAddData;
    Button btnviewAll;
    Button btnDelete;
    Button btnviewUpdate;

    private static RadioGroup radG;
    private static RadioButton priorityGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //calls the DatabaseHelper class to create a db and table
        myDb = new DatabaseHelper(this);

        //links the xml edittext and button to java
        editName = (EditText)findViewById(R.id.editText_name);
        editSurname = (EditText)findViewById(R.id.editText_surname);
        editTextId = (EditText)findViewById(R.id.editText_id);
        btnAddData = (Button)findViewById(R.id.button_add);
        btnviewAll = (Button)findViewById(R.id.button_viewAll);
        btnviewUpdate= (Button)findViewById(R.id.button_update);
        btnDelete= (Button)findViewById(R.id.button_delete);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();



    }


//delete the sql row based on the ID indicated

    public void DeleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(editTextId.getText().toString());
                        if(deletedRows > 0)
                            Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show();

                        //clears edit text fields
                        editName.getText().clear();
                        editSurname.getText().clear();
                        editTextId.getText().clear();
                    }
                }
        );
    }

    //update info based on ID
    public void UpdateData() {
        //radio group declaration
        radG = (RadioGroup)findViewById(R.id.radioG);
        btnviewUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //link the radiogroup with variable "priorityGroup"
                        int sel_id = radG.getCheckedRadioButtonId();
                        priorityGroup = (RadioButton)findViewById(sel_id);

                        boolean isUpdate = myDb.updateData(
                                editTextId.getText().toString(),
                                editName.getText().toString(),
                                editSurname.getText().toString(),
                                priorityGroup.getText().toString());
                        if(isUpdate == true)
                            Toast.makeText(MainActivity.this,"Data Update",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();

                        //clears edit text fields
                        editName.getText().clear();
                        editSurname.getText().clear();
                        editTextId.getText().clear();

                    }
                }
        );
    }

    //add a row to sql based on the info the user based
    public  void AddData() {
        //radio group declaration
        radG = (RadioGroup)findViewById(R.id.radioG);

        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //links the radiogroup with variable "priorityGroup"
                        int sel_id = radG.getCheckedRadioButtonId();
                        priorityGroup = (RadioButton)findViewById(sel_id);

                        boolean isInserted = myDb.insertData(
                                editName.getText().toString(),
                                editSurname.getText().toString(),
                                priorityGroup.getText().toString() );
                        if(isInserted == true)
                            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();

                        //clears edit text fields
                        editName.getText().clear();
                        editSurname.getText().clear();
                        editTextId.getText().clear();

                    }
                }
        );
    }

    //allows the user to see all the rows within the sql we created
    public void viewAll() {
        btnviewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0) {
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();

                        //loop the info in each row
                        while (res.moveToNext()) {
                            buffer.append("Id :"+ res.getString(0)+"\n");
                            buffer.append("Name :"+ res.getString(1)+"\n");
                            buffer.append("Surname :"+ res.getString(2)+"\n");
                            buffer.append("Priority :"+ res.getString(3)+"\n\n");
                        }

                        // Show all data
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }

    //popup message when user clicks viewAll button
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
