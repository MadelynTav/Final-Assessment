package nyc.c4q;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by c4q-madelyntavarez on 8/30/15.
 */
public class DBAdapter {
    DBHelper dbHelper;

   public DBAdapter(Context context){
       dbHelper = new DBHelper(context);
   }
    public long insertMemberData(int id, String name, String city, String state, int month, int day, int year) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.UID,id);
        contentValues.put(DBHelper.NAME, name);
        contentValues.put(DBHelper.CITY, city);
        contentValues.put(DBHelper.MONTH,month);
        contentValues.put(DBHelper.DAY,day);
        contentValues.put(DBHelper.YEAR,year);
        contentValues.put(DBHelper.STATE, state);
        long id1 = db.insert(DBHelper.TABLE_NAME, null, contentValues);
        return id1;
    }

    public long insertBookData(int id, String title, String author, String isbn, String isbn13, String publisher, int pushlisherYear, boolean isCheckedOut,
                               int checkedOutBy, int checkoutYear, int checkOutMonth, int checkOutDay, int dueDateYear, int dueDateMonth, int
                               dueDateDay){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.UID,id);
        contentValues.put(DBHelper.TITLE,title);
        contentValues.put(DBHelper.AUTHOR,author);
        contentValues.put(DBHelper.ISBN,isbn);
        contentValues.put(DBHelper.ISBN13,isbn13);
        contentValues.put(DBHelper.PUBLISHER,publisher);
        contentValues.put(DBHelper.PUBLISHERYEAR,pushlisherYear);
        contentValues.put(DBHelper.ISCHECKOUTOUT,isCheckedOut);
        contentValues.put(DBHelper.CHECKEDOUTBY,checkedOutBy);
        contentValues.put(DBHelper.CHECKOUTYEAR,checkoutYear);
        contentValues.put(DBHelper.CHECKOUTMONTH,checkOutMonth);
        contentValues.put(DBHelper.CHECKOUTDAY,checkOutDay);
        contentValues.put(DBHelper.DUEYEAR,dueDateYear);
        contentValues.put(DBHelper.DUEMONTH,dueDateMonth);
        contentValues.put(DBHelper.DUEDAY,dueDateDay);

        long id2=db.insert(DBHelper.TABLE_NAME_2,null,contentValues);
        return id2;
    }
    public String getDataForMember(String name) {
        //Select Name, password from Table where name=""
        SQLiteDatabase dbs = dbHelper.getWritableDatabase();
        //Select ID, Name and Password from table
        String[] columns = {DBHelper.UID, DBHelper.NAME, DBHelper.CITY, DBHelper.STATE, DBHelper.MONTH,DBHelper.DAY,DBHelper.YEAR};
        Cursor cursor = dbs.query(DBHelper.TABLE_NAME_2, columns, DBHelper.NAME + " = '" + name + "'", null, null, null, null);

        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            int index1=cursor.getColumnIndex(DBHelper.UID);
            int index2 = cursor.getColumnIndex(DBHelper.NAME);
            int index3 = cursor.getColumnIndex(DBHelper.CITY);
            int index4= cursor.getColumnIndex(DBHelper.STATE);
            int index5=cursor.getColumnIndex(DBHelper.MONTH);
            int index6=cursor.getColumnIndex(DBHelper.DAY);
            int index7=cursor.getColumnIndex(DBHelper.YEAR);
            int userID=cursor.getInt(index1);
            String userName = cursor.getString(index2);
            String userCityAndState = cursor.getString(index3)+", " +cursor.getString(index4);
            String dob=cursor.getInt(index5)+"/"+cursor.getInt(index6)+"/"+cursor.getInt(index7);

            buffer.append("id: "+userID+"\n name: "+userName + "\n dob: " + dob + "\n location: "+userCityAndState);

        }
        return buffer.toString();
    }

    static class DBHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "LibraryDatabase";
        private static String TABLE_NAME = "LibraryBooks";
        private static String TABLE_NAME_2 = "LibraryMembers";
        private static int DATABASE_VERSION = 1;
        private static final String UID = "_id";
        private Context context;
        private static final String CITY = "City";
        private static final String STATE = "State";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        private static final String NAME = "Name";
        private static final String MONTH="Month";
        private static final String DAY="Day";
        private static final String YEAR="Year";
        private static final String TITLE="Title";
        private static final String ISBN="ISBN";
        private static final String AUTHOR="Author";
        private static final String ISBN13="ISBN13";
        private static final String PUBLISHER="Publisher";
        private static final String ISCHECKOUTOUT="IsCheckedOut";
        private static final String PUBLISHERYEAR="PublisherYear";
        private static final String CHECKEDOUTBY="CheckedOutBy";
        private static final String CHECKOUTYEAR="CheckoutYear";
        private static final String CHECKOUTMONTH="CheckoutMonth";
        private static final String CHECKOUTDAY="CheckoutDay";
        private static final String DUEYEAR="DueYear";
        private static final String DUEMONTH="DueMonth";
        private static final String DUEDAY="DueDay";
        private static final String CREATE_MEMBER_TABLE = "Create table " + TABLE_NAME + " (" + UID + " INTEGER PRIMARY KEY, " + NAME
                + " VARCHAR(255), " + CITY + " VARCHAR (255), "+STATE + " VARCHAR (255), "+ MONTH + " INTEGER PRIMARY KEY, " + DAY + " INTEGER PRIMARY KEY, " + YEAR + " INTEGER PRIMARY KEY);";

        private static final String CREATE_BOOK_TABLE = "Create table "+ TABLE_NAME_2+" ("+ UID+" Integer PRIMARY KEY, " + TITLE + " VARCHAR (255), "+ ISBN + " VARCHAR (255), "+ ISBN13 + " VARCHAR (255), "+
                PUBLISHER + " VARCHAR (255), "+ PUBLISHERYEAR + " INTEGER PRIMARY KEY, " + CHECKEDOUTBY + " VARCHAR (255), "+ CHECKOUTYEAR + " INTEGER PRIMARY KEY, " +   CHECKOUTMONTH + " INTEGER PRIMARY KEY, " + CHECKOUTDAY + " INTEGER PRIMARY KEY, " +
                DUEYEAR + " INTEGER PRIMARY KEY, " + DUEMONTH + " INTEGER PRIMARY KEY, " + DUEDAY + " INTEGER PRIMARY KEY);";

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
            Toast.makeText(context,"Constructor Called",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Toast.makeText(context, "OnCreate Was Called", Toast.LENGTH_LONG).show();

            db.execSQL(CREATE_MEMBER_TABLE);
            db.execSQL(CREATE_BOOK_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Toast.makeText(context, "ONUPGRADE Was Called",Toast.LENGTH_LONG).show();
            db.execSQL(DROP_TABLE);
            onCreate(db);


        }
    }
}
