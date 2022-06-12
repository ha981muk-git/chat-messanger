package edu.fra.uas.net.utill;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * convert Byte[] to Object
 *
 * @author kalnaasan
 */
public class Parser {
    /**
     * first 4 byte presents Opcode
     */
    private static final int START_OPCODE = 0;
    private static final int OPCODE_LENGTH = 4;

    private Parser() {
    }

    /**
     * convert bytes[] to Integer
     *
     * @param data  array of bytes
     * @param start start byte of array
     * @param end   end byte of array
     * @return int
     */
    private static int convertBytesToInt(byte[] data, int start, int end) {
        byte[] numArr = Arrays.copyOfRange(data, start, end);
        ByteBuffer wrapped = ByteBuffer.wrap(numArr); // big-endian by default
        return wrapped.getInt();
    }

    /**
     * convert byte[] to String
     *
     * @param data  array of bytes
     * @param start start byte of array
     * @param end   end byte of array
     * @return {@link String}
     */
    private static String convertBytesToString(byte[] data, int start, int end) {
        return new String(Arrays.copyOfRange(data, start, end));
    }

    /**
     * convert an Integer to byte[]
     *
     * @param number a number
     * @param length length of byte[]
     * @return byte[]
     */
    private static byte[] convertToBytes(int number, int length) {
        byte[] data = new byte[length];
        byte[] integerArr = BigInteger.valueOf(number).toByteArray();
        for (int i = 0; i < length; i++) {
            if (i < integerArr.length) {
                data[i] = integerArr[i];
            } else {
                data[i] = 0;
            }
        }
        return data;
    }

    /**
     * convert a String to byte[]
     *
     * @param text   a test
     * @param length length of array of bytes
     * @return byte[]
     */
    private static byte[] convertToBytes(String text, int length) {
        byte[] data = new byte[length];
        byte[] stringArr = text.getBytes();
        for (int i = 0; i < length; i++) {
            if (i < stringArr.length) {
                data[i] = stringArr[i];
            } else {
                data[i] = 0;
            }
        }
        return data;
    }

    /**
     * merge two arrays of bytes
     *
     * @param arr1 first array of bytes
     * @param arr2 second array of bytes
     * @return byte[]
     */
    private static byte[] mergeArrays(byte[] arr1, byte[] arr2) {
        byte[] merge = new byte[arr1.length + arr2.length];
        int counter = 0;
        for (int i = 0; i < arr1.length; i++) {
            merge[i] = arr1[i];
            counter++;
        }
        for (int i = 0; i < arr2.length; i++) {
            merge[counter++] = arr1[i];
        }
        return merge;
    }

    /**
     * detect opcode
     *
     * @param data byte[] of opcode
     * @return integer
     */
    public static int detectType(byte[] data) {
        return Parser.convertBytesToInt(data, START_OPCODE, Parser.OPCODE_LENGTH);
    }
}
