package com.yammer.breakerbox.service.comparable;

import com.yammer.azure.core.TableType;

import java.util.Comparator;

import static com.google.common.base.Preconditions.checkNotNull;

public class DescendingRowVersionFirstOrder<T extends TableType> implements Comparator<T> {

    private final String priorityTerm;

    public DescendingRowVersionFirstOrder(String priorityTerm) {
        this.priorityTerm = TimeUtil.trimMillis(checkNotNull(priorityTerm));
    }

    @Override
    public int compare(T left, T right) {
        if (left == null || left.getRowKey() == null || "null".equals(left.getRowKey())) return 1;
        if (right == null || right.getRowKey() == null || "null".equals(right.getRowKey())) return -1;

        final String leftRowKey = TimeUtil.trimMillis(left.getRowKey());
        final String rightRowKey = TimeUtil.trimMillis(right.getRowKey());

        if(priorityTerm.equals(leftRowKey)) return -1;
        if(priorityTerm.equals(rightRowKey)) return 1;

        return Long.valueOf(rightRowKey).compareTo(Long.valueOf(leftRowKey));
    }
}
