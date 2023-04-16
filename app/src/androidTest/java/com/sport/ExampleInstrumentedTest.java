package com.sport;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.sport.util.database.DBOpenHelper;
import com.sport.util.database.DBTable;
import com.sport.util.database.entity.Point;
import com.sport.util.database.entity.Record;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.sport", appContext.getPackageName());

        DBTable record_table = DBTable.asDBTable(Record.class);
        DBTable dbTable = DBTable.asDBTable(Point.class);
        DBOpenHelper record = DBOpenHelper.createDBHelper(appContext, "record", record_table, 1);
        DBOpenHelper sport = DBOpenHelper.createDBHelper(appContext, "sport", dbTable, 1);
        LinkedList<Record> all = record.getAll(Record.class);
        LinkedList<Point> all1 = sport.getAll(Point.class);
        
    }
}