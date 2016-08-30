package mdl.sinlov.android.key_simulation_root;

/**
 * for event of Root key simulation
 * <pre>
 *     sinlov
 *
 *     /\__/\
 *    /`    '\
 *  ≈≈≈ 0  0 ≈≈≈ Hello world!
 *    \  --  /
 *   /        \
 *  /          \
 * |            |
 *  \  ||  ||  /
 *   \_oo__oo_/≡≡≡≡≡≡≡≡o
 *
 * </pre>
 * Created by "sinlov" on 16/8/29.
 */
public class RootKeyEvent {

    private static final String INPUT_KEY_EVENT = "input keyevent ";
    private static final String INPUT_TEXT = "input text ";
    private static final String INPUT_SWIPE = "input swipe ";
    private static final String INPUT_TAP = "input tap ";
    private static final String CMD_SPACE = " ";
    private static final String CMD_STR_START = "\'";
    private static final String CMD_STR_END = "\'";
    private static StringBuffer sb = new StringBuffer();

    public static void sendKeyEvent(EK ek) {
        sb.setLength(0);
        sb.append(INPUT_KEY_EVENT);
        sb.append(ek.ordinal());
        ShellManager.execShellRootCmd(sb.toString());
    }

    public static void sendTouch(int x, int y) {
        sb.setLength(0);
        sb.append(INPUT_TAP);
        sb.append(x);
        sb.append(CMD_SPACE);
        sb.append(y);
        ShellManager.execShellRootCmd(sb.toString());
    }

    public static void sendSwipe(int fromX, int fromY, int toX, int toY) {
        sb.setLength(0);
        sb.append(INPUT_SWIPE);
        sb.append(fromX);
        sb.append(CMD_SPACE);
        sb.append(fromY);
        sb.append(CMD_SPACE);
        sb.append(toX);
        sb.append(CMD_SPACE);
        sb.append(toY);
        sb.append(CMD_SPACE);
        ShellManager.execShellRootCmd(sb.toString());
    }

    public static void sendText(String text) {
        sb.setLength(0);
        sb.append(INPUT_TEXT);
        sb.append(CMD_STR_START);
        sb.append(text);
        sb.append(CMD_STR_END);
        ShellManager.execShellRootCmd(sb.toString());
    }
}
