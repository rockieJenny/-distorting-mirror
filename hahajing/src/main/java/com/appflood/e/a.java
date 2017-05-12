package com.appflood.e;

import android.content.Context;
import com.appflood.c.f;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

public final class a {
    private static byte[] a = new byte[0];

    public static InputStream a(URLConnection uRLConnection) throws Exception {
        InputStream inputStream = uRLConnection.getInputStream();
        String contentEncoding = uRLConnection.getContentEncoding();
        return (contentEncoding == null || !contentEncoding.toLowerCase().contains(HttpRequest.ENCODING_GZIP)) ? (contentEncoding == null || !contentEncoding.toLowerCase().contains("deflate")) ? inputStream : new InflaterInputStream(inputStream) : new GZIPInputStream(inputStream);
    }

    public static String a(Context context, String str) {
        return c(context, str);
    }

    public static String a(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance(CommonUtils.SHA1_INSTANCE);
            instance.update(str.getBytes(HttpRequest.CHARSET_UTF8), 0, str.length());
            byte[] digest = instance.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                stringBuffer.append(Integer.toString((b & 255) + 256, 16).substring(1));
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    private static String a(Map<String, String> map) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Entry entry : map.entrySet()) {
            stringBuilder.append((String) entry.getKey());
            stringBuilder.append("=");
            stringBuilder.append((String) entry.getValue());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    private static String a(byte[] bArr) {
        try {
            MessageDigest instance = MessageDigest.getInstance(CommonUtils.MD5_INSTANCE);
            instance.reset();
            instance.update(bArr);
            byte[] digest = instance.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : digest) {
                String toHexString = Integer.toHexString(b & 255);
                if (toHexString.length() > 1) {
                    stringBuilder.append(toHexString);
                } else {
                    stringBuilder.append('0').append(toHexString);
                }
            }
            return stringBuilder.toString();
        } catch (Throwable e) {
            j.b(e, "Failed to compute md5");
            return null;
        }
    }

