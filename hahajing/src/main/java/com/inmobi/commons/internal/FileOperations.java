package com.inmobi.commons.internal;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

public class FileOperations {
    public static boolean setPreferences(Context context, String str, String str2, String str3) {
        if (context == null || str == null || str2 == null || "".equals(str.trim()) || "".equals(str2.trim())) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Failed to set preferences..App context NULL");
            return false;
        }
        Editor edit = context.getSharedPreferences(str, 0).edit();
        edit.putString(str2, str3);
        edit.commit();
        return true;
    }

    public static void setPreferences(Context context, String str, String str2, boolean z) {
        if (context == null || str == null || str2 == null || "".equals(str.trim()) || "".equals(str2.trim())) {
            Log.debug(InternalSDKUtil.LOGGING_TAG, "Failed to set preferences..App context NULL");
            return;
        }
        Editor edit = context.getSharedPreferences(str, 0).edit();
        edit.putBoolean(str2, z);
        edit.commit();
    }

    public static void setPreferences(Context context, String str, String str2, int i) {
        if (context == null || str == null || str2 == null || "".equals(str.trim()) || "".equals(str2.trim())) {
            Log.debug(InternalSDKUtil.LOGGING_TAG, "Failed to set preferences..App context NULL");
            return;
        }
        Editor edit = context.getSharedPreferences(str, 0).edit();
        edit.putInt(str2, i);
        edit.commit();
    }

    public static void setPreferences(Context context, String str, String str2, long j) {
        if (context == null || str == null || str2 == null || "".equals(str.trim()) || "".equals(str2.trim())) {
            Log.debug(InternalSDKUtil.LOGGING_TAG, "Failed to set preferences..App context NULL");
            return;
        }
        Editor edit = context.getSharedPreferences(str, 0).edit();
        edit.putLong(str2, j);
        edit.commit();
    }

    public static void setPreferences(Context context, String str, String str2, float f) {
        if (context == null || str == null || str2 == null || "".equals(str.trim()) || "".equals(str2.trim())) {
            Log.debug(InternalSDKUtil.LOGGING_TAG, "Failed to set preferences..App context NULL");
            return;
        }
        Editor edit = context.getSharedPreferences(str, 0).edit();
        edit.putFloat(str2, f);
        edit.commit();
    }

    public static String getPreferences(Context context, String str, String str2) {
        if (context != null && str != null && str2 != null && !"".equals(str.trim()) && !"".equals(str2.trim())) {
            return context.getSharedPreferences(str, 0).getString(str2, null);
        }
        Log.debug(InternalSDKUtil.LOGGING_TAG, "Failed to get preferences..App context NULL");
        return null;
    }

    public static boolean getBooleanPreferences(Context context, String str, String str2) {
        if (context != null && str != null && str2 != null && !"".equals(str.trim()) && !"".equals(str2.trim())) {
            return context.getSharedPreferences(str, 0).getBoolean(str2, false);
        }
        Log.debug(InternalSDKUtil.LOGGING_TAG, "Failed to get preferences..App context NULL");
        return false;
    }

    public static int getIntPreferences(Context context, String str, String str2) {
        if (context != null && str != null && str2 != null && !"".equals(str.trim()) && !"".equals(str2.trim())) {
            return context.getSharedPreferences(str, 0).getInt(str2, 0);
        }
        Log.debug(InternalSDKUtil.LOGGING_TAG, "Failed to get preferences..App context NULL");
        return 0;
    }

    public static long getLongPreferences(Context context, String str, String str2) {
        if (context != null && str != null && str2 != null && !"".equals(str.trim()) && !"".equals(str2.trim())) {
            return context.getSharedPreferences(str, 0).getLong(str2, 0);
        }
        Log.debug(InternalSDKUtil.LOGGING_TAG, "Failed to get preferences..App context NULL");
        return 0;
    }

    public static Object readFromFile(Context context, String str) {
        ObjectInputStream objectInputStream;
        Object readObject;
        Throwable th;
        Throwable th2;
        ObjectInputStream objectInputStream2 = null;
        if (context == null || str == null || "".equals(str.trim())) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Cannot read map application context or Filename NULL");
            return objectInputStream2;
        }
        ObjectInputStream objectInputStream3;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(new File(context.getDir("data", 0), str)));
            try {
                readObject = objectInputStream.readObject();
                objectInputStream3 = objectInputStream;
            } catch (EOFException e) {
                Log.internal(InternalSDKUtil.LOGGING_TAG, "End of File reached");
                objectInputStream3 = objectInputStream;
                if (objectInputStream3 != null) {
                    return readObject;
                }
                try {
                    objectInputStream3.close();
                    return readObject;
                } catch (IOException e2) {
                    Log.internal(InternalSDKUtil.LOGGING_TAG, "Log File Close Exception");
                    return Boolean.valueOf(false);
                }
            } catch (Throwable e3) {
                th = e3;
                objectInputStream3 = objectInputStream;
                th2 = th;
                Log.internal(InternalSDKUtil.LOGGING_TAG, "Event log File doesnot exist", th2);
                if (objectInputStream3 != null) {
                    return readObject;
                }
                objectInputStream3.close();
                return readObject;
            } catch (Throwable e32) {
                th = e32;
                objectInputStream3 = objectInputStream;
                th2 = th;
                Log.internal(InternalSDKUtil.LOGGING_TAG, "Event log File corrupted", th2);
                if (objectInputStream3 != null) {
                    return readObject;
                }
                objectInputStream3.close();
                return readObject;
            } catch (Throwable e322) {
                th = e322;
                objectInputStream3 = objectInputStream;
                th2 = th;
                Log.internal(InternalSDKUtil.LOGGING_TAG, "Event log File IO Exception", th2);
                if (objectInputStream3 != null) {
                    return readObject;
                }
                objectInputStream3.close();
                return readObject;
            } catch (Throwable e3222) {
                th = e3222;
                objectInputStream3 = objectInputStream;
                th2 = th;
                Log.internal(InternalSDKUtil.LOGGING_TAG, "Error: class not found", th2);
                if (objectInputStream3 != null) {
                    return readObject;
                }
                objectInputStream3.close();
                return readObject;
            }
        } catch (EOFException e4) {
            objectInputStream = objectInputStream2;
            Log.internal(InternalSDKUtil.LOGGING_TAG, "End of File reached");
            objectInputStream3 = objectInputStream;
            if (objectInputStream3 != null) {
                return readObject;
            }
            objectInputStream3.close();
            return readObject;
        } catch (FileNotFoundException e5) {
            th2 = e5;
            objectInputStream3 = objectInputStream2;
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Event log File doesnot exist", th2);
            if (objectInputStream3 != null) {
                return readObject;
            }
            objectInputStream3.close();
            return readObject;
        } catch (StreamCorruptedException e6) {
            th2 = e6;
            objectInputStream3 = objectInputStream2;
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Event log File corrupted", th2);
            if (objectInputStream3 != null) {
                return readObject;
            }
            objectInputStream3.close();
            return readObject;
        } catch (IOException e7) {
            th2 = e7;
            objectInputStream3 = objectInputStream2;
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Event log File IO Exception", th2);
            if (objectInputStream3 != null) {
                return readObject;
            }
            objectInputStream3.close();
            return readObject;
        } catch (ClassNotFoundException e8) {
            th2 = e8;
            objectInputStream3 = objectInputStream2;
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Error: class not found", th2);
            if (objectInputStream3 != null) {
                return readObject;
            }
            objectInputStream3.close();
            return readObject;
        }
        if (objectInputStream3 != null) {
            return readObject;
        }
        objectInputStream3.close();
        return readObject;
    }

    public static boolean saveToFile(Context context, String str, Object obj) {
        if (context == null || str == null || "".equals(str.trim()) || obj == null) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Cannot read map application context of Filename NULL");
            return false;
        }
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(context.getDir("data", 0), str), false));
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    Log.internal(InternalSDKUtil.LOGGING_TAG, "Log File Close Exception");
                    return false;
                }
            }
            return true;
        } catch (Throwable e2) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Log File Not found", e2);
            return false;
        } catch (Throwable e22) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Log File IO Exception", e22);
            return false;
        }
    }

    public static boolean isFileExist(Context context, String str) {
        if (new File(context.getDir("data", 0), str).exists()) {
            return true;
        }
        return false;
    }

    public static String readFileAsString(Context context, String str) throws IOException {
        File file = new File(context.getCacheDir().getAbsolutePath() + File.separator + str);
        file.createNewFile();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                break;
            }
            stringBuffer.append("\n").append(readLine);
        }
        bufferedReader.close();
        if (stringBuffer.length() >= 1) {
            return stringBuffer.substring(1).toString();
        }
        return "";
    }

    public static void writeStringToFile(Context context, String str, String str2, boolean z) throws IOException {
        File file = new File(context.getCacheDir().getAbsolutePath() + File.separator + str);
        file.createNewFile();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, z));
        bufferedWriter.write(str2);
        bufferedWriter.close();
    }
}
