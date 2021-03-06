/*
 * Copyright 2004-2011 H2 Group. Multiple-Licensed under the H2 License,
 * Version 1.0, and under the Eclipse Public License, Version 1.0
 * (http://h2database.com/html/license.html).
 * Initial Developer: H2 Group
 */
package org.h2.test.todo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Random;
import org.h2.test.unit.TestReopen;
import org.h2.test.utils.RecordingFileSystem;
import org.h2.tools.DeleteDbFiles;

/**
 * Test crashing a database by creating a lot of temporary tables.
 */
public class TestTempTableCrash {

    /**
     * Run just this test.
     *
     * @param args ignored
     */
    public static void main(String[] args) throws Exception {
        new TestTempTableCrash().test();
    }

    private void test() throws Exception {
        Connection conn;
        Statement stat;

        System.setProperty("h2.delayWrongPasswordMin", "0");
        System.setProperty("h2.check2", "false");
        System.setProperty("h2.lobInDatabase", "true");
        RecordingFileSystem.register();
        System.setProperty("reopenShift", "4");
        TestReopen reopen = new TestReopen();
        RecordingFileSystem.setRecorder(reopen);

        String url = "jdbc:h2:" + RecordingFileSystem.PREFIX +
                "memFS:data;PAGE_SIZE=64;ANALYZE_AUTO=100";
        // String url = "jdbc:h2:" + RecordingFileSystem.PREFIX +
        //      "data/test;PAGE_SIZE=64";

        Class.forName("org.h2.Driver");
        DeleteDbFiles.execute("data", "test", true);
        conn = DriverManager.getConnection(url, "sa", "sa");
        stat = conn.createStatement();

        Random random = new Random(1);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            long now = System.currentTimeMillis();
            if (now > start + 1000) {
                System.out.println("i: " + i);
                start = now;
            }
            int x;
            x = random.nextInt(100);
            stat.execute("drop table if exists test" + x);
            String type = random.nextBoolean() ? "temp" : "";
            // String type = "";
            stat.execute("create " + type + " table test" + x + "(id int primary key, name varchar)");
            if (random.nextBoolean()) {
                stat.execute("create index idx_" + x + " on test" + x + "(name, id)");
            }
            if (random.nextBoolean()) {
                stat.execute("insert into test" + x + " select x, x from system_range(1, " + random.nextInt(100) + ")");
            }
            if (random.nextInt(10) == 1) {
                conn.close();
                conn = DriverManager.getConnection(url, "sa", "sa");
                stat = conn.createStatement();
            }
        }
        conn.close();
    }

}