    private static Map<String, String> a(InputStream inputStream, Map<String, String> map) {
        Object e;
        Throwable th;
        Map<String, String> hashMap = map == null ? new HashMap() : map;
        if (inputStream != null) {
            Closeable bufferedReader;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, HttpRequest.CHARSET_UTF8));
                while (true) {
                    try {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        readLine = readLine.trim();
                        if (!readLine.startsWith("#")) {
                            int indexOf = readLine.indexOf(61);
                            if (indexOf != -1) {
                                hashMap.put(readLine.substring(0, indexOf), readLine.substring(indexOf + 1));
                            }
                        }
                    } catch (Exception e2) {
                        e = e2;
                    }
                }
                a(bufferedReader);
            } catch (Exception e3) {
                e = e3;
                bufferedReader = null;
                try {
                    j.c("Faied to load string resources: " + e);
                    a(bufferedReader);
                    return hashMap;
                } catch (Throwable th2) {
                    th = th2;
                    a(bufferedReader);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                bufferedReader = null;
                a(bufferedReader);
                throw th;
            }
        }
        return hashMap;
    }

    public static void a(Context context, String str, String str2) {
        b(context, str, str2);
    }

    public static boolean a(Closeable closeable) {
        if (closeable == null) {
            return true;
        }
        try {
            closeable.close();
            return true;
        } catch (Throwable th) {
            j.a(th, "Failed to close IO" + closeable.toString());
            return false;
        }
    }

    public static boolean a(File file, byte[] bArr) {
        Closeable fileOutputStream;
        Throwable th;
        Throwable th2;
        if (file == null || bArr == null) {
            return false;
        }
        try {
            fileOutputStream = new FileOutputStream(file);
            try {
                fileOutputStream.write(bArr);
                a(fileOutputStream);
                return true;
            } catch (Throwable th3) {
                th = th3;
                try {
                    j.a(th, "failed to write bytes " + file.getPath());
                    a(fileOutputStream);
                    return false;
                } catch (Throwable th4) {
                    th2 = th4;
                    a(fileOutputStream);
                    throw th2;
                }
            }
        } catch (Throwable th5) {
            th2 = th5;
            fileOutputStream = null;
            a(fileOutputStream);
            throw th2;
        }
    }

    public static byte[] a(File file) {
        byte[] bArr = null;
        if (file != null && file.exists()) {
            try {
                bArr = a(new FileInputStream(file));
            } catch (Throwable th) {
                j.a(th, "failed to load dataFromFile " + file.getPath());
            }
        }
        return bArr;
    }

    private static byte[] a(InputStream inputStream) {
        if (inputStream == null) {
            return a;
        }
        byte[] th;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            try {
                int read = inputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, read);
            } catch (Throwable th2) {
                th = th2;
                j.a((Throwable) th, "failed to load dataFromStream");
                return a;
            } finally {
                a((Closeable) inputStream);
            }
        }
        th = byteArrayOutputStream.toByteArray();
        return th;
    }

    public static int b(Context context, String str) {
        return d(context, str);
    }

    private static int b(Context context, String str, String str2) {
        Throwable e;
        Closeable fileInputStream;
        Throwable e2;
        FileLock fileLock = null;
        File e3 = f.e();
        if (e3 == null && context != null) {
            e3 = context.getFileStreamPath("com_appflood_provider_file");
        }
        if (e3 == null) {
            j.c(" file is null key = " + str);
            return 0;
        }
        if (!e3.exists()) {
            "the provider file is NOT exist!! path = " + e3.getAbsolutePath();
            j.a();
            try {
                j.a();
                if (e3 != null) {
                    File parentFile = e3.getParentFile();
                    if (!(parentFile == null || parentFile.exists())) {
                        parentFile.mkdirs();
                    }
                }
                e3.createNewFile();
            } catch (Throwable e4) {
                j.a(e4, "error in create new file");
                return 0;
            }
        }
        Map hashMap = new HashMap();
        try {
            fileInputStream = new FileInputStream(e3);
            try {
                a((InputStream) fileInputStream, hashMap);
                a(fileInputStream);
                hashMap.put(str, str2);
                try {
                    fileInputStream = new FileOutputStream(e3);
                    try {
                        FileChannel channel = fileInputStream.getChannel();
                        fileLock = channel.tryLock();
                        if (fileLock == null) {
                            channel.close();
                            a(fileInputStream);
                            if (fileLock != null) {
                                try {
                                    fileLock.release();
                                } catch (Throwable e42) {
                                    j.a(e42, "io erro in filelock release");
                                }
                            }
                            a(fileInputStream);
                            return 0;
                        }
                        fileInputStream.write(a(hashMap).getBytes(HttpRequest.CHARSET_UTF8));
                        if (fileLock != null) {
                            try {
                                fileLock.release();
                            } catch (Throwable e22) {
                                j.a(e22, "io erro in filelock release");
                            }
                        }
                        a(fileInputStream);
                        return 1;
                    } catch (IOException e5) {
                        e42 = e5;
                        try {
                            j.a(e42, "error in outputstream");
                            if (fileLock != null) {
                                try {
                                    fileLock.release();
                                } catch (Throwable e422) {
                                    j.a(e422, "io erro in filelock release");
                                }
                            }
                            a(fileInputStream);
                            return 0;
                        } catch (Throwable th) {
                            e22 = th;
                            if (fileLock != null) {
                                try {
                                    fileLock.release();
                                } catch (Throwable e4222) {
                                    j.a(e4222, "io erro in filelock release");
                                }
                            }
                            a(fileInputStream);
                            throw e22;
                        }
                    }
                } catch (IOException e6) {
                    e4222 = e6;
                    fileInputStream = null;
                    j.a(e4222, "error in outputstream");
                    if (fileLock != null) {
                        fileLock.release();
                    }
                    a(fileInputStream);
                    return 0;
                } catch (Throwable th2) {
                    e22 = th2;
                    fileInputStream = null;
                    if (fileLock != null) {
                        fileLock.release();
                    }
                    a(fileInputStream);
                    throw e22;
                }
            } catch (FileNotFoundException e7) {
                e4222 = e7;
                try {
                    j.a(e4222, "error in read file");
                    a(fileInputStream);
                    return 0;
                } catch (Throwable th3) {
                    e22 = th3;
                    a(fileInputStream);
                    throw e22;
                }
            }
        } catch (FileNotFoundException e8) {
            e4222 = e8;
            fileInputStream = null;
            j.a(e4222, "error in read file");
            a(fileInputStream);
            return 0;
        } catch (Throwable th4) {
            e22 = th4;
            fileInputStream = null;
            a(fileInputStream);
            throw e22;
        }
    }

    public static String b(String str) {
        return a(j.b(str));
    }

    private static String c(Context context, String str) {
        String str2;
        Throwable e;
        Throwable th;
        File e2 = f.e();
        if (e2 == null && context != null) {
            e2 = context.getFileStreamPath("com_appflood_provider_file");
        }
        if (e2 == null) {
            j.c(" file is null key = " + str);
            return "";
        } else if (!e2.exists()) {
            return "";
        } else {
            String str3 = "";
            try {
                InputStream fileInputStream = new FileInputStream(e2);
                Map hashMap = new HashMap();
                a(fileInputStream, hashMap);
                if (!hashMap.containsKey(str)) {
                    return "";
                }
                str2 = (String) hashMap.get(str);
                try {
                    fileInputStream.close();
                    return str2;
                } catch (FileNotFoundException e3) {
                    e = e3;
                    j.a(e, "error in read file");
                    return str2;
                } catch (IOException e4) {
                    e = e4;
                    j.a(e, "error in inputstrame");
                    return str2;
                }
            } catch (Throwable e5) {
                th = e5;
                str2 = str3;
                e = th;
                j.a(e, "error in read file");
                return str2;
            } catch (Throwable e52) {
                th = e52;
                str2 = str3;
                e = th;
                j.a(e, "error in inputstrame");
                return str2;
            }
        }
    }

    private static int d(Context context, String str) {
        Throwable e;
        Closeable fileInputStream;
        Throwable e2;
        FileLock fileLock = null;
        File e3 = f.e();
        if (e3 == null && context != null) {
            e3 = context.getFileStreamPath("com_appflood_provider_file");
        }
        if (e3 == null) {
            j.c(" file is null key = " + str);
            return 0;
        }
        if (!e3.exists()) {
            try {
                j.a();
                e3.createNewFile();
            } catch (Throwable e4) {
                j.a(e4, "error in create new file");
                return 0;
            }
        }
        Map hashMap = new HashMap();
        try {
            fileInputStream = new FileInputStream(e3);
            try {
                a((InputStream) fileInputStream, hashMap);
                a(fileInputStream);
                if (!hashMap.containsKey(str)) {
                    return -1;
                }
                hashMap.remove(str);
                try {
                    fileInputStream = new FileOutputStream(e3);
                    try {
                        FileChannel channel = fileInputStream.getChannel();
                        fileLock = channel.tryLock();
                        if (fileLock == null) {
                            channel.close();
                            a(fileInputStream);
                            if (fileLock != null) {
                                try {
                                    fileLock.release();
                                } catch (Throwable e42) {
                                    j.a(e42, "io erro in filelock release");
                                }
                            }
                            a(fileInputStream);
                            return 0;
                        }
                        fileInputStream.write(a(hashMap).getBytes(HttpRequest.CHARSET_UTF8));
                        if (fileLock != null) {
                            try {
                                fileLock.release();
                            } catch (Throwable e22) {
                                j.a(e22, "io erro in filelock release");
                            }
                        }
                        a(fileInputStream);
                        return 1;
                    } catch (IOException e5) {
                        e42 = e5;
                        try {
                            j.a(e42, "error in outputstream");
                            if (fileLock != null) {
                                try {
                                    fileLock.release();
                                } catch (Throwable e422) {
                                    j.a(e422, "io erro in filelock release");
                                }
                            }
                            a(fileInputStream);
                            return 0;
                        } catch (Throwable th) {
                            e22 = th;
                            if (fileLock != null) {
                                try {
                                    fileLock.release();
                                } catch (Throwable e4222) {
                                    j.a(e4222, "io erro in filelock release");
                                }
                            }
                            a(fileInputStream);
                            throw e22;
                        }
                    }
                } catch (IOException e6) {
                    e4222 = e6;
                    fileInputStream = null;
                    j.a(e4222, "error in outputstream");
                    if (fileLock != null) {
                        fileLock.release();
                    }
                    a(fileInputStream);
                    return 0;
                } catch (Throwable th2) {
                    e22 = th2;
                    fileInputStream = null;
                    if (fileLock != null) {
                        fileLock.release();
                    }
                    a(fileInputStream);
                    throw e22;
                }
            } catch (FileNotFoundException e7) {
                e4222 = e7;
                try {
                    j.a(e4222, "error in read file");
                    a(fileInputStream);
                    return 0;
                } catch (Throwable th3) {
                    e22 = th3;
                    a(fileInputStream);
                    throw e22;
                }
            }
        } catch (FileNotFoundException e8) {
            e4222 = e8;
            fileInputStream = null;
            j.a(e4222, "error in read file");
            a(fileInputStream);
            return 0;
        } catch (Throwable th4) {
            e22 = th4;
            fileInputStream = null;
            a(fileInputStream);
            throw e22;
        }
    }
}
