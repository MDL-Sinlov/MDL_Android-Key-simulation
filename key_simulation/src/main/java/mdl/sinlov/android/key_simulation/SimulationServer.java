package mdl.sinlov.android.key_simulation;

import android.app.Instrumentation;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
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
public class SimulationServer extends Service {

    public static final int MSG_CHARACTER_SYNC = 1;

    private Instrumentation instrumentation;
    private SafeHandler handler = new SafeHandler(this);

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        instrumentation = new Instrumentation();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private static class SafeHandler extends Handler {
        private static WeakReference<SimulationServer> wkSimulation;

        public SafeHandler(SimulationServer webSocketClient) {
            SafeHandler.wkSimulation = new WeakReference<SimulationServer>(webSocketClient);
        }

        public WeakReference<SimulationServer> get() {
            return wkSimulation;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SimulationServer simulation = wkSimulation.get();
            if (null != simulation) {
                switch (msg.what) {
                    case MSG_CHARACTER_SYNC:
                            simulation.instrumentation.sendCharacterSync(msg.arg1);
                        break;
                }
            }
        }
    }

    public void sendKeySync(int keyCode){
        handler.sendMessage(handler.obtainMessage(MSG_CHARACTER_SYNC, keyCode, keyCode));
    }
}
