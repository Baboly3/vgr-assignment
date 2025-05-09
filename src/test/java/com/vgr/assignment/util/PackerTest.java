package com.vgr.assignment.util;

import com.vgr.assignment.model.Article;
import com.vgr.assignment.model.Box;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PackerTest {

    private final Box box1 = new Box(1, 4, 5);
    private final Box box2 = new Box(2, 8, 12);
    private final Box box3 = new Box(3, 12, 20);

    private final Map<Integer, Article> prototypes = Map.of(
            1, new Article(1,1,1),
            2, new Article(2,1,2),
            3, new Article(3,1,4),
            4, new Article(4,1,6),
            5, new Article(5,1,8),
            6, new Article(6,1,9),
            7, new Article(7,1,12),
            8, new Article(8,1,5),
            9, new Article(9,1,9),
            10, new Article(10,8,12)
    );

    @Test
    void singleSmallArticleFitsBox1() {
        List<Article> articles = expandArticles(Map.of(1,1));
        assertTrue(Packer.fits(box1, articles));
    }

    @Test
    void sampleCase1GivesBox2() {
        Map<Integer,Integer> order = Map.of(7,6, 4,2, 1,4);
        List<Article> articles = expandArticles(order);

        assertFalse(Packer.fits(box1, articles));
        assertTrue(Packer.fits(box2, articles));
    }

    @Test
    void pickupRequiredWhenTooManyItems() {
        Map<Integer,Integer> order = Map.of(7,12, 1,100);
        List<Article> articles = expandArticles(order);

        assertFalse(Packer.fits(box1, articles));
        assertFalse(Packer.fits(box2, articles));
    }

    @Test
    void wideArticleType10FitsBox2() {
        List<Article> articles = expandArticles(Map.of(10,1));

        assertFalse(Packer.fits(box1, articles));
        assertTrue(Packer.fits(box2, articles));
    }

    @Test
    void unknownTypeOrWrongWidthReturnsFalseOrTrue() {
        List<Article> unknown = expandArticles(Map.of(99,1));
        assertFalse(Packer.fits(box1, unknown));
        Map<Integer, Article> badProto = Map.of(1, new Article(1,2,3));
        List<Article> single = expandArticles(Map.of(1,1), badProto);

        assertTrue(Packer.fits(box1, single));
    }

    /**
     * Hjälpmetod som expanderar en order-karta till en lista av Article
     */
    private List<Article> expandArticles(Map<Integer,Integer> order) {
        return expandArticles(order, prototypes);
    }

    private List<Article> expandArticles(Map<Integer,Integer> order,
                                         Map<Integer,Article> protoMap) {
        List<Article> list = new ArrayList<>();
        for (var entry : order.entrySet()) {
            Article proto = protoMap.get(entry.getKey());
            if (proto == null) {
                return List.of();
            }
            IntStream.range(0, entry.getValue())
                    .mapToObj(i -> proto)
                    .forEach(list::add);
        }
        return list;
    }
}