package com.vgr.assignment.util;

import com.vgr.assignment.model.Article;
import com.vgr.assignment.model.Box;

import java.util.*;

/**
 * Packing utilities for placing articles into boxes.
 */
public final class Packer {
    private Packer() {}


    public static boolean fits(Box box, List<Article> articles) {
        if (articles == null || articles.isEmpty()) {
            return false;
        }
        boolean allWidthOne = articles.stream().allMatch(a -> a.width() == 1);
        if (allWidthOne) {
            return packByHeight(box, articles);
        }

        return packByFirstFit(box, articles);
    }

    private static boolean packByFirstFit(Box box, List<Article> articles) {
        int boxWidth = box.width();
        int boxHeight = box.length();

        List<Article> sorted = new ArrayList<>(articles);
        sorted.sort(Comparator.comparingInt(Article::width).reversed()
                .thenComparingInt(Article::length).reversed());

        int[] cols = new int[boxWidth];
        Arrays.fill(cols, 0);

        for (Article art : sorted) {
            if (!place(art, cols, boxHeight)) {
                return false;
            }
        }
        return true;
    }

    private static boolean packByHeight(Box box, List<Article> articles) {
        int boxWidth = box.width();
        int boxHeight = box.length();

        int maxHeight = articles.stream()
                .mapToInt(Article::length)
                .max().orElse(0);
        int[] hist = new int[maxHeight + 1];
        for (Article art : articles) {
            hist[art.length()]++;
        }

        int[] cols = new int[boxWidth];
        Arrays.fill(cols, 0);

        for (int h = maxHeight; h >= 1; h--) {
            for (int i = 0; i < hist[h]; i++) {
                int idx = shortestColumn(cols);
                if (cols[idx] + h > boxHeight) {
                    return false;
                }
                cols[idx] += h;
            }
        }
        return true;
    }

    private static int shortestColumn(int[] cols) {
        int idx = 0;
        for (int i = 1; i < cols.length; i++) {
            if (cols[i] < cols[idx]) {
                idx = i;
            }
        }
        return idx;
    }

    private static boolean place(Article art, int[] cols, int maxHeight) {
        int w = art.width();
        int h = art.length();
        for (int start = 0; start <= cols.length - w; start++) {
            if (canPlace(start, w, h, cols, maxHeight)) {
                for (int i = start; i < start + w; i++) {
                    cols[i] += h;
                }
                return true;
            }
        }
        return false;
    }

    private static boolean canPlace(int start, int width, int height, int[] cols, int limit) {
        for (int i = start; i < start + width; i++) {
            if (cols[i] + height > limit) {
                return false;
            }
        }
        return true;
    }
}