package com.techgarden.trees.tree;

import com.techgarden.trees.components.Branch;
import com.techgarden.trees.components.Trunk;
import org.eclipse.collections.impl.factory.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class LeafTreeTest {
    private LeafTree tree;

    @Before
    public void setup() {
        List<Branch> branches = Lists.mutable.of(new Branch(30.0), new Branch(40.0));
        tree = new LeafTree(new Trunk(50.0), branches, 100.0);
    }

    @Test
    public void testGrow() {
        tree.grow(100.0);
        assertEquals(200.0, tree.getHeight());
        assertEquals(100.0, tree.getTrunk().getDiameter());
        assertEquals(130.0, tree.getBranch(0).getLength());
        assertEquals(140.0, tree.getBranch(1).getLength());
    }
}
