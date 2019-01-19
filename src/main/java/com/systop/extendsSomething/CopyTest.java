package com.systop.extendsSomething;

import java.lang.reflect.Array;
import java.util.Arrays;

public class CopyTest {
    public static void main(String[] args){
        String[] b = {"Tom","Dick","Harry"};
        b = (String[])goodCopy(b,10);
        System.out.println(Arrays.toString(b));
    }

    private static Object goodCopy(Object b, int newLength) {
        Class cl = b.getClass();
        if(!cl.isArray()) return  null;
        Class componentType = cl.getComponentType();
        int length = Array.getLength(b);
        Object newArray = Array.newInstance(componentType, newLength);
        System.arraycopy(b,0,newArray,0,Math.min(length,newLength));
        return newArray;
    }

}
