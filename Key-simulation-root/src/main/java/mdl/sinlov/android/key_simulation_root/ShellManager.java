package mdl.sinlov.android.key_simulation_root;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.OutputStream;

/**
 * for android shell management
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
public class ShellManager {

    public static final String TAG = "ShellManager";
    /**
     * default uid = -1
     */
    public static final int UID_DEFAULT = -1;
    /**
     * root uid = 0
     */
    public static final int UID_ROOT = 0;

    private static final String CAN_GET_ROOT_BY_SHELL_SUCCESS = "You can get root by shell!";
    private static final String CAN_GET_ROOT_BY_SHELL_REFUSE = "You can not get root by shell! ";

    public static void execShellRootCmd(String cmd) {
        try {
            Process process = Runtime.getRuntime().exec("su");
            OutputStream outputStream = process.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(
                    outputStream);
            dataOutputStream.writeBytes(cmd);
            dataOutputStream.flush();
            dataOutputStream.close();
            outputStream.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * is can root by shell
     *
     * @param context application Context
     * @return boolean
     */
    public static boolean isCanGetRootByShell(Context context) {
        String apkRoot = "chmod 777 " + context.getPackageCodePath();
        return rootCommand(apkRoot);
    }

    /**
     * put shell command by root
     *
     * @param command command string
     * @return success or refuse
     */
    public static boolean rootCommand(String command) {
        Process process = null;
        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(command + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            Log.d(TAG, CAN_GET_ROOT_BY_SHELL_REFUSE + e.getMessage());
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                assert process != null;
                process.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.v(TAG, CAN_GET_ROOT_BY_SHELL_SUCCESS);
        return true;
    }

    /**
     * Get APP UID
     * <p> You can also get packageName by APP ID
     *
     * @param context     application context
     * @param packageName this package package name
     * @return uid or {@link ShellManager#UID_DEFAULT} or {@link ShellManager#UID_ROOT}
     */
    public static int getUID(Context context, String packageName) {
        int UID = UID_DEFAULT;
        try {
            PackageManager pm = context.getPackageManager();
            //noinspection WrongConstant
            ApplicationInfo ai = pm.getApplicationInfo(packageName, PackageManager.GET_ACTIVITIES);
            UID = ai.uid;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "GetUID: " + e.toString());
        }
        return UID;
    }

    private ShellManager() {
    }
}
