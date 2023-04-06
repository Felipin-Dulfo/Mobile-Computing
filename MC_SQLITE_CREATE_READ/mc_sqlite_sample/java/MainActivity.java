

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //DECLARE DBHELPER NEW INSTANCE
    DBHELPER MYDB = new DBHELPER(this);

    //DECLARE GLOBAL VARIABLE FOR LIST VIEW
    ListView stud_listUI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ASSIGN GLOBAL VARIABLE OF LIST VIEW TO UI
        stud_listUI = findViewById(R.id.stud_list);

        //set stud_listUI onItemClick
        stud_listUI.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //GET TEXT FROM CLICKED ITEM
                //i IS INDEX NUMBER
                String itemValue = stud_listUI.getItemAtPosition(i).toString();
                //Split itemValue by '-' We Only Need newItemValue[1] Which is Full Name
                String[] newItemValue = itemValue.split("-");

                Intent intentActivty = new Intent(MainActivity.this, Add_Form.class);
                //putExtra Value newItemValue[1] to Intent
                intentActivty.putExtra("item_value",newItemValue[1].trim());
                startActivity(intentActivty);
            }
        });

        //CALL VIEW-DATA FUNCTION
        viewData();
        //CALL setTopHeaderBarText FUNCTION
        setTopHeaderBarText();
    }

    //onResume
    @Override
    protected void onResume() {
        super.onResume();
        //CALL VIEW-DATA
        viewData();
        //CALL setTopHeaderBarText FUNCTION
        setTopHeaderBarText();
    }

    //FUNCTION TO POPULATE LISTVIEW FROM SQLITE
    private void viewData() {

        //The ArrayList class is a resizable array, which can be found in the java.util package
        //DECLARE ArrayList Variable listItem and Assign into new Arraylist
        ArrayList<String> listItem = new ArrayList<>();

        //The ArrayAdapter serves as a link between the UI Component and the Data Source.
        //It transforms data from the data sources into view items that may be shown in the UI
        //DECLARE ArrayAdapter Variable data_adapter To Be Use Later To View In LIST VIEW
        ArrayAdapter<String> data_adapter;

        //Cursors are what contain the result set of a query made against a database in Android
        //Assign Value to Variable cursor from the result in viewAllData() in DBHELPER Class
        Cursor cursor = MYDB.viewAllData();

        if(cursor != null && cursor.getCount() != 0){
            //While Loop Cycle Through Every Data
            while(cursor.moveToNext()){
                //add cursor.getString(2) to listItem, 2 is COURSE 1 is Full Name
                //Add item to ArrayList Variable listItem
                listItem.add(cursor.getString(2)+" - "+cursor.getString(1));
            }

            // android.R.layout.simple_list_item_1
            // reference to an built-in XML layout document that is part of the Android OS,
            // rather than one of your own XML layouts.
            data_adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,listItem);
            //Set stud_listUI adapter with data_adapter from result
            stud_listUI.setAdapter(data_adapter);
        }
    }

    //FUNCTION GO TO Add_Form.class
    public void goAddForm(View view){
        Intent i = new Intent(this, Add_Form.class);
        i.putExtra("item_value","");
        startActivity(i);
    }

    //View Count on TopHeaderBar Text
    public void setTopHeaderBarText(){
        int res_count = MYDB.countAllData();
        TextView txtTitleU = findViewById(R.id.txtTitle);
        String title = "STUDENT - LIST [ "+res_count+" ]";
        txtTitleU.setText(title);
    }
}