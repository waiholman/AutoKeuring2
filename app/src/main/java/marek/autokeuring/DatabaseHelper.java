package marek.autokeuring;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_PATH = "/data/data/marek.autokeuring/databases/";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contentDB.db";
    public static final String TABLE_CONTENT = "content";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CATEGORY_ID= "categoryid";
    public static final String COLUMN_PAGENUMBER = "pagenumber";
    public static final String COLUMN_CONTENT = "content";

    private Context context;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        this.context = context;

        copyDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
//                TABLE_CONTENT + "("
//                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_CATEGORY_ID
//                + " INTEGER," + COLUMN_PAGENUMBER + " INTEGER,"
//                + COLUMN_CONTENT + " TEXT"
//                + ")";
//        db.execSQL(CREATE_PRODUCTS_TABLE);

        copyDatabase();
    }

    private void copyDatabase()
    {
        try
        {
            InputStream myInput = context.getAssets().open(DATABASE_NAME);

            String outFileName = DATABASE_PATH + DATABASE_NAME;

            OutputStream myOutput = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer))>0){
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();
        }catch(IOException e)
        {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTENT);
        onCreate(db);
    }

    public void addContent(Content content) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY_ID, content._category);
        values.put(COLUMN_PAGENUMBER, content._page);
        values.put(COLUMN_CONTENT, content._content);

        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.insert(TABLE_CONTENT, null, values);
        db.close();
    }

    public Content findProduct(int category, int page) {
        String query = "Select * FROM " + TABLE_CONTENT + " WHERE " + COLUMN_CATEGORY_ID + " =  " + category + " AND "+ COLUMN_PAGENUMBER + " = " +page;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Content product = new Content();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            product._id = Integer.parseInt(cursor.getString(0));
            product._category = Integer.parseInt(cursor.getString(1));
            product._page = Integer.parseInt(cursor.getString(2));
            product._content = cursor.getString(3);
            cursor.close();
        } else {
            product = null;
        }

        String maxpageQuery = "Select Count(*) FROM " + TABLE_CONTENT + " WHERE " + COLUMN_CATEGORY_ID + " =  " + category;
        cursor = db.rawQuery(maxpageQuery, null);
        cursor.moveToFirst();
        product._maxPage = Integer.parseInt(cursor.getString(0));

        db.close();
        return product;
    }
}
