package rrrrrr;

import com.immersion.hapticmediasdk.controllers.MediaController;

public class rcrrrr implements Runnable {
    public static int b04150415Е0415Е0415 = 1;
    public static int b0415ЕЕ0415Е0415 = 92;
    public static int bЕ0415Е0415Е0415 = 0;
    public static int bЕЕ04150415Е0415 = 2;
    public final /* synthetic */ MediaController b0417З0417041704170417;

    public rcrrrr(MediaController mediaController) {
        if (((b0415ЕЕ0415Е0415 + b04150415Е0415Е0415) * b0415ЕЕ0415Е0415) % bЕЕ04150415Е0415 != bЕ0415Е0415Е0415) {
            b0415ЕЕ0415Е0415 = 15;
            bЕ0415Е0415Е0415 = 7;
        }
        try {
            this.b0417З0417041704170417 = mediaController;
            try {
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public static int b0415Е04150415Е0415() {
        return 6;
    }

    public void run() {
        if (this.b0417З0417041704170417.isPlaying() && MediaController.bл043B043Bллл(this.b0417З0417041704170417) != null) {
            MediaController.bл043B043Bллл(this.b0417З0417041704170417).syncUpdate(this.b0417З0417041704170417.getCurrentPosition(), this.b0417З0417041704170417.getReferenceTimeForCurrentPosition());
            MediaController mediaController = this.b0417З0417041704170417;
            int i = b0415ЕЕ0415Е0415;
            switch ((i * (b04150415Е0415Е0415 + i)) % bЕЕ04150415Е0415) {
                case 0:
                    break;
                default:
                    b0415ЕЕ0415Е0415 = b0415Е04150415Е0415();
                    bЕ0415Е0415Е0415 = 99;
                    break;
            }
            MediaController.bл043B043Bллл(mediaController).getHandler().removeCallbacks(this);
            MediaController.bл043B043Bллл(this.b0417З0417041704170417).getHandler().postDelayed(this, 1000);
        }
    }
}
