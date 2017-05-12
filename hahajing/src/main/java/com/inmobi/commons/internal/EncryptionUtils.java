package com.inmobi.commons.internal;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.inmobi.commons.thirdparty.Base64;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtils {
    private static byte[] a = new byte[16];
    private static String b = "SHA1PRNG";
    private static String c = "Crypto";
    private static String d = "HmacSHA1";
    private static String e = "RSA";
    private static String f = "RSA/ECB/nopadding";
    private static String g = "aeskeygenerate";
    private static String h = "last_key_generate";
    private static String i = "AES/CBC/PKCS7Padding";
    private static String j = "AES";

    private static class a extends IvParameterSpec {
        public a(byte[] bArr) {
            super(bArr);
        }
    }

    private static class b extends RSAPublicKeySpec {
        public b(BigInteger bigInteger, BigInteger bigInteger2) {
            super(bigInteger, bigInteger2);
        }
    }

    private static class c extends SecretKeySpec {
        public c(byte[] bArr, String str) {
            super(bArr, str);
        }

        public c(byte[] bArr, int i, int i2, String str) {
            super(bArr, i, i2, str);
        }
    }

    private static String a(String str, byte[] bArr, byte[] bArr2, byte[] bArr3, String str2, String str3) {
        try {
            byte[] a = a(str.getBytes(HttpRequest.CHARSET_UTF8), bArr, bArr2);
            byte[] a2 = a(a, bArr3);
            a = a(a);
            a2 = a(a2);
            return new String(Base64.encode(b(a(a(b(b(a(bArr), a(bArr3)), a(bArr2)), str3, str2)), b(a, a2)), 8));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] a(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        byte[] bArr4 = null;
        Key cVar = new c(bArr2, j);
        AlgorithmParameterSpec aVar = new a(bArr3);
        try {
            Cipher instance = Cipher.getInstance(i);
            instance.init(1, cVar, aVar);
            bArr4 = instance.doFinal(bArr);
        } catch (NoSuchAlgorithmException e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "NoSuchAlgorithmException");
        } catch (NoSuchPaddingException e2) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "NoSuchPaddingException");
        } catch (InvalidKeyException e3) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "InvalidKeyException");
        } catch (IllegalBlockSizeException e4) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "IllegalBlockSizeException");
        } catch (BadPaddingException e5) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "BadPaddingException");
        } catch (InvalidAlgorithmParameterException e6) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "InvalidAlgorithmParameterException");
        }
        return bArr4;
    }

    public static byte[] DeAe(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        return b(bArr, bArr2, bArr3);
    }

    private static byte[] a() {
        try {
            SecureRandom.getInstance(b, c).nextBytes(a);
        } catch (NoSuchAlgorithmException e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "NoSuchAlgorithmException");
        } catch (NoSuchProviderException e2) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "NoSuchProviderException");
        }
        return a;
    }

    private static byte[] a(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = null;
        Key cVar = new c(bArr2, 0, bArr2.length, d);
        try {
            Mac instance = Mac.getInstance(d);
            instance.init(cVar);
            bArr3 = instance.doFinal(bArr);
        } catch (NoSuchAlgorithmException e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "NoSuchAlgorithmException");
        } catch (InvalidKeyException e2) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "InvalidKeyException");
        }
        return bArr3;
    }

    private static byte[] a(byte[] bArr) {
        long length = (long) bArr.length;
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.order(ByteOrder.LITTLE_ENDIAN);
        allocate.putLong(length);
        Object array = allocate.array();
        Object obj = new byte[(array.length + bArr.length)];
        System.arraycopy(array, 0, obj, 0, array.length);
        System.arraycopy(bArr, 0, obj, array.length, bArr.length);
        return obj;
    }

    private static byte[] a(byte[] bArr, String str, String str2) {
        try {
            RSAPublicKey rSAPublicKey = (RSAPublicKey) KeyFactory.getInstance(e).generatePublic(new b(new BigInteger(str2, 16), new BigInteger(str, 16)));
            Cipher instance = Cipher.getInstance(f);
            instance.init(1, rSAPublicKey);
            return instance.doFinal(bArr);
        } catch (NoSuchAlgorithmException e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "NoSuchAlgorithmException");
            return null;
        } catch (InvalidKeySpecException e2) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "InvalidKeySpecException");
            return null;
        } catch (NoSuchPaddingException e3) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "NoSuchPaddingException");
            return null;
        } catch (InvalidKeyException e4) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "InvalidKeyException");
            return null;
        } catch (IllegalBlockSizeException e5) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "IllegalBlockSizeException");
            return null;
        } catch (BadPaddingException e6) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "BadPaddingException");
            return null;
        }
    }

    private static byte[] b(byte[] bArr, byte[] bArr2) {
        Object obj = new byte[(bArr.length + bArr2.length)];
        System.arraycopy(bArr, 0, obj, 0, bArr.length);
        System.arraycopy(bArr2, 0, obj, bArr.length, bArr2.length);
        return obj;
    }

    public static byte[] keag() {
        return b();
    }

    private static byte[] b() {
        SharedPreferences sharedPreferences = InternalSDKUtil.getContext().getSharedPreferences(g, 0);
        long j = sharedPreferences.getLong(h, 0);
        if (0 == j) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Generating for first time");
            Editor edit = sharedPreferences.edit();
            edit.putLong(h, System.currentTimeMillis());
            edit.commit();
            return a();
        }
        if ((j + 86400000) - System.currentTimeMillis() <= 0) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "generated again");
            edit = sharedPreferences.edit();
            edit.putLong(h, System.currentTimeMillis());
            edit.commit();
            return a();
        }
        Log.internal(InternalSDKUtil.LOGGING_TAG, "already generated");
        return a;
    }

    public static String SeMeGe(String str, byte[] bArr, byte[] bArr2, byte[] bArr3, String str2, String str3) {
        return a(str, bArr, bArr2, bArr3, str2, str3);
    }

    private static byte[] b(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        byte[] bArr4 = null;
        Key cVar = new c(bArr2, j);
        try {
            Cipher instance = Cipher.getInstance(i);
            instance.init(2, cVar, new a(bArr3));
            bArr4 = instance.doFinal(bArr);
        } catch (NoSuchAlgorithmException e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "NoSuchAlgorithmException");
        } catch (NoSuchPaddingException e2) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "NoSuchPaddingException");
        } catch (InvalidKeyException e3) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "InvalidKeyException");
        } catch (IllegalBlockSizeException e4) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "IllegalBlockSizeException");
        } catch (BadPaddingException e5) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "BadPaddingException");
        } catch (InvalidAlgorithmParameterException e6) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "InvalidAlgorithmParameterException");
        }
        return bArr4;
    }

    public static byte[] generateKey(int i) {
        try {
            byte[] bArr = new byte[i];
            new SecureRandom().nextBytes(bArr);
            return bArr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
