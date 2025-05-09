package com.vgr.assignment.service;


import com.vgr.assignment.config.ArticleProps;
import com.vgr.assignment.config.BoxProps;
import com.vgr.assignment.model.Article;
import com.vgr.assignment.model.Box;
import com.vgr.assignment.util.Packer;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;

@Service
public class PackingService {

    private final List<Box> boxes;
    private final Map<Integer, Article> articleSpecs;

    public PackingService(BoxProps boxProps,
                          ArticleProps articleProps) {
        this.boxes = boxProps.types().entrySet().stream()
                .map(e -> {
                    int id = e.getKey();
                    var p = e.getValue();
                    return new Box(id, p.width(), p.length());
                })
                .sorted(Comparator.comparingInt(box -> box.width() * box.length()))
                .toList();

        this.articleSpecs = Collections.unmodifiableMap(articleProps.types());
    }

    public Optional<Integer> chooseBox(Map<Integer, Integer> order) {
        List<Article> articles = order.entrySet().stream()
                .flatMap(entry -> {
                    Article prototype = articleSpecs.get(entry.getKey());
                    if (prototype == null) {
                        throw new IllegalArgumentException("Okänd artikeltyp: " + entry.getKey());
                    }
                    return IntStream.range(0, entry.getValue())
                            .mapToObj(i -> prototype);
                })
                .toList();
        return boxes.stream()
                .filter(box -> Packer.fits(box, articles))
                .findFirst()
                .map(Box::id);
    }
}

