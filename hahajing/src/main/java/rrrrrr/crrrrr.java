package rrrrrr;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus;
import com.immersion.hapticmediasdk.MediaTaskManager;
import com.immersion.hapticmediasdk.controllers.MediaController;

public class crrrrr extends Handler {
    public static int b04210421СС04210421 = 0;
    public static int b0421С0421С04210421 = 2;
    public static int bС0421СС04210421 = 1;
    public static int bСС0421С04210421 = 1;
    public final /* synthetic */ MediaController b0417З0417З0417З;

    public crrrrr(MediaController mediaController, Looper looper) {
        this.b0417З0417З0417З = mediaController;
        super(looper);
    }

    public static int bС04210421С04210421() {
        return 35;
    }

    public void handleMessage(Message message) {
        try {
            switch (message.what) {
                case 6:
                    if (MediaController.bБ04110411041104110411(this.b0417З0417З0417З).get() == message.arg1 && MediaController.b043Bллллл(this.b0417З0417З0417З).get() == message.arg2) {
                        try {
                            MediaTaskManager bл043Bлллл = MediaController.bл043Bлллл(this.b0417З0417З0417З);
                            int i = bС0421СС04210421;
                            switch ((i * (bСС0421С04210421 + i)) % b0421С0421С04210421) {
                                case 0:
                                    break;
                                default:
                                    bС0421СС04210421 = 55;
                                    b04210421СС04210421 = bС04210421С04210421();
                                    break;
                            }
                            if (bл043Bлллл.getSDKStatus() == SDKStatus.PAUSED_DUE_TO_BUFFERING) {
                                MediaController.bл043Bлллл(this.b0417З0417З0417З).transitToState(SDKStatus.PLAYING);
                                return;
                            }
                            MediaController.b043B043Bлллл(this.b0417З0417З0417З, MediaController.bБ04110411041104110411(this.b0417З0417З0417З).get(), SystemClock.uptimeMillis());
                            this.b0417З0417З0417З.playbackStarted();
                            return;
                        } catch (Exception e) {
                            throw e;
                        }
                    }
                    return;
                case 7:
                    this.b0417З0417З0417З.a(message.arg1);
                    return;
                case 8:
                    MediaController.b043Bл043Bллл(this.b0417З0417З0417З, message);
                    return;
                default:
                    return;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }
}
