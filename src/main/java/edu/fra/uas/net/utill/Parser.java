package edu.fra.uas.net.utill;

import edu.fra.uas.net.message.Data;
import edu.fra.uas.net.model.Message;
import edu.fra.uas.net.model.User;

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
    /**
     * Byte 5 to Byte 20 present username
     */
    private static final int START_USERNAME = 4;
    private static final int USERNAME_LENGTH = 16;
    /**
     * Byte 21 to Byte 36 present host
     */
    private static final int START_HOST = 20;
    private static final int HOST_LENGTH = 16;
    /**
     * Byte 37 to Byte 44 present port
     */
    private static final int START_PORT = 36;
    private static final int PORT_LENGTH = 8;
    /**
     * Byte 21 to Byte 36 present receiver
     */
    private static final int START_RECEIVER = 20;
    private static final int RECEIVER_LENGTH = 16;
    /**
     * Byte 37 to Byte 44 present type
     */
    private static final int START_TYPE = 36;
    private static final int TYPE_LENGTH = 8;
    /**
     * Byte 45 to end of byte[] present content
     */
    private static final int START_MESSAGE = 44;
    /**
     * opcode to register a client
     */
    public static final int REGISTER = 0x01;
    /**
     * opcode to deregister a client
     */
    public static final int DEREGISTER = 0X02;
    /**
     * opcode presents message
     */
    public static final int MESSAGE = 0x03;
    /**
     * opcode to return a List of users
     */
    public static final int CLIENTS = 0x04;

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

    /**
     * convert byte[] to User
     *
     * @param data byte[] of register a user
     * @return User
     */
    public static User convertBytesToUser(byte[] data) {
        String username = Parser.convertBytesToString(data, OPCODE_LENGTH, OPCODE_LENGTH + USERNAME_LENGTH);
        String hostname = Parser.convertBytesToString(data, START_HOST, START_HOST + HOST_LENGTH);
        int port = Parser.convertBytesToInt(data, START_PORT, START_PORT + PORT_LENGTH);
        return new User(username, hostname, port);
    }

    /**
     * convert a User to array of bytes
     *
     * @param username name of client
     * @param hostname host of client
     * @param port     port of client
     * @return byte[]
     */
    public static byte[] createByteArray(String username, String hostname, int port) {
        // convert opcode to 4-byte[]
        byte[] data = convertToBytes(REGISTER, OPCODE_LENGTH);
        // convert username to 16-byte[]
        byte[] usernameArr = convertToBytes(username, USERNAME_LENGTH);
        data = mergeArrays(data, usernameArr);
        // convert hostname to 16-byte[]
        byte[] hostnameArr = convertToBytes(hostname, HOST_LENGTH);
        data = mergeArrays(data, hostnameArr);
        // convert port to 8-byte[]
        byte[] portArr = convertToBytes(port, PORT_LENGTH);
        data = mergeArrays(data, portArr);
        return data;
    }

    /**
     * convert byte[] to Message
     *
     * @param data array of byte
     * @return Message
     */
    public static Message convertBytesToMessage(byte[] data) {
        String sender = convertBytesToString(data, START_USERNAME, START_USERNAME + USERNAME_LENGTH);
        String receiver = convertBytesToString(data, START_RECEIVER, START_RECEIVER + RECEIVER_LENGTH);
        String type = convertBytesToString(data, START_TYPE, START_TYPE + TYPE_LENGTH);
        Data content = new Data(Arrays.copyOfRange(data, START_MESSAGE, START_MESSAGE + data.length));
        return new Message(sender, receiver, type, content);
    }

    /**
     * convert a Message to array of bytes
     *
     * @param message message
     * @return byte[]
     */
    public static byte[] createByteArray(Message message) {
        // convert opcode to 4-byte[]
        byte[] data = convertToBytes(MESSAGE, OPCODE_LENGTH);
        // convert sender to 16-byte[]
        byte[] senderArr = convertToBytes(message.getSender(), USERNAME_LENGTH);
        data = mergeArrays(data, senderArr);
        // convert receiver to 16-byte[]
        byte[] receiverArr = convertToBytes(message.getReceiver(), RECEIVER_LENGTH);
        data = mergeArrays(data, receiverArr);
        // merge content with data
        data = mergeArrays(data, message.getContent().getData());
        return data;
    }

    /**
     * create byte[] from opcode and username
     *
     * @param opcode   type of array
     * @param username name of client
     * @return byte[]
     */
    public static byte[] createByteArray(int opcode, String username) {
        byte[] data = convertToBytes(opcode, OPCODE_LENGTH);
        data = mergeArrays(data, convertToBytes(username, USERNAME_LENGTH));
        return data;
    }
}