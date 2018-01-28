package com.techgarden.trees.tree;

import com.techgarden.trees.Tree;
import com.techgarden.trees.components.Branch;
import com.techgarden.trees.components.Trunk;

import java.util.List;

public class LeafTree extends Tree {

    public LeafTree(Trunk trunk, List<Branch> branches, double height) {
        super(trunk, branches, height);
    }

    @Override
    public void grow(double centimeters) {
        super.grow(centimeters);
        trunk.increaseDiameter(0.5 * centimeters);
        branches.forEach(branch -> branch.increaseLength(centimeters));
    }
}
