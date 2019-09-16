package gamsystech.user.newbleupdated.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import gamsystech.user.newbleupdated.activities.registration_activity.RegisterRequestModel;
import gamsystech.user.newbleupdated.model.TreatmentLogs;
import gamsystech.user.newbleupdated.model.User;
import gamsystech.user.newbleupdated.pojo.Log;

public class DbHandler extends SQLiteOpenHelper
{


    public String TAG = DbHandler.class.getSimpleName();



    /*DB handler instance*/
    private static DbHandler appLocalData;


    /*DATABSE  NAME*/
    private static final String DATABASE_NAME = "redox";//name of the database

    /*DATABASE VERSION*/
    private static final int DATABASE_VERSION = 4;

    /*DATABASE TABLE NAME*/
    private static final String TREATMENT_TABLE = "Treatment_Log";                   //name for the table
    private static final String USER_TABLE   ="user_table";                      //user table
    public  static final String REGISTER_TABLE = "register_table";


    /*login table column names*/
    private static final String COLUMN_ID   ="user_id";
    private static final String COLUMN_USERNAME   ="user_username";
    private static final String COLUMN_PASSWORD   ="user_password";



    /*treatment table column names*/
    public static final String KEY_ID = "key";
    public static final String START_TIME = "start_time";
    public static final String R_IBL = "r_ibl";
    public static final String R_MAP = "r_map";
    public static final String R_SYS = "r_sys";
    public static final String R_DIA = "r_dia";
    public static final String R_PULSE = "r_pulse";
    public static final String R_HRADC = "r_hradc";

    public static final String L_SYS ="l_sys" ;
    public static final String L_DIA = "l_dia";
    public static final String L_PULSE = "l_pulse";
    public static final String MOB ="mob";



    // User REgistration Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_EMAIL_ID = "email_id";

    private static final String COLUMN_USER_MOBILE = "user_mobile";
    private static final String COLUMN_USER_PASS = "user_pass";




