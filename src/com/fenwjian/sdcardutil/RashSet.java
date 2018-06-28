package com.fenwjian.sdcardutil;

import java.util.ArrayList;

/**
 * Java 语言: 红黑树
 *
 * @author skywang
 * @date 2013/11/07
 * @editor KnIfER
 * @date 2017/11/18
 */

public class RashSet<T1 extends Comparable<T1>>
	extends RBTree<T1> {
	
	RBTNode<T1> lastSearchRes;
	
	public boolean contains(T1 key) {
		RBTNode<T1> tmp = search(key);
		if(tmp!=null) {
			lastSearchRes=tmp;
			return true;
		}
		return false;
	}

	
	public void put(T1 key) {
		insert(key);
	}


	public void removeLastSelected() {
		remove(lastSearchRes);
	}
	
	public  RBTNode<T1> getLastSelected() {
		return lastSearchRes;
	}

	
	@Override
    public RBTNode<T1> xxing_samsara(T1 val){
        RBTNode<T1> tmpnode =downwardNeighbour_skipego(this.mRoot,val);
        
        //	return this.maximum(this.mRoot);
        return tmpnode;
    }
	
	@Override
    public RBTNode<T1> sxing_samsara(T1 val){
        RBTNode<T1> tmpnode =upwardNeighbour_skipego(this.mRoot,val);
        
        //	return this.minimum(this.mRoot);
        return tmpnode;
    }



	
	
	
}