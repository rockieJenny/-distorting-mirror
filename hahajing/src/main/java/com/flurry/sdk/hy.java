package com.flurry.sdk;

import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.lang.reflect.Field;

public enum hy implements hz {
    IDENTITY {
        public String a(Field field) {
            return field.getName();
        }
    },
    UPPER_CAMEL_CASE {
        public String a(Field field) {
            return hy.b(field.getName());
        }
    },
    UPPER_CAMEL_CASE_WITH_SPACES {
        public String a(Field field) {
            return hy.b(hy.b(field.getName(), " "));
        }
    },
    LOWER_CASE_WITH_UNDERSCORES {
        public String a(Field field) {
            return hy.b(field.getName(), EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR).toLowerCase();
        }
    },
    LOWER_CASE_WITH_DASHES {
        public String a(Field field) {
            return hy.b(field.getName(), "-").toLowerCase();
        }
    };

    private static String b(String str, String str2) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (Character.isUpperCase(charAt) && stringBuilder.length() != 0) {
                stringBuilder.append(str2);
            }
            stringBuilder.append(charAt);
        }
        return stringBuilder.toString();
    }

    private static String b(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        char charAt = str.charAt(0);
        while (i < str.length() - 1 && !Character.isLetter(charAt)) {
            stringBuilder.append(charAt);
            i++;
            charAt = str.charAt(i);
        }
        if (i == str.length()) {
            return stringBuilder.toString();
        }
        if (Character.isUpperCase(charAt)) {
            return str;
        }
        return stringBuilder.append(a(Character.toUpperCase(charAt), str, i + 1)).toString();
    }

    private static String a(char c, String str, int i) {
        if (i < str.length()) {
            return c + str.substring(i);
        }
        return String.valueOf(c);
    }
}
