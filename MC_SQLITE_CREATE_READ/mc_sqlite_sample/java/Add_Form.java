

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import java.util.ArrayList;

public class Add_Form extends AppCompatActivity {
    //CALL / INITIALIZE DATABASE HELPER
    DBHELPER MYDB = new DBHELPER(this);

    String ID_TO_UPDATE = "0",ITEM_VALUE;

    //EditText Variables for UI
    EditText full_nameUI,courseUI;
    Button btnDelete;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_form);

        full_nameUI = findViewById(R.id.fullname);
        courseUI = findViewById(R.id.course);
        btnDelete= findViewById(R.id.btnDelete);
        tvResult= findViewById(R.id.txtResult);

        //CALL VIEW INFO TO EDIT
        viewInfo();
    }
    //Function for Button Save
    public void SaveItem(View view){
        String full_name,course;


        full_name = full_nameUI.getText().toString();
        course  = courseUI.getText().toString();

        if(full_name.equals("") || course.equals("")){
            Toast.makeText(this, "Error: Please Input Full Name or Course", Toast.LENGTH_SHORT).show();
        }else{
            if(!ID_TO_UPDATE.equals("0")){

                MYDB.VAR_STUD_ID = ID_TO_UPDATE;
                MYDB.VAR_FULL_NAME = full_name;
                MYDB.VAR_COURSE = course;

                int num_rows_affected = MYDB.updateData();
                //CHECK IF RESULT IS > 0
                if(num_rows_affected > 0){
                    Toast.makeText(this, "DATA SUCCESSFULLY UPDATED", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "DATA UPDATE FAILED", Toast.LENGTH_SHORT).show();
                }

            }else{
                //CALL insertData Method from DBHELPER CLASS
                //Assign Value to DBHELPER CLASS Variable VAR_FULL_NAME from EditText Value full_name
                MYDB.VAR_FULL_NAME = full_name;
                //Assign Value to DBHELPER CLASS Variable VAR_COURSE from EditText Value course
                MYDB.VAR_COURSE = course;
                //Declare and Assign Value to boolean Variable result
                //that returns from a Function in insertData() in DBHELPER CLASS
                boolean result = MYDB.insertData();
                //CHECK IF RESULT IS TRUE / FALSE
                if(result){//TRUE
                    Toast.makeText(this, "DATA SUCCESSFULLY INSERTED", Toast.LENGTH_SHORT).show();
                    full_nameUI.setText("");
                    courseUI.setText("");
                }else{
                    //FALSE
                    Toast.makeText(this, "DATA INSERTION FAILED", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    //Function for Button Delete
    public void DeleteItem(View view){
        int num_rows_affected = MYDB.deleteData();

        //CHECK IF RESULT IS > 0
        if(num_rows_affected > 0){
            Toast.makeText(this, "DATA SUCCESSFULLY DELETED", Toast.LENGTH_SHORT).show();

            //When Successfully Deleted
            //Go Back to MainActivity
            Intent i = new Intent(this,MainActivity.class);
            //clears your Activity stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        }else{
            Toast.makeText(this, "DATA DELETED FAILED", Toast.LENGTH_SHORT).show();
        }
    }

    //FUNCTION COUNT DATA CHECK
    public void simpleViewResult(View view){
        //Cursors are what contain the result set of a query made against a database in Android
        Cursor query_result = MYDB.viewAllData();

        //StringBuilder stringBuilder = new StringBuilder();
        String str_output = "";

        if(query_result != null && query_result.getCount() != 0){
            //While Loop Cycle Through Every Data
            while(query_result.moveToNext()){
                //add cursor.getString(2) to listItem, 2 is COURSE 1 is Full Name
                //stringBuilder.append(query_result.getString(1)).append("\n");
                str_output += "[ "+query_result.getString(0)+" ] "+query_result.getString(2)+" - "+query_result.getString(1)+"\n";
            }
            tvResult.setText(str_output);
        }
    }

    //FUNCTION Get Details from Specific item clicked
    //then View in EditText full_name and course
    public void viewInfo(){
        ITEM_VALUE = getIntent().getExtras().getString("item_value");
        if(!ITEM_VALUE.equals("")){
            //Call getStudentInfo in DBHELPER Class
            MYDB.getStudentInfo(ITEM_VALUE);

            //Assign ID_TO_UPDATE with Value from MYDB.VAR_STUD_ID in getStudentInfo(strName) in DBHELPER Class
            ID_TO_UPDATE = MYDB.VAR_STUD_ID;
            //full_name setText to VALUE of MYDB.VAR_FULL_NAME getStudentInfo in DBHELPER Class
            full_nameUI.setText(MYDB.VAR_FULL_NAME);
            //course setText to VALUE of MYDB.VAR_COURSE getStudentInfo in DBHELPER Class
            courseUI.setText(MYDB.VAR_COURSE);

            //SET BUTTON btnDelete enabled
            btnDelete.setEnabled(true);
        }else{
            //SET BUTTON btnDelete disabled
            btnDelete.setEnabled(false);
        }
    }

}