    public static final String CREATE_TABLE_TREATMENT = "CREATE TABLE IF NOT EXISTS " + TREATMENT_TABLE + "(" + KEY_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," + START_TIME + " DATETIME," + R_SYS + " INTEGER," + R_DIA + " INTEGER," + R_PULSE + " INTEGER,"  + L_SYS + " INTEGER," + L_DIA + " INTEGER," + L_PULSE + " INTEGER,"+ MOB+ "TEXT)";


   // drop table sql query

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + USER_TABLE;
    private String DROP_RHTREATMENT_TABLE = "DROP TABLE IF EXISTS " + TREATMENT_TABLE;

    /*LOCAL DATABASE CONSTRUCTOR*/
    public DbHandler(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    //return db handler instance to the class if used
    public static DbHandler getInstance(Context context)
    {
        if (appLocalData == null)
        {
            appLocalData = new DbHandler(context.getApplicationContext());
        }
        return appLocalData;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        //Query to create table in database
        sqLiteDatabase.execSQL(CREATE_TABLE_TREATMENT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

        db.execSQL(DROP_USER_TABLE);

        //  db.execSQL(DROP_REGISTER_TABLE);
         db.execSQL(DROP_RHTREATMENT_TABLE);
        onCreate(db);
    }



    /*method to add user*/
    public void adduser(RegisterRequestModel registerRequestModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME,registerRequestModel.getUserName());
        values.put(COLUMN_PASSWORD,registerRequestModel.getPin());

        long id = db.insert(REGISTER_TABLE,null,values);
        db.close();
        android.util.Log.d(TAG,"usertable"+id);
    }

    /*getting single user*/
    public  boolean getUser(String mobilenumber, String password)
    {
        String selectuser  ="select * from "+ USER_TABLE + "where" + COLUMN_USERNAME + "=" + "'"+mobilenumber+"'" + "and" + COLUMN_PASSWORD + "=" + "'" +password+ "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor  = db.rawQuery(selectuser,null);

        /*move to first row*/
        cursor.moveToFirst();

        if(cursor.getCount() > 0)
        {
            return true;
        }
        cursor.close();
        db.close();

        return false;
    }

    /*get single user through model class*/
    public User getuserthroughmodel(String mobilenumber, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        if(cursor != null)
            cursor = db.query(USER_TABLE, new String[]{KEY_ID, COLUMN_USERNAME, COLUMN_PASSWORD}, COLUMN_USERNAME + "=? and " + COLUMN_PASSWORD + "=?", new String[]{mobilenumber, password}, null, null, null, null);
        cursor.moveToFirst();

        User emp = new User();
        emp.setId(Integer.parseInt(cursor.getString(0)));
        emp.setMobileno(cursor.getString(1));
        emp.setPassword(cursor.getString(2));
        return emp;
    }



    //method to add row to table
    public  void addTreatment(String starttime, int r_sys, int r_dia, int r_pulse, int l_sys, int l_dia, int l_pulse,String mob)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ContentValues contentValues =new ContentValues();

        contentValues.put(START_TIME, dateFormat.format(starttime));
        contentValues.put(R_SYS, r_sys);
        contentValues.put(R_DIA, r_dia);
        contentValues.put(R_PULSE, r_pulse);
        contentValues.put(L_SYS, l_sys);
        contentValues.put(L_DIA, l_dia);
        contentValues.put(L_PULSE, l_pulse);
        contentValues.put(MOB,mob);
        db.insert(TREATMENT_TABLE,null,contentValues);


        db.close();
    }






    /*method to get treatment of single user values*/
    public TreatmentLogs gettreatment(String r_ibl, String r_map, String r_sys, String r_dia, String r_pulse, String r_hradc)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        /*cursor not need to be sysnchronized*/
        Cursor c_treatment = db.query(TREATMENT_TABLE, new String[] {KEY_ID, R_IBL,R_MAP,R_SYS,R_DIA,R_PULSE,R_HRADC},
                R_IBL + "=? and " + R_MAP + "=? and " +  R_SYS + "=? and " + R_DIA + "=? and " + R_PULSE + "=? and " +R_HRADC + "=? and ",
                new String[]{String.valueOf(r_ibl), r_map, r_sys, r_dia,r_pulse,r_hradc }, null, null, null, null);


        if(c_treatment !=null)
            c_treatment.moveToFirst();

        TreatmentLogs treatmentLogs =new TreatmentLogs();

        treatmentLogs.setId(Integer.parseInt(c_treatment.getString(0)));
        treatmentLogs.setIBatterlevelRH(c_treatment.getString(1));
        treatmentLogs.setIMAP(c_treatment.getString(2));
        treatmentLogs.setIsysRH(c_treatment.getString(3));
        treatmentLogs.setIdiaRH(c_treatment.getString(4));
        treatmentLogs.setIHRADCRH(c_treatment.getString(5));
        treatmentLogs.setIHRADCRH(c_treatment.getString(6));



        /*return treatment logs*/
        return treatmentLogs;
    }

    /*method to list all users details*/

    public  List<User> getAllUser()
    {
        List<User> userList = new ArrayList<User>();
        String selectlistuser = "SELECT * FROM " + USER_TABLE ;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectlistuser,null);

        // looping through all rows and adding to list
        if(cursor.moveToFirst())
        {
            do
            {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setMobileno(cursor.getString(1));
                user.setPassword(cursor.getString(2));

                userList.add(user);
            }
            while (cursor.moveToNext());

        }
        // return contact list
        return userList;
    }


    //method to list all details from table
    public List<Log> getAllTreatment(String mob)
    {
        List<Log>logList =new ArrayList<Log>();
        String selectQuery = "SELECT * FROM " + TREATMENT_TABLE + " WHERE " + MOB + " = " + mob;//retrieve data from the database

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do
            {
                Log m = new Log();
                m.setId(cursor.getInt(0));
                m.setTreatmentStartTimeStamp(cursor.getString(1));
                m.setRightHandSystolic(cursor.getInt(2));
                m.setRightHandDiastolic(cursor.getInt(3));
                m.setRightHandPulse(cursor.getInt(4));
                m.setLeftHandSystolic(cursor.getInt(5));
                m.setLeftHandDiastolic(cursor.getInt(6));
                m.setLeftHandPulse(cursor.getInt(7));
                m.setMob(cursor.getString(8));
                logList.add(m);
            }
            while(cursor.moveToNext());
        }
        return logList;
    }


    /*for export database to internal storage*/

   // exportDB("MyDbName");

    private void exportDB(String db_name,Context context)
    {

        File sd = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "Your Backup Folder"+ File.separator );

        boolean success = true;

        if (!sd.exists())
        {
            success = sd.mkdir();           //make directory
        }
        if (success)
        {

            File data = Environment.getDataDirectory();
            FileChannel source=null;
            FileChannel destination=null;
            String currentDBPath = "/data/"+ context.getPackageName() +"/databases/"+db_name;
            String backupDBPath = db_name;
            File currentDB = new File(data, currentDBPath);
            File backupDB = new File(sd, backupDBPath);
            try {
                source = new FileInputStream(currentDB).getChannel();
                destination = new FileOutputStream(backupDB).getChannel();
                destination.transferFrom(source, 0, source.size());
                source.close();
                destination.close();



            } catch(IOException e)
            {
                e.printStackTrace();
            }
        }}


        /*import  from db*/

   // importDB("MyDbName");

    private void importDB(String db_name,Context context)
    {
        File sd = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "Your Backup Folder"+
                File.separator );
        File data = Environment.getDataDirectory();

        FileChannel source=null;
        FileChannel destination=null;

        String backupDBPath = "/data/"+ context.getPackageName() +"/databases/"+db_name;
        String currentDBPath = db_name;
        File currentDB = new File(sd, currentDBPath);
        File backupDB = new File(data, backupDBPath);
        try
        {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            //Toast.makeText(this, "Please wait", Toast.LENGTH_SHORT).show();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }


}
