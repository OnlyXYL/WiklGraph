package top.wikl.wikljava;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author XYL
 * @version 1.2
 * @since 2020/12/30 0030 17:48
 */
public class Test {

    public static void main(String[] args) {


        String s = MD5("员工");

        String s1 = MD5("员工");

        System.out.println("s:" + s + "===" + s1);

    }


    /**
     * 计算字符串的md5
     */
    public static String MD5(String input) {
        if (input == null || input.length() == 0) {
            return null;
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(input.getBytes());
            byte[] byteArray = md5.digest();

            char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            // 一个字节对应两个16进制数，所以长度为字节数组乘2
            char[] charArray = new char[byteArray.length * 2];
            int index = 0;
            for (byte b : byteArray) {
                charArray[index++] = hexDigits[b >>> 4 & 0xf];
                charArray[index++] = hexDigits[b & 0xf];
            }
            return new String(charArray);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
