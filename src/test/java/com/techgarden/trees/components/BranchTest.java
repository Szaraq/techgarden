package com.techgarden.trees.components;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;


public class BranchTest {
    private Branch branch;

    @Before
    public void setup() {
        branch = new Branch(100.0);
    }

    @Test
    public void testIncreaseLength() {
        branch.increaseLength(50.0);
        assertEquals(150.0, branch.getLength());
    }
}
