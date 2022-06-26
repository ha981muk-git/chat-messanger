package edu.fra.uas.net.utill;

import edu.fra.uas.net.model.Message;
import edu.fra.uas.net.model.User;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * convert Byte[] to Object <br>
 * <p>
 * |4-byte|16-byte|16-byte |
 * |opcode|sender |receiver|
 * <p>
 * |4-byte|16-bytes|16-bytes|16-bytes |2-bytes|
 * |opcode|sender  |receiver|Hostname |port   |
 * <p>
 * |4-byte|16-byte|16-byte |
 * |opcode |sender |receiver|
 * <p>
 * |4-byte|16-byte|16-byte |4-byte|any-length|
 * |opcode|sender |receiver|type  |content   |
 * <p>
 * |4-byte|16-byte|16-byte |
 * |opcode|sender |receiver|
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
    private static final int START_SENDER = START_OPCODE + OPCODE_LENGTH;
    private static final int SENDER_LENGTH = 16;
    /**
     * Byte 21 to Byte 36 present receiver
     */
    private static final int START_RECEIVER = START_SENDER + SENDER_LENGTH;
    private static final int RECEIVER_LENGTH = 16;
    /**
     * Byte 37 to Byte 52 present host
     */
    private static final int START_HOST = START_RECEIVER + RECEIVER_LENGTH;
    private static final int HOST_LENGTH = 16;
    /**
     * Byte 53 to Byte 68 present port
     */
    private static final int START_PORT = START_HOST + HOST_LENGTH;
    private static final int PORT_LENGTH = 4;
    /**
     * Byte 37 to Byte 44 present type
     */
    private static final int START_TYPE = START_RECEIVER + RECEIVER_LENGTH;
    private static final int TYPE_LENGTH = 4;
    /**
     * Byte 45 to end of byte[] present content
     */
    private static final int START_MESSAGE = START_TYPE + TYPE_LENGTH;
    /**
     * opcode to register a client
     */
    public static final int REGISTER = 0x01;
    /**
     * opcode to deregister a client
     */
    public static final int DEREGISTER = 0X02;
    /**
     * opcode to return a List of users
     */
    public static final int SEARCH = 0x03;
    /**
     * opcode presents message
     */
    public static final int POLLER = 0x04;
    /**
     * opcode presents message
     */
    public static final int MESSAGE = 0x05;

    public static final int MESSAGE_TYPE_REGISTER = 0x01;
    public static final int MESSAGE_TYPE_DEREGISTER = 0x02;
    public static final int MESSAGE_TYPE_SEARCH = 0x03;
    public static final int MESSAGE_TYPE_MESSAGE = 0x04;
    public static final int MESSAGE_TYPE_FILE = 0x05;


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
        return Parser.deleteEmptyBytes(new String(Arrays.copyOfRange(data, start, end)));
    }

    /**
     * convert an Integer to byte[]
     *
     * @param number a number
     * @param length length of byte[]
     * @return byte[]
     */
    private static byte[] convertToBytes(int number, int length) {
        byte[] integerArr = BigInteger.valueOf(number).toByteArray();
        byte[] data = new byte[length - integerArr.length];
        data = Parser.mergeArrays(data, integerArr);
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
        byte[] stringArr = text.getBytes();
        byte[] data = new byte[length - stringArr.length];
        data = Parser.mergeArrays(data, stringArr);
        return data;
    }

    /**
     * merge two arrays of bytes
     *
     * @param arr1 first array of bytes
     * @param arr2 second array of bytes
     * @return byte[]
     */
    public static byte[] mergeArrays(byte[] arr1, byte[] arr2) {
        byte[] merge = new byte[arr1.length + arr2.length];
        int counter = 0;
        for (int i = 0; i < arr1.length; i++) {
            merge[i] = arr1[i];
            counter++;
        }
        for (byte b : arr2) {
            merge[counter++] = b;
        }
        return merge;
    }

    /**
     * to create basic byte[] from opcode, sender and receiver
     *
     * @param opcode   type of content
     * @param sender   message is sent by Client
     * @param receiver message should be received by Client
     * @return byte[]
     */
    private static byte[] createBasesArray(int opcode, String sender, String receiver) {
        // convert opcode to byte[]
        byte[] data = Parser.convertToBytes(opcode, Parser.OPCODE_LENGTH);
        // convert sender to byte[]
        data = Parser.mergeArrays(data, Parser.convertToBytes(sender, SENDER_LENGTH));
        // convert receiver to byte[]
        data = Parser.mergeArrays(data, Parser.convertToBytes(receiver, RECEIVER_LENGTH));
        return data;
    }

    /**
     * detect opcode
     *
     * @param data byte[] of opcode
     * @return integer
     */
    public static int detectType(byte[] data) {
        return Parser.convertBytesToInt(data, START_OPCODE, Parser.START_OPCODE + Parser.OPCODE_LENGTH);
    }

    /**
     * convert byte[] to User
     *
     * @param data byte[] of register a user
     * @return User
     */
    public static User convertBytesToUser(byte[] data) {
        String sender = Parser.convertBytesToString(data, START_SENDER, START_SENDER + SENDER_LENGTH);
        String hostname = Parser.convertBytesToString(data, START_HOST, START_HOST + HOST_LENGTH);
        int port = Parser.convertBytesToInt(data, START_PORT, START_PORT + PORT_LENGTH);
        return new User(sender, hostname, port);
    }

    /**
     * convert a User to array of bytes
     *
     * @param sender   name of client
     * @param hostname host of client
     * @param port     port of client
     * @return byte[]
     */
    public static byte[] createByteArray(String sender, String hostname, int port) {
        // Create bases byte[]
        byte[] data = Parser.createBasesArray(Parser.REGISTER, sender, "server");
        // convert hostname to byte[]
        data = Parser.mergeArrays(data, Parser.convertToBytes(hostname, Parser.HOST_LENGTH));
        // convert port to byte[]
        data = Parser.mergeArrays(data, Parser.convertToBytes(port, Parser.PORT_LENGTH));
        return data;
    }

    /**
     * convert byte[] to Message
     *
     * @param data array of byte
     * @return Message
     */
    public static Message convertBytesToMessage(byte[] data) {
        String sender = convertBytesToString(data, START_SENDER, START_SENDER + SENDER_LENGTH);
        String receiver = convertBytesToString(data, START_RECEIVER, START_RECEIVER + RECEIVER_LENGTH);
        int type = convertBytesToInt(data, START_TYPE, START_TYPE + TYPE_LENGTH);
        byte[] content = Arrays.copyOfRange(data, Parser.START_MESSAGE, data.length);
        return new Message(sender, receiver, type, content);
    }

    /**
     * convert a Message to array of bytes
     *
     * @param message message
     * @return byte[]
     */
    public static byte[] createByteArray(Message message) {
        byte[] data = Parser.createBasesArray(Parser.MESSAGE, message.getSender(), message.getReceiver());
        // convert type to bytes and merge it into byte[]
        data = Parser.mergeArrays(data, Parser.convertToBytes(message.getType(), Parser.TYPE_LENGTH));
        // merge content into byte[]
        data = mergeArrays(data, message.getContent());
        return data;
    }

    /**
     * create byte[] from opcode and username
     *
     * @param opcode type of array
     * @param sender name of client
     * @return byte[]
     */
    public static byte[] createByteArray(int opcode, String sender) {
        return Parser.createBasesArray(opcode, sender, "server");
    }

    public static String getSenderFromBytes(byte[] data) {
        return Parser.convertBytesToString(data, OPCODE_LENGTH, OPCODE_LENGTH + SENDER_LENGTH);
    }

    private static String deleteEmptyBytes(String text) {
        int tmp = 0;
        byte[] data = text.getBytes();
        for (byte b : data) {
            if (b == 0) tmp++;
        }

        return new String(Arrays.copyOfRange(data, tmp, data.length));
    }

    /**
     * to get receiver from byte[]
     *
     * @param data byte[]
     * @return String {@link String}
     */
    public static String getReceiverFromBytes(byte[] data) {
        return Parser.convertBytesToString(data, Parser.START_RECEIVER, Parser.START_RECEIVER + Parser.RECEIVER_LENGTH);
    }
}
