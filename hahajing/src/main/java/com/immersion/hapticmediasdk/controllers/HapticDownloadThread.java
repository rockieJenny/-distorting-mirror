package com.immersion.hapticmediasdk.controllers;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import com.google.android.gms.wallet.WalletConstants;
import com.immersion.hapticmediasdk.models.HttpUnsuccessfulException;
import com.immersion.hapticmediasdk.utils.FileManager;
import com.immersion.hapticmediasdk.utils.Log;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpResponse;

public class HapticDownloadThread extends Thread {
    private static final String a = "HapticDownloadThread";
    private static final int b = 4096;
    public static int b04150415Е041504150415 = 1;
    public static int b0415ЕЕ041504150415 = 39;
    public static int bЕ0415Е041504150415 = 0;
    public static int bЕЕ0415041504150415 = 2;
    private String c;
    private Handler d;
    private boolean e;
    private Thread f;
    private FileManager g;
    private volatile boolean h = false;
    private volatile boolean i = false;
    private volatile boolean j = false;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public HapticDownloadThread(java.lang.String r4, android.os.Handler r5, boolean r6, com.immersion.hapticmediasdk.utils.FileManager r7) {
        /*
        r3 = this;
        r2 = 1;
        r1 = 0;
        r0 = "HapticDownloadThread";
        r3.<init>(r0);
        r3.h = r1;
        r3.i = r1;
        r3.j = r1;
        r3.c = r4;
    L_0x000f:
        switch(r2) {
            case 0: goto L_0x000f;
            case 1: goto L_0x0016;
            default: goto L_0x0012;
        };
    L_0x0012:
        switch(r2) {
            case 0: goto L_0x000f;
            case 1: goto L_0x0016;
            default: goto L_0x0015;
        };
    L_0x0015:
        goto L_0x0012;
    L_0x0016:
        r3.d = r5;
        r3.e = r6;
        r3.g = r7;
        r0 = r3.d;
        r0 = r0.getLooper();
        r1 = b0415ЕЕ041504150415;
        r2 = b04150415Е041504150415;
        r1 = r1 + r2;
        r2 = b0415ЕЕ041504150415;
        r1 = r1 * r2;
        r2 = bЕЕ0415041504150415;
        r1 = r1 % r2;
        r2 = bЕ0415Е041504150415;
        if (r1 == r2) goto L_0x003b;
    L_0x0031:
        r1 = 70;
        b0415ЕЕ041504150415 = r1;
        r1 = b0415Е0415041504150415();
        bЕ0415Е041504150415 = r1;
    L_0x003b:
        r0 = r0.getThread();
        r3.f = r0;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticDownloadThread.<init>(java.lang.String, android.os.Handler, boolean, com.immersion.hapticmediasdk.utils.FileManager):void");
    }

    public static int b0415Е0415041504150415() {
        return 19;
    }

    public static int b0427ЧЧЧЧЧ() {
        return 1;
    }

    public static int bЕ04150415041504150415() {
        return 0;
    }

    public synchronized boolean isFirstPacketReady() {
        boolean z;
        try {
            z = this.h || this.i;
        } catch (Exception e) {
            throw e;
        }
        return z;
    }

    public void run() {
        if (this.e) {
            Process.setThreadPriority(10);
            try {
                HttpResponse executeGet = ImmersionHttpClient.getHttpClient().executeGet(this.c, null, 60000);
                int statusCode = executeGet.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    writeToFile(executeGet.getEntity().getContent(), Integer.parseInt(executeGet.getFirstHeader(HttpRequest.HEADER_CONTENT_LENGTH).getValue()));
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder("HTTP STATUS CODE: ");
                stringBuilder.append(statusCode);
                switch (statusCode) {
                    case 400:
                        stringBuilder.append(" Bad Request");
                        break;
                    case 403:
                        stringBuilder.append(" Forbidden");
                        break;
                    case WalletConstants.ERROR_CODE_INVALID_PARAMETERS /*404*/:
                        stringBuilder.append(" Not Found");
                        break;
                    case 500:
                        stringBuilder.append(" Internal Server Error");
                        break;
                    case 502:
                        stringBuilder.append(" Bad Gateway");
                        break;
                    case 503:
                        stringBuilder.append(" Service Unavailable");
                        break;
                    default:
                        break;
                }
                throw new HttpUnsuccessfulException(statusCode, stringBuilder.toString());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            } catch (Object e2) {
                Message obtainMessage = this.d.obtainMessage(8);
                Bundle bundle = new Bundle();
                bundle.putSerializable(HapticPlaybackThread.HAPTIC_DOWNLOAD_EXCEPTION_KEY, e2);
                obtainMessage.setData(bundle);
                if (this.f.isAlive() && !this.j) {
                    Handler handler = this.d;
                    if (((b0415ЕЕ041504150415 + b04150415Е041504150415) * b0415ЕЕ041504150415) % bЕЕ0415041504150415 != bЕ0415Е041504150415) {
                        b0415ЕЕ041504150415 = b0415Е0415041504150415();
                        bЕ0415Е041504150415 = b0415Е0415041504150415();
                    }
                    handler.sendMessage(obtainMessage);
                }
                Log.e(a, e2.getMessage());
                return;
            }
        }
        InputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(this.c);
        } catch (FileNotFoundException e3) {
            e3.printStackTrace();
            fileInputStream = null;
        }
        if (fileInputStream != null) {
            try {
                writeToFile(fileInputStream, fileInputStream.available());
            } catch (IOException e4) {
                e4.printStackTrace();
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void terminate() {
        /*
        r3 = this;
        r2 = 1;
        r0 = b0415ЕЕ041504150415;
        r1 = b04150415Е041504150415;
        r0 = r0 + r1;
        r1 = b0415ЕЕ041504150415;
        r0 = r0 * r1;
        r1 = bЕЕ0415041504150415;
        r0 = r0 % r1;
        r1 = bЕ04150415041504150415();
        if (r0 == r1) goto L_0x001c;
    L_0x0012:
        r0 = 53;
        b0415ЕЕ041504150415 = r0;
        r0 = b0415Е0415041504150415();
        bЕ0415Е041504150415 = r0;
    L_0x001c:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x0024;
            case 1: goto L_0x001c;
            default: goto L_0x0020;
        };
    L_0x0020:
        switch(r2) {
            case 0: goto L_0x001c;
            case 1: goto L_0x0024;
            default: goto L_0x0023;
        };
    L_0x0023:
        goto L_0x0020;
    L_0x0024:
        r3.j = r2;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticDownloadThread.terminate():void");
    }

    public boolean writeToFile(InputStream inputStream, int i) throws IOException {
        Bundle bundle;
        Closeable closeable;
        Throwable th;
        Closeable closeable2;
        String str;
        Message obtainMessage;
        int i2 = 0;
        try {
            byte[] bArr = new byte[4096];
            if (inputStream == null || i <= 0) {
                if (!this.h) {
                    String str2 = "downloaded an empty file";
                    Message obtainMessage2 = this.d.obtainMessage(8);
                    Bundle bundle2 = new Bundle();
                    bundle2.putSerializable(HapticPlaybackThread.HAPTIC_DOWNLOAD_EXCEPTION_KEY, new FileNotFoundException(str2));
                    obtainMessage2.setData(bundle2);
                    if (this.f.isAlive() && !this.j) {
                        this.d.sendMessage(obtainMessage2);
                    }
                    Log.e(a, str2);
                }
                this.g.closeCloseable(null);
                this.g.closeCloseable(null);
                this.i = true;
                return false;
            }
            try {
                Closeable bufferedInputStream = new BufferedInputStream(inputStream);
                try {
                    BufferedOutputStream makeOutputStreamForStreaming = this.e ? this.g.makeOutputStreamForStreaming(this.c) : this.g.makeOutputStream(this.c);
                    Message obtainMessage3;
                    if (makeOutputStreamForStreaming == null) {
                        if (!this.h) {
                            String str3 = "downloaded an empty file";
                            obtainMessage3 = this.d.obtainMessage(8);
                            bundle = new Bundle();
                            bundle.putSerializable(HapticPlaybackThread.HAPTIC_DOWNLOAD_EXCEPTION_KEY, new FileNotFoundException(str3));
                            try {
                                obtainMessage3.setData(bundle);
                                if (this.f.isAlive() && !this.j) {
                                    this.d.sendMessage(obtainMessage3);
                                }
                                Log.e(a, str3);
                            } catch (Exception e) {
                                throw e;
                            }
                        }
                        this.g.closeCloseable(bufferedInputStream);
                        this.g.closeCloseable(makeOutputStreamForStreaming);
                        this.i = true;
                        return false;
                    }
                    try {
                        String str4;
                        if (this.e) {
                            while (!isInterrupted() && !this.j) {
                                int read = bufferedInputStream.read(bArr, 0, 4096);
                                if (read < 0) {
                                    break;
                                }
                                makeOutputStreamForStreaming.write(bArr, 0, read);
                                i2 += read;
                                if (this.f.isAlive()) {
                                    if (!this.h) {
                                        this.h = true;
                                    }
                                    makeOutputStreamForStreaming.flush();
                                    this.d.sendMessage(this.d.obtainMessage(3, i2, 0));
                                }
                            }
                        } else {
                            this.h = true;
                            if (this.j) {
                                if (!this.h) {
                                    str4 = "downloaded an empty file";
                                    obtainMessage3 = this.d.obtainMessage(8);
                                    bundle = new Bundle();
                                    bundle.putSerializable(HapticPlaybackThread.HAPTIC_DOWNLOAD_EXCEPTION_KEY, new FileNotFoundException(str4));
                                    obtainMessage3.setData(bundle);
                                    if (this.f.isAlive() && !this.j) {
                                        this.d.sendMessage(obtainMessage3);
                                    }
                                    Log.e(a, str4);
                                }
                                this.g.closeCloseable(bufferedInputStream);
                                this.g.closeCloseable(makeOutputStreamForStreaming);
                                this.i = true;
                                return true;
                            }
                            this.d.sendMessage(this.d.obtainMessage(3, i, 0));
                        }
                        Log.i(a, "file download completed");
                        if (!this.h) {
                            str4 = "downloaded an empty file";
                            obtainMessage3 = this.d.obtainMessage(8);
                            bundle = new Bundle();
                            if (((b0415ЕЕ041504150415 + b04150415Е041504150415) * b0415ЕЕ041504150415) % bЕЕ0415041504150415 != bЕ0415Е041504150415) {
                                b0415ЕЕ041504150415 = 2;
                                bЕ0415Е041504150415 = 54;
                            }
                            bundle.putSerializable(HapticPlaybackThread.HAPTIC_DOWNLOAD_EXCEPTION_KEY, new FileNotFoundException(str4));
                            obtainMessage3.setData(bundle);
                            if (this.f.isAlive() && !this.j) {
                                this.d.sendMessage(obtainMessage3);
                            }
                            Log.e(a, str4);
                        }
                        this.g.closeCloseable(bufferedInputStream);
                        if (((b0415ЕЕ041504150415 + b0427ЧЧЧЧЧ()) * b0415ЕЕ041504150415) % bЕЕ0415041504150415 != bЕ0415Е041504150415) {
                            b0415ЕЕ041504150415 = 47;
                            bЕ0415Е041504150415 = 86;
                        }
                        this.g.closeCloseable(makeOutputStreamForStreaming);
                        this.i = true;
                        return true;
                    } catch (Throwable th2) {
                        closeable = bufferedInputStream;
                        BufferedOutputStream bufferedOutputStream = makeOutputStreamForStreaming;
                        th = th2;
                        Object obj = bufferedOutputStream;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    closeable2 = null;
                    closeable = bufferedInputStream;
                    if (!this.h) {
                        str = "downloaded an empty file";
                        obtainMessage = this.d.obtainMessage(8);
                        bundle = new Bundle();
                        bundle.putSerializable(HapticPlaybackThread.HAPTIC_DOWNLOAD_EXCEPTION_KEY, new FileNotFoundException(str));
                        obtainMessage.setData(bundle);
                        if (this.f.isAlive() && !this.j) {
                            this.d.sendMessage(obtainMessage);
                        }
                        Log.e(a, str);
                    }
                    this.g.closeCloseable(closeable);
                    this.g.closeCloseable(closeable2);
                    this.i = true;
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                closeable2 = null;
                closeable = null;
                if (this.h) {
                    str = "downloaded an empty file";
                    obtainMessage = this.d.obtainMessage(8);
                    bundle = new Bundle();
                    bundle.putSerializable(HapticPlaybackThread.HAPTIC_DOWNLOAD_EXCEPTION_KEY, new FileNotFoundException(str));
                    obtainMessage.setData(bundle);
                    this.d.sendMessage(obtainMessage);
                    Log.e(a, str);
                }
                this.g.closeCloseable(closeable);
                this.g.closeCloseable(closeable2);
                this.i = true;
                throw th;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }
}
