

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHELPER extends SQLiteOpenHelper{
    //DECLARE CLASS VARIABLE
    public String VAR_STUD_ID = null;
    public String VAR_FULL_NAME = null;
    public String VAR_COURSE = null;

    //DATABASE NAME
    public static final String DATABASE_NAME = "STUDENT_INFO.db";
    //DATABASE VERSION
    public static final int DB_VERSION = 1;

    //TABLE NAME
    public static final String TABLE_NAME = "STUDENT";
    //DECLARE VARIABLES TABLE COLUMN
    public static final String COL_STUD_ID = "STUD_ID";
    public static final String COL_STUD_FULL_NAME = "STUD_FULL_NAME";
    public static final String COL_STUD_COURSE = "STUD_COURSE";

    //DECLARE VARIABLE FOR CREATE TABLE SCRIPT
    public static final String CREATE_TABLE_STUDENT = "CREATE TABLE "+TABLE_NAME+" ("
            +COL_STUD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +COL_STUD_FULL_NAME+" TEXT,"
            +COL_STUD_COURSE+" TEXT)";

    //DBHELPER Class Constructor
    public DBHELPER(Context context){
        super(context, DATABASE_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_STUDENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { //int i is OldVersion, int i1 is NewVersion
        //Drop Table If Exist
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //Call onCreate Method
        onCreate(sqLiteDatabase);

    }

    /* Create Read Update Delete - CRUD */
    /*  CREATE  */
    //FUNCTION / METHOD FOR DATA INSERT
    //public boolean insertData(String full_name, String course){
    public boolean insertData(){
        //getWritableDatabase ( Create and/or open a database that will be used for reading and writing).
        SQLiteDatabase db  = this.getWritableDatabase();

        //ContentValues are used to insert new rows into tables.
        //Each Content Values object represents a single table row as a map of column names to values
        ContentValues contentValues = new ContentValues();

        //contentValues.put(String Key, VALUE) // LIKE KEY VALUE PAIR
        //String KEY COLUMN_NAME
        //VALUE value for COLUMN_NAME
        //toUpperCase() TO CHANGE string TO UPPERCASE, trim() TO REMOVE WHITE SPACE
        contentValues.put(COL_STUD_FULL_NAME,this.VAR_FULL_NAME.toUpperCase().trim());
        contentValues.put(COL_STUD_COURSE,this.VAR_COURSE.toUpperCase().trim());

        //INSERT TO TABLE_NAME WITH contentValues
        long query_result = db.insert(TABLE_NAME,null,contentValues);

        //the row ID of the newly inserted row, or -1 if an error occurred
        return query_result != -1;

    }

    /*  READ  */
    //FUNCTION / METHOD GET ALL DATA FROM TABLE STUDENT
    public Cursor viewAllData(){
        //getWritableDatabase ( Create and/or open a database that will be used for reading and writing).
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursors are what contain the result set of a query made against a database in Android
        Cursor res = db.rawQuery( "select * from "+TABLE_NAME, null );
        return res;
    }

    /*  UPDATE  */
    //FUNCTION / METHOD UPDATE DATA
    public int updateData() {
        //getWritableDatabase ( Create and/or open a database that will be used for reading and writing).
        SQLiteDatabase db = this.getWritableDatabase();

        //ContentValues are used to insert new rows into tables.
        //Each Content Values object represents a single table row as a map of column names to values
        ContentValues contentValues = new ContentValues();

        //contentValues.put(String Key, VALUE) // LIKE KEY VALUE PAIR
        //String KEY COLUMN_NAME
        //VALUE value for COLUMN_NAME
        //toUpperCase() TO CHANGE string TO UPPERCASE, trim() TO REMOVE WHITE SPACE
        contentValues.put(COL_STUD_FULL_NAME, this.VAR_FULL_NAME.toUpperCase().trim());
        contentValues.put(COL_STUD_COURSE,this.VAR_COURSE.toUpperCase().trim());
        //returns the number of rows affected
        int i = db.update(TABLE_NAME, contentValues, COL_STUD_ID + " = " + this.VAR_STUD_ID, null);
        return i;
    }

    /*  DELETE  */
    //FUNCTION / METHOD DELETE DATA
    public int deleteData(){
        //getWritableDatabase ( Create and/or open a database that will be used for reading and writing).
        SQLiteDatabase db = this.getWritableDatabase();
        //delete(TABLE_NAME, WhereClause on Condition
        //returns number of rows affected
        int i = db.delete(TABLE_NAME, COL_STUD_ID + "=" + this.VAR_STUD_ID, null);
        return i;
    }

    //FUNCTION / METHOD GET SPECIFIC ROW
    //PARAMETER strName ( full_name )
    public void getStudentInfo(String strName){
        //getWritableDatabase ( Create and/or open a database that will be used for reading and writing).
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursors are what contain the result set of a query made against a database in Android
        Cursor res = db.rawQuery( "select * from "+TABLE_NAME+" WHERE "+COL_STUD_FULL_NAME+" = '"+strName+"' LIMIT 1", null );
        //move to first record in cursor
        res.moveToFirst();

        this.VAR_STUD_ID = res.getString(0);
        this.VAR_FULL_NAME = res.getString(1);
        this.VAR_COURSE = res.getString(2);

        res.close();
    }

    ///FUNCTION / METHOD COUNT ALL DATA IN TABLE
    public int countAllData(){
        //getWritableDatabase ( Create and/or open a database that will be used for reading and writing).
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursors are what contain the result set of a query made against a database in Android
        Cursor res = db.rawQuery("SELECT COUNT(*) FROM "+TABLE_NAME,null);
        //move to first record in cursor
        res.moveToFirst();
        int count = res.getInt(0);
        //close cursor
        res.close();
        //return result which is count
        return count;
    }

}
