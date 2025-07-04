package com.deliverooassignment.cron;

import java.util.SortedSet;
import java.util.TreeSet;

public class FieldParser {
    public static SortedSet<Integer> parse(String spec, int min, int max) {
        SortedSet<Integer> result = new TreeSet<>();

        for (String part : spec.split(",")) {
            if (part.equals("*")) {
                for (int i = min; i <= max; i++) {
                    result.add(i);
                }
            } else if (part.contains("/")) {
                String[] split = part.split("/");
                String range = split[0];
                int step = Integer.parseInt(split[1]);

                int start = min, end = max;
                if (!range.equals("*")) {
                    String[] bounds = range.split("-");
                    start = Integer.parseInt(bounds[0]);
                    end   = Integer.parseInt(bounds[1]);
                }
                for (int i = start; i <= end; i += step) {
                    result.add(i);
                }
            } else if (part.contains("-")) {
                String[] bounds = part.split("-");
                int start = Integer.parseInt(bounds[0]);
                int end   = Integer.parseInt(bounds[1]);
                for (int i = start; i <= end; i++) {
                    result.add(i);
                }
            } else {
                result.add(Integer.parseInt(part));
            }
        }

        return result;
    }
}
