package com.vgr.assignment.model;

public record Box(int id, int width, int length) implements Comparable<Box> {
    @Override public int compareTo(Box o) {
        return Long.compare((long) width * length, (long) o.width * o.length);
    }
}
