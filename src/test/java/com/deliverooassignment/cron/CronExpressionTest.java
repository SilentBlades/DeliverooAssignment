package com.deliverooassignment.cron;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CronExpressionTest {
    //Happy path:
    // 1. Correct input
    @Test
    void correctInputTest() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = System.out;
        System.setOut(new PrintStream(baos));

        String input1String = "*/15 0 1,15 * 1-5 /usr/bin/find";
        CronExpression cronExpression = CronExpression.parse(input1String);
        try {
            cronExpression.printExpanded();
        } finally {
            System.setOut(printStream);
        }
        String output1Expected =
                String.join("\n",
                        String.format("%-14s%s", "minute",        "0 15 30 45"),
                        String.format("%-14s%s", "hour",          "0"),
                        String.format("%-14s%s", "day of month",  "1 15"),
                        String.format("%-14s%s", "month",         "1 2 3 4 5 6 7 8 9 10 11 12"),
                        String.format("%-14s%s", "day of week",   "1 2 3 4 5"),
                        String.format("%-14s%s", "command",       "/usr/bin/find")
                );
        String output1actual = baos.toString().trim();
        assertEquals(output1Expected, output1actual);

        baos.reset();
        System.setOut(new PrintStream(baos));
        String input2String = "*/15 0 1,15 * 1-5 /usr/bin/find hello one more command";
        cronExpression = CronExpression.parse(input2String);
        try {
            cronExpression.printExpanded();
        } finally {
            System.setOut(printStream);
        }
        String output2Expected =
                String.join("\n",
                        String.format("%-14s%s", "minute",        "0 15 30 45"),
                        String.format("%-14s%s", "hour",          "0"),
                        String.format("%-14s%s", "day of month",  "1 15"),
                        String.format("%-14s%s", "month",         "1 2 3 4 5 6 7 8 9 10 11 12"),
                        String.format("%-14s%s", "day of week",   "1 2 3 4 5"),
                        String.format("%-14s%s", "command",       "/usr/bin/find hello one more command")
                );
        String output2actual = baos.toString().trim();
        assertEquals(output2Expected, output2actual);
    }

    // Sad path:
    // Incorrect input
    @Test
    void incorrectInputTest() {
        assertThrows(IllegalArgumentException.class,
                () -> CronExpression.parse("* * * *"));

        assertThrows(IllegalArgumentException.class,
                () -> CronExpression.parse(null));

        assertThrows(IllegalArgumentException.class,
                () -> CronExpression.parse(""));
    }
}
