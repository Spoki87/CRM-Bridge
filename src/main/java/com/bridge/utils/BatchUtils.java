package com.bridge.utils;

import java.util.ArrayList;
import java.util.List;

public class BatchUtils {
    public static <T> List<List<T>> splitIntoBatches(List<T> sourceList, int batchSize) {
        List<List<T>> batches = new ArrayList<>();
        for (int i = 0; i < sourceList.size(); i += batchSize) {
            int end = Math.min(sourceList.size(), i + batchSize);
            batches.add(new ArrayList<>(sourceList.subList(i, end)));
        }
        return batches;
    }
}
