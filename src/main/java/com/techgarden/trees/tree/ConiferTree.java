package com.techgarden.trees.tree;

import com.techgarden.trees.Tree;
import com.techgarden.trees.components.Branch;
import com.techgarden.trees.components.Trunk;

import java.util.List;

public class ConiferTree extends Tree {

    public ConiferTree(Trunk trunk, List<Branch> branches, double height) {
        super(trunk, branches, height);
    }

    @Override
    public void grow(double centimeters) {
        super.grow(centimeters);
        trunk.increaseDiameter(0.75 * centimeters);
        branches.forEach(branch -> branch.increaseLength(centimeters));
    }
}
