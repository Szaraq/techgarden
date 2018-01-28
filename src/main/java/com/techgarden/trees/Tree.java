package com.techgarden.trees;

import com.techgarden.trees.components.Branch;
import com.techgarden.trees.components.Trunk;

import java.util.List;

public abstract class Tree {
    protected Trunk trunk;
    protected List<Branch> branches;
    protected double height;

    public Tree(Trunk trunk, List<Branch> branches, double height) {
        this.trunk = trunk;
        this.branches = branches;
        this.height = height;
    }

    public Trunk getTrunk() {
        return trunk;
    }

    public Branch getBranch(int index) {
        return branches.get(index);
    }

    public List<Branch> getAllBranches() {
        return branches;
    }

    public double getHeight() {
        return height;
    }

    public void grow(double centimeters) {
        height += centimeters;
    }
}
