package com.fsck.k9.storage.migrations;


import android.database.sqlite.SQLiteDatabase;


class MigrationTo69 {
    public static void addMessageColumnLabel(SQLiteDatabase db) {
        db.execSQL("ALTER TABLE messages ADD label TEXT");
    }
}
