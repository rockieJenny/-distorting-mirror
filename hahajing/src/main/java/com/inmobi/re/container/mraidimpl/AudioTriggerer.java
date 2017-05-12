package com.inmobi.re.container.mraidimpl;

import android.media.AudioRecord;
import com.inmobi.commons.internal.Log;
import com.inmobi.re.controller.util.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AudioTriggerer {
    static boolean a;
    public static AudioRecord audioRecorder = null;
    static boolean b;
    static Timer c;
    public static List<AudioTriggerCallback> callbacks = new ArrayList();
    static long d = 0;
    static long e = 50;
    private static int[] f = new int[]{8000, 11025, 22050, 44100};

    public static void addEventListener(AudioTriggerCallback audioTriggerCallback) {
        callbacks.add(audioTriggerCallback);
        if (callbacks.size() == 1) {
            b();
        }
    }

    public static void removeEventListener(AudioTriggerCallback audioTriggerCallback) {
        callbacks.remove(audioTriggerCallback);
        if (callbacks.size() == 0) {
            c();
        }
    }

    private static void a(double d) {
        for (AudioTriggerCallback audioLevel : callbacks) {
            try {
                audioLevel.audioLevel(d);
            } catch (Exception e) {
                Log.internal(Constants.RENDERING_LOG_TAG, "AudioTriggerer: One of the mic listeners has a problem.");
            }
        }
    }

    private static void b() {
        a = true;
        audioRecorder = h();
        c = new Timer();
        c.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                AudioTriggerer.d();
            }
        }, d, e);
    }

    private static void c() {
        a = false;
        if (audioRecorder != null) {
            if (f()) {
                g();
            }
            try {
                audioRecorder.stop();
                audioRecorder.release();
                c.cancel();
                c.purge();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (RuntimeException e2) {
                e2.printStackTrace();
            }
        }
    }

    private static void d() {
        if (audioRecorder != null && audioRecorder.getState() == 1) {
            short[] sArr = new short[512];
            float[] fArr = new float[3];
            b = true;
            try {
                audioRecorder.startRecording();
                while (f()) {
                    int read = audioRecorder.read(sArr, 0, sArr.length);
                    float f = 0.0f;
                    for (int i = 0; i < read; i += 2) {
                        short s = (short) (sArr[i] | sArr[i + 1]);
                        if (s != (short) 0) {
                            f += (float) (Math.abs(s) / read);
                        }
                    }
                    fArr[0] = f;
                    float f2 = 0.0f;
                    for (int i2 = 0; i2 < 3; i2++) {
                        f2 += fArr[i2];
                    }
                    a((double) ((f2 / ((float) read)) / 32.0f));
                }
                e();
            } catch (Exception e) {
            }
        }
    }

    private static void e() {
        if (audioRecorder != null) {
            if (f()) {
                g();
            }
            try {
                audioRecorder.stop();
                audioRecorder.release();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (RuntimeException e2) {
                e2.printStackTrace();
            }
        }
    }

    private static boolean f() {
        return b;
    }

    private static void g() {
        b = false;
    }

    private static AudioRecord h() {
        for (int i : f) {
            for (short s : new short[]{(short) 3, (short) 2}) {
                for (short s2 : new short[]{(short) 16, (short) 12}) {
                    try {
                        int minBufferSize = AudioRecord.getMinBufferSize(i, s2, s);
                        if (minBufferSize != -2) {
                            AudioRecord audioRecord = new AudioRecord(0, i, s2, s, minBufferSize);
                            if (audioRecord.getState() == 1) {
                                return audioRecord;
                            }
                        } else {
                            continue;
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        return null;
    }
}
