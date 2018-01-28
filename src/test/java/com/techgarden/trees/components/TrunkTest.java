package com.techgarden.trees.components;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TrunkTest {
    private Trunk trunk;

    @Before
    public void setup() {
        trunk = new Trunk(100.0);
    }

    @Test
    public void testIncreaseDiameter() {
        trunk.increaseDiameter(50.0);
        assertEquals(150.0, trunk.getDiameter());
    }
}
