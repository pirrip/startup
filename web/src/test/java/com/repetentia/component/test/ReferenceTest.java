package com.repetentia.component.test;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

class BigData {
    private int[] array = new int[2500]; //10000byte, 10K
}

public class ReferenceTest {
    private List<WeakReference<BigData>> weakRefs = new LinkedList<>();
    private List<SoftReference<BigData>> softRefs = new LinkedList<>();
    private List<BigData> strongRefs = new LinkedList<>();


    public void weakReferenceTest() {
        List<WeakReference<BigData>> weakRefs = new LinkedList<>();
        try {
            for (int i = 0; true; i++) {
                weakRefs.add(new WeakReference<BigData>(new BigData()));
            }
        } catch (OutOfMemoryError ofm) { // weak일 경우 out of memory 발생 하지 않는다.
            System.out.println("out of memory!");
        }
    }

    public void softReferenceTest() {
        try {
            for (int i = 0; true; i++) {
            	if (i % 1000 == 0) System.out.println(i);
                softRefs.add(new SoftReference<BigData>(new BigData()));
            }
        } catch (OutOfMemoryError ofm) { // weak일 경우 out of memory 발생 하지 않는다.
            System.out.println("out of memory!");
        }
    }


    public void strongReferenceTest() {
        try {
            for (int i = 0; true; i++) {
            	if (i % 1000 == 0) {
            		System.out.println(i);
            	}
                strongRefs.add(new BigData());
            }
        } catch (OutOfMemoryError ofm) { // Strong일 경우 out of memory 발생
        	System.out.println("came here");
        	ofm.printStackTrace();
            System.out.println("out of memory!");
            strongReferenceTest();
        }
    }


    public static void main(String[] args) {
    	ReferenceTest rt = new ReferenceTest();
    	try {
    		rt.strongReferenceTest();
    	} catch (Throwable t) {
    		System.out.println("catching t");
    	}

    }
}
