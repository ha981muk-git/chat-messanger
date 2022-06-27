package edu.fra.uas.net.utill;

import edu.fra.uas.net.gui.Client_old;

import java.util.Random;

public class Constant {
    public static final String SERVE_HOSTNAME = "127.0.0.1";
    public static final int SERVE_PORT = 8080;
    public static final String CLIENT_HOSTNAME = "127.0.0.1";
    public static final int CLIENT_PORT = Constant.generatePort();
    public static final int MIN_PORT = 65000;
    public static final int MAX_PORT = 65100;
    public static final int SERVER_PORT = 8080;
    public static final int FRAME_X = 200;
    public static final int FRAME_Y = 200;
    public static final int FRAME_WIDTH = 600;
    public static final int FRAME_HEIGHT = 340;
    public static final int BORDER = 5;
    public static final int BUTTON_HEIGHT = 30;
    public static final int BUTTON_DELETE_X = 10;
    public static final int BUTTON_DELETE_Y = 165;
    public static final int SCROLL_TAB_PANE_HEIGHT = 149;
    public static final int SCROLL_TAB_PANE_WIDTH = 549;

    /**
     * to generate port of client
     *
     * @return int
     */
    private static int generatePort() {
        Random random = new Random();
        return random.nextInt(Constant.MAX_PORT - Constant.MIN_PORT + 1) + Constant.MIN_PORT;
    }
}
