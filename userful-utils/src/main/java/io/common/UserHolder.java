package io.common;

/**
 * Author: ZhaoXing

 */

/**
 * ThreadLocal
 * @param
 */
public class UserHolder {
    private static final ThreadLocal tl = new ThreadLocal<>();


    public static <T> void saveUser(T user){
        tl.set(user);
    }

    public static <T> T getUser(){
        return (T)tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }
}
