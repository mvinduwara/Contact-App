package com.example.my_contact.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.my_contact.model.CallRecord;
import com.example.my_contact.model.Contact;
import com.example.my_contact.model.Message;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "contactsApp.db";
    private static final int DATABASE_VERSION = 3;

    // Contacts Table
    private static final String TABLE_CONTACTS = "contacts";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";

    // Messages Table
    private static final String TABLE_MESSAGES = "messages";
    private static final String COLUMN_MSG_ID = "msg_id";
    private static final String COLUMN_MSG_CONTACT = "contact_name";
    private static final String COLUMN_MSG_TEXT = "message_text";
    private static final String COLUMN_MSG_IS_SENT = "is_sent_by_me";

    // Call Logs Table
    private static final String TABLE_CALL_LOGS = "call_logs";
    private static final String COLUMN_CALL_ID = "call_id";
    private static final String COLUMN_CALL_CONTACT = "call_contact";
    private static final String COLUMN_CALL_TYPE = "call_type";
    private static final String COLUMN_CALL_DATE = "call_date";
    private static final String COLUMN_CALL_TIME = "call_time";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createContactsTable = "CREATE TABLE " + TABLE_CONTACTS + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_PHONE + " TEXT)";
        String createMessagesTable = "CREATE TABLE " + TABLE_MESSAGES + " (" + COLUMN_MSG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_MSG_CONTACT + " TEXT, " + COLUMN_MSG_TEXT + " TEXT, " + COLUMN_MSG_IS_SENT + " INTEGER)";
        String createCallLogsTable = "CREATE TABLE " + TABLE_CALL_LOGS + " (" + COLUMN_CALL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CALL_CONTACT + " TEXT, " + COLUMN_CALL_TYPE + " TEXT, " + COLUMN_CALL_DATE + " TEXT, " + COLUMN_CALL_TIME + " TEXT)";

        db.execSQL(createContactsTable);
        db.execSQL(createMessagesTable);
        db.execSQL(createCallLogsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALL_LOGS);
        onCreate(db);
    }

    // --- CONTACTS ---
    public boolean addOne(String name, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_PHONE, phone);
        return db.insert(TABLE_CONTACTS, null, cv) != -1;
    }

    public List<Contact> getAllContacts() {
        List<Contact> returnList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACTS + " ORDER BY " + COLUMN_NAME + " ASC", null);
        if (cursor.moveToFirst()) {
            do { returnList.add(new Contact(cursor.getString(1), cursor.getString(2))); } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public boolean deleteContact(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_CONTACTS, COLUMN_NAME + "=?", new String[]{name});
        db.close();
        return result > 0;
    }

    // --- MESSAGES ---
    public boolean addMessage(String contactName, String text, boolean isSentByMe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_MSG_CONTACT, contactName);
        cv.put(COLUMN_MSG_TEXT, text);
        cv.put(COLUMN_MSG_IS_SENT, isSentByMe ? 1 : 0);
        long insert = db.insert(TABLE_MESSAGES, null, cv);
        db.close();
        return insert != -1;
    }

    public List<Message> getMessagesForContact(String contactName) {
        List<Message> returnList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MESSAGES + " WHERE " + COLUMN_MSG_CONTACT + " = ?", new String[]{contactName});
        if (cursor.moveToFirst()) {
            do { returnList.add(new Message(cursor.getString(2), cursor.getInt(3) == 1)); } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }

    // --- CALL LOGS ---
    public boolean addCallLog(String contactName, String callType, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CALL_CONTACT, contactName);
        cv.put(COLUMN_CALL_TYPE, callType);
        cv.put(COLUMN_CALL_DATE, date);
        cv.put(COLUMN_CALL_TIME, time);
        long insert = db.insert(TABLE_CALL_LOGS, null, cv);
        db.close();
        return insert != -1;
    }

    public List<CallRecord> getCallLogs(String contactName) {
        List<CallRecord> returnList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CALL_LOGS + " WHERE " + COLUMN_CALL_CONTACT + " = ? ORDER BY " + COLUMN_CALL_ID + " DESC", new String[]{contactName});
        if (cursor.moveToFirst()) {
            do { returnList.add(new CallRecord(cursor.getString(2), cursor.getString(3), cursor.getString(4))); } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }
}