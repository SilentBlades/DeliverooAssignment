package com.deliverooassignment.cron;

import java.util.SortedSet;
import java.util.stream.Collectors;

public class CronExpression {
    private final SortedSet<Integer> minute, hour, dayOfMonth, month, dayOfWeek;
    private final String command;

    private CronExpression(SortedSet<Integer> minute,
                           SortedSet<Integer> hour,
                           SortedSet<Integer> dayOfMonth,
                           SortedSet<Integer> month,
                           SortedSet<Integer> dayOfWeek,
                           String command) {
        this.minute = minute;
        this.hour = hour;
        this.dayOfMonth = dayOfMonth;
        this.month = month;
        this.dayOfWeek = dayOfWeek;
        this.command = command;
    }

    public static CronExpression parse(String line) {
        String[] parameters = line.trim().split("\\s+", 6);
        if (parameters.length < 6) {
            throw new IllegalArgumentException("Cron line must have 6 parameters");
        }

        SortedSet<Integer> minute = FieldParser.parse(parameters[0], Field.MINUTE.getMin(), Field.MINUTE.getMax());
        SortedSet<Integer> hour = FieldParser.parse(parameters[1], Field.HOUR.getMin(), Field.HOUR.getMax());
        SortedSet<Integer> dayOfMonth = FieldParser.parse(parameters[2], Field.DAY_OF_MONTH.getMin(), Field.DAY_OF_MONTH.getMax());
        SortedSet<Integer> month = FieldParser.parse(parameters[3], Field.MONTH.getMin(), Field.MONTH.getMax());
        SortedSet<Integer> dayOfWeek = FieldParser.parse(parameters[4], Field.DAY_OF_WEEK.getMin(), Field.DAY_OF_WEEK.getMax());
        String command = parameters[5];

        return new CronExpression(minute, hour, dayOfMonth, month, dayOfWeek, command);
    }

    public void printExpanded() {
        print(Field.MINUTE.getLabel(), minute);
        print(Field.HOUR.getLabel(), hour);
        print(Field.DAY_OF_MONTH.getLabel(), dayOfMonth);
        print(Field.MONTH.getLabel(), month);
        print(Field.DAY_OF_WEEK.getLabel(), dayOfWeek);
        System.out.printf("%-14s%s%n", "command", command);
    }

    private void print(String label, SortedSet<Integer> values) {
        String valuesAsSingleString = values.stream()
                .map(Object::toString)
                .collect(Collectors.joining(" "));
        System.out.printf("%-14s%s%n", label, valuesAsSingleString);
    }
}
