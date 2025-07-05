package com.deliverooassignment.cron;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FieldParserTest {
    final int min = 0;
    final int max = 12;

    // Happy Paths
    // 1. Full Range
    @Test
    void fullRangeTest() {
        SortedSet<Integer> input = FieldParser.parse("*", min, 3);
        SortedSet<Integer> output = new TreeSet<>(Arrays.asList(0, 1, 2, 3));
        assertEquals(input, output);
    }

    // 2. Simple Range
    @Test
    void simpleRangeTest() {
        SortedSet<Integer> input = FieldParser.parse("3-6", min, max);
        SortedSet<Integer> output = new TreeSet<>(Arrays.asList(3, 4, 5, 6));
        assertEquals(input, output);
    }

    // 3. Step
    @Test
    void stepTest() {
        SortedSet<Integer> input1 = FieldParser.parse("*/3", min, max);
        SortedSet<Integer> output1 = new TreeSet<>(Arrays.asList(0, 3, 6, 9, 12));
        assertEquals(input1, output1);

        SortedSet<Integer> input2 = FieldParser.parse("2-10/3", min, max);
        SortedSet<Integer> output2 = new TreeSet<>(Arrays.asList(2, 5, 8));
        assertEquals(input2, output2);

        SortedSet<Integer> input3 = FieldParser.parse("2-10/12", min, max);
        SortedSet<Integer> output3 = new TreeSet<>(Arrays.asList(2));
        assertEquals(input3, output3);
    }

    // 4. List
    @Test
    void listTest() {
        SortedSet<Integer> input = FieldParser.parse("1,8,11", min, max);
        SortedSet<Integer> output = new TreeSet<>(Arrays.asList(1, 8, 11));
        assertEquals(input, output);
    }

    // 5. Duplicate
    @Test
    void duplicateTest() {
        SortedSet<Integer> actual = FieldParser.parse("*/5,1-10,6,8", min, max);
        SortedSet<Integer> expected = new TreeSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        assertEquals(expected, actual);
    }

    // Sad Paths
    // 1. NonNumeric input
    @Test
    void nonNumericInputTest() {
        assertThrows(NumberFormatException.class, () -> FieldParser.parse("a-b", min, max));
    }

    // 2. Reversed range
    @Test
    void reversedRangeTest() {
        assertThrows(IllegalArgumentException.class,
                () -> FieldParser.parse("10-5/5", min, max));
    }

    // 3. 0 step
    @Test
    void zeroStepTest() {
        assertThrows(IllegalArgumentException.class,
                () -> FieldParser.parse("*/0", min, max));
    }

    // 4. Out of boundaries
    @Test
    void outOfBoundariesTest() {
        assertThrows(IllegalArgumentException.class,
                () -> FieldParser.parse("13", min, max));

        assertThrows(IllegalArgumentException.class,
                () -> FieldParser.parse("1", 2, max));

        assertThrows(IllegalArgumentException.class,
                () -> FieldParser.parse("1-13", min, max));

        assertThrows(IllegalArgumentException.class,
                () -> FieldParser.parse("1-12", 2, max));
    }

    // 5. Empty segment
    @Test
    void emptySegmentTest() {
        assertThrows(IllegalArgumentException.class,
                () -> FieldParser.parse("", min, max));

        assertThrows(IllegalArgumentException.class,
                () -> FieldParser.parse("-13", min, max));

        assertThrows(IllegalArgumentException.class,
                () -> FieldParser.parse("1-", min, max));

        assertThrows(IllegalArgumentException.class,
                () -> FieldParser.parse("-10/3", min, max));

        assertThrows(IllegalArgumentException.class,
                () -> FieldParser.parse("1-/3", min, max));
    }

    // 6. Space within segment
    @Test
    void spaceWithinSegmentTest() {
        assertThrows(NumberFormatException.class,
                () -> FieldParser.parse("1, 2, 3", min, max));

        assertThrows(NumberFormatException.class,
                () -> FieldParser.parse("1 - 12", min, max));

        assertThrows(NumberFormatException.class,
                () -> FieldParser.parse("* / 2", min, max));
    }
}
