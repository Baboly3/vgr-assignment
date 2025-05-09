package com.vgr.assignment.config;

import com.vgr.assignment.PackingApplication;
import com.vgr.assignment.model.Box;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = PackingApplication.class)
class BoxPropsIT {

    @Autowired
    private BoxProps boxProps;

    @Test
    void yamlIsLoadedIntoBoxProperties() {
        assertNotNull(boxProps.types(), "Map skall vara bunden och ej null");
        assertFalse(boxProps.types().isEmpty(), "Map får inte vara tom");
        assertTrue(boxProps.types().containsKey(1), "Box-typ 1 saknas i YAML");

        Box box1 = boxProps.types().get(1);
        assertEquals(4, box1.width(), "Box 1 width ska vara 4");
        assertEquals(5, box1.length(), "Box 1 length ska vara 5");

        Box box2 = boxProps.types().get(2);
        assertNotNull(box2, "Box-typ 2 förväntas finnas");
        assertEquals(8, box2.width());
        assertEquals(12, box2.length());
    }
}
