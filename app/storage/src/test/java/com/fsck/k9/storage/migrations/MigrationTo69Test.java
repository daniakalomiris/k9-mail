package com.fsck.k9.storage.migrations;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fsck.k9.storage.RobolectricTest;
import com.fsck.k9.controller.MessagingControllerCommands.PendingAppend;
import com.fsck.k9.controller.MessagingControllerCommands.PendingCommand;
import com.fsck.k9.controller.MessagingControllerCommands.PendingEmptyTrash;
import com.fsck.k9.controller.MessagingControllerCommands.PendingExpunge;
import com.fsck.k9.controller.MessagingControllerCommands.PendingMarkAllAsRead;
import com.fsck.k9.controller.MessagingControllerCommands.PendingMoveOrCopy;
import com.fsck.k9.controller.MessagingControllerCommands.PendingSetFlag;
import com.fsck.k9.mail.Flag;
import com.fsck.k9.storage.migrations.MigrationTo60.OldPendingCommand;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


public class MigrationTo69Test extends RobolectricTest {

    @Test
    public void migrateAddMessageColShouldChangeTableColumns() {
        SQLiteDatabase database = createV68Table();

        MigrationTo69.addMessageColumnLabel(database);

        List<String> columns = getColumnList(database, "messages");
        assertEquals(true, columns.contains("label"));
    }

    @Test
    public void migrateAddMessageColMultipleTimesShouldNotThrow() {
        SQLiteDatabase database = createV68Table();
        MigrationTo60.migratePendingCommands(database);
        MigrationTo60.migratePendingCommands(database);
    }

    private SQLiteDatabase createV68Table() {
        SQLiteDatabase database = SQLiteDatabase.create(null);
        database.execSQL("CREATE TABLE messages (" +
                "id INTEGER PRIMARY KEY, " +
                "deleted INTEGER default 0, " +
                "folder_id INTEGER, " +
                "uid TEXT, " +
                "subject TEXT, " +
                "date INTEGER, " +
                "flags TEXT, " +
                "sender_list TEXT, " +
                "to_list TEXT, " +
                "cc_list TEXT, " +
                "bcc_list TEXT, " +
                "reply_to_list TEXT, " +
                "attachment_count INTEGER, " +
                "internal_date INTEGER, " +
                "message_id TEXT, " +
                "preview_type TEXT default \"none\", " +
                "preview TEXT, " +
                "mime_type TEXT, "+
                "normalized_subject_hash INTEGER, " +
                "empty INTEGER default 0, " +
                "read INTEGER default 0, " +
                "flagged INTEGER default 0, " +
                "answered INTEGER default 0, " +
                "forwarded INTEGER default 0, " +
                "message_part_id INTEGER," +
                "encryption_type TEXT" +
                ")");
        return database;
    }

    private List<String> getColumnList(SQLiteDatabase db, String table) {
        List<String> columns = new ArrayList<>();
        Cursor columnCursor = db.rawQuery("PRAGMA table_info(" + table + ")", null);
        try {
            while (columnCursor.moveToNext()) {
                columns.add(columnCursor.getString(1));
            }
        } finally {
            columnCursor.close();
        }
        return columns;
    }
}
