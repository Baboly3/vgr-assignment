package com.vgr.assignment.config;

import com.vgr.assignment.PackingApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = PackingApplication.class)
class ArticlePropsIT {

    @Autowired
    private ArticleProps props;

    @Test
    void yamlIsLoadedIntoArticleProperties() {
        assertNotNull(props.types(), "Map skall vara bunden och ej null");
        assertFalse(props.types().isEmpty(), "Map får inte vara tom");
        assertTrue(props.types().containsKey(1), "Artikel typ 1 saknas i YAML");
        assertEquals(1, props.types().get(1).width());
        assertEquals(1, props.types().get(1).length());
    }


}