package org.example;

public class Node {
    public int value;
    public Node up;
    public Node down;
    //public Node left;
    //public Node right;
    public ColumnNode top;

    public Node() {
        this.up = null;
        this.down = null;
        this.top = null;
        //this.left = null;
        //this.right = null;
        this.value = 0;
    }

    public Node(int value) {
        this.up = null;
        this.down = null;
        this.top = null;
        this.value = value;
    }
}
