package com.fenwjian.sdcardutil;

public class RBTNode<T extends Comparable<? super T> > {
    private static final boolean RED   = false;
    private static final boolean BLACK = true;
    boolean color;        // 颜色
    T key;
    RBTNode<T> left;
    RBTNode<T> right;
    RBTNode<T> parent;
    public int usedUp=0;

    public RBTNode(T key, boolean color, RBTNode<T> parent, RBTNode<T> left, RBTNode<T> right) {
        this.key = key;
        this.color = color;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }

    public T getKey() {
        return key;
    }
    //!perilous operation
    public void setKey(T newVal) {
        key = newVal;
    }
    public String toString() {
        return ""+key+(this.color==RED?"(R)":"B");
    }

	public RBTNode<T> getLeft() {
		return left;
	}

	public RBTNode<T> getRight() {
		return right;
	}
}