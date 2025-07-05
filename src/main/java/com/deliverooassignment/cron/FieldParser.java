package com.deliverooassignment.cron;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * This method parses each segment of the cron. Each segment of the cron could be:
 * <ol>
 *  <li>Limits: 1,2,3,4 or 1 or *</li>
 *  <li>Wildcard: *</li>
 *  <li>Range: start-end</li>
 *  <li>Step: &#42/n or start-end/n</li>
 * </ol>
 *
 * <ul>
 *   <li><b>Limits:</b>
 *     <li>Add all the numbers in the list to the result</li>
 *     <li>Validation: The numbers should be in the valid range(inclusive) and not empty</li>
 *   </li>
 *
 *   <li><b>Wildcard:</b>
 *     <li>Add all numbers between min(inclusive) and max(inclusive)</li>
 *   </li>
 *
 *   <li><b>Range:</b>
 *     <li>Add all numbers between start(inclusive)-end(inclusive)</li>
 *     <li>Validation: start < end; start >=min and end <= max and start and end should not be empty</></li>
 *   </li>
 *
 *   <li><b>Step:</b>
 *     <li>Add all numbers by skipping n numbers. * denotes all numbers between min and max. start-end denotes a range</li>
 *     <li>Validation: n cannot be <= 0; All validations for range also apply here</li>
 *   </li>
 * </ul>
 */

public class FieldParser {
    public static SortedSet<Integer> parse(String spec, int min, int max) {
        SortedSet<Integer> result = new TreeSet<>();

        // 1. limits
        for (String segment : spec.split(",")) {
            // 2. Wildcard
            if (segment.equals("*")) {
                for (int i = min; i <= max; i++) {
                    result.add(i);
                }
            }
            // 3. Step
            else if (segment.contains("/")) {
                String[] split = segment.split("/");
                if(split.length != 2 || split[0].isEmpty() || split[1].isEmpty()) {
                    throw new IllegalArgumentException("Invalid step specified: " + segment);
                }

                String range = split[0];
                int start = min, end = max;
                int step = Integer.parseInt(split[1]);
                if(step <= 0) {
                    throw new IllegalArgumentException("Step <= 0: " + segment);
                }

                if (!range.equals("*")) {
                    String[] bounds = range.split("-");
                    if(bounds.length != 2 || bounds[0].isEmpty() || bounds[1].isEmpty()) {
                        throw new IllegalArgumentException("Invalid range in step: " + segment);
                    }
                    start = Integer.parseInt(bounds[0]);
                    end   = Integer.parseInt(bounds[1]);
                    if(start > end) {
                        throw new IllegalArgumentException("Incorrect range specified: " + segment);
                    }
                    if(start < min || end > max) {
                        throw new IllegalArgumentException("Segment is out of bounds: " + segment);
                    }
                }
                for (int i = start; i <= end; i += step) {
                    result.add(i);
                }
            }
            // 4. Range
            else if (segment.contains("-")) {
                String[] bounds = segment.split("-");
                if(bounds.length != 2 || bounds[0].isEmpty() || bounds[1].isEmpty()) {
                    throw new IllegalArgumentException("Invalid range in step: " + segment);
                }
                int start = Integer.parseInt(bounds[0]);
                int end   = Integer.parseInt(bounds[1]);
                if(start > end) {
                    throw new IllegalArgumentException("Incorrect range specified: " + segment);
                }
                if(start < min || end > max) {
                    throw new IllegalArgumentException("Segment is out of bounds: " + segment);
                }
                for (int i = start; i <= end; i++) {
                    result.add(i);
                }
            } else {
                if(Integer.parseInt(segment) < min || Integer.parseInt(segment) > max) {
                    throw new IllegalArgumentException("Segment is out of bounds: " + segment);
                }
                result.add(Integer.parseInt(segment));
            }
        }

        return result;
    }
}
