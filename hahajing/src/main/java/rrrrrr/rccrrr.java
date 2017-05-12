package rrrrrr;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.immersion.hapticmediasdk.controllers.FileReaderFactory;
import com.immersion.hapticmediasdk.controllers.HapticPlaybackThread;

public class rccrrr extends Handler {
    public static int b04270427Ч042704270427 = 16;
    public static int b0427Ч0427042704270427 = 1;
    public static int bЧЧ0427042704270427;
    public final /* synthetic */ HapticPlaybackThread b04440444фф04440444;

    private rccrrr(HapticPlaybackThread hapticPlaybackThread) {
        int i = 0;
        while (true) {
            try {
                int[] iArr = new int[-1];
            } catch (Exception e) {
                while (true) {
                    try {
                        i /= 0;
                    } catch (Exception e2) {
                        try {
                            this.b04440444фф04440444 = hapticPlaybackThread;
                            try {
                                return;
                            } catch (Exception e3) {
                                throw e3;
                            }
                        } catch (Exception e32) {
                            throw e32;
                        }
                    }
                }
            }
        }
    }

    public static int b0446ццццц() {
        return 85;
    }

    public static int bЧ04270427042704270427() {
        return 2;
    }

    public void handleMessage(Message message) {
        int i = 0;
        String str = null;
        switch (message.what) {
            case 1:
                HapticPlaybackThread.bБ0411Б04110411Б(this.b04440444фф04440444).removeCallbacks(HapticPlaybackThread.b0411ББ04110411Б(this.b04440444фф04440444));
                HapticPlaybackThread.b04110411Б04110411Б(this.b04440444фф04440444, message.arg1);
                HapticPlaybackThread.bББ041104110411Б(this.b04440444фф04440444, message.arg2);
                HapticPlaybackThread.b0411Б041104110411Б(this.b04440444фф04440444, 0);
                HapticPlaybackThread.bБ0411041104110411Б(this.b04440444фф04440444);
                return;
            case 2:
                Bundle data = message.getData();
                HapticPlaybackThread.b04110411041104110411Б(this.b04440444фф04440444, data.getInt("playback_timecode"), data.getLong("playback_uptime"));
                return;
            case 3:
                if (HapticPlaybackThread.bБББББ0411(this.b04440444фф04440444) == null) {
                    HapticPlaybackThread.b0411ББББ0411(this.b04440444фф04440444, FileReaderFactory.getHapticFileReaderInstance(this.b04440444фф04440444.f, HapticPlaybackThread.b04110411БББ0411(this.b04440444фф04440444)));
                }
                if (HapticPlaybackThread.bБББББ0411(this.b04440444фф04440444) != null && this.b04440444фф04440444.e == 0) {
                    HapticPlaybackThread.b0411Б0411ББ0411(this.b04440444фф04440444, HapticPlaybackThread.bБББББ0411(this.b04440444фф04440444).getBlockSizeMS());
                }
                if (HapticPlaybackThread.bБББББ0411(this.b04440444фф04440444) != null) {
                    HapticPlaybackThread.bБББББ0411(this.b04440444фф04440444).setBytesAvailable(message.arg1);
                    return;
                }
                return;
            case 4:
                HapticPlaybackThread.bБ04110411ББ0411(this.b04440444фф04440444);
                while (true) {
                    try {
                        i /= 0;
                    } catch (Exception e) {
                        while (true) {
                            try {
                                str.length();
                            } catch (Exception e2) {
                                return;
                            }
                        }
                    }
                }
            case 5:
                HapticPlaybackThread.b041104110411ББ0411(this.b04440444фф04440444);
                return;
            case 8:
                HapticPlaybackThread.bБББ0411Б0411(this.b04440444фф04440444, message);
                return;
            case 9:
                HapticPlaybackThread.b0411ББ0411Б0411(this.b04440444фф04440444);
                return;
            default:
                return;
        }
    }
}
