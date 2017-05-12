package com.google.android.gms.analytics;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlrpc.android.IXMLRPCSerializer;

abstract class n<T extends m> {
    Context mContext;
    a<T> yL;

    public interface a<U extends m> {
        void c(String str, int i);

        void e(String str, String str2);

        void e(String str, boolean z);

        U er();

        void f(String str, String str2);
    }

    public n(Context context, a<T> aVar) {
        this.mContext = context;
        this.yL = aVar;
    }

    private T a(XmlResourceParser xmlResourceParser) {
        String trim;
        try {
            xmlResourceParser.next();
            int eventType = xmlResourceParser.getEventType();
            while (eventType != 1) {
                if (xmlResourceParser.getEventType() == 2) {
                    String toLowerCase = xmlResourceParser.getName().toLowerCase();
                    if (toLowerCase.equals("screenname")) {
                        toLowerCase = xmlResourceParser.getAttributeValue(null, IXMLRPCSerializer.TAG_NAME);
                        trim = xmlResourceParser.nextText().trim();
                        if (!(TextUtils.isEmpty(toLowerCase) || TextUtils.isEmpty(trim))) {
                            this.yL.e(toLowerCase, trim);
                        }
                    } else if (toLowerCase.equals(IXMLRPCSerializer.TYPE_STRING)) {
                        r0 = xmlResourceParser.getAttributeValue(null, IXMLRPCSerializer.TAG_NAME);
                        trim = xmlResourceParser.nextText().trim();
                        if (!(TextUtils.isEmpty(r0) || trim == null)) {
                            this.yL.f(r0, trim);
                        }
                    } else if (toLowerCase.equals("bool")) {
                        toLowerCase = xmlResourceParser.getAttributeValue(null, IXMLRPCSerializer.TAG_NAME);
                        trim = xmlResourceParser.nextText().trim();
                        if (!(TextUtils.isEmpty(toLowerCase) || TextUtils.isEmpty(trim))) {
                            try {
                                this.yL.e(toLowerCase, Boolean.parseBoolean(trim));
                            } catch (NumberFormatException e) {
                                ae.T("Error parsing bool configuration value: " + trim);
                            }
                        }
                    } else if (toLowerCase.equals("integer")) {
                        r0 = xmlResourceParser.getAttributeValue(null, IXMLRPCSerializer.TAG_NAME);
                        trim = xmlResourceParser.nextText().trim();
                        if (!(TextUtils.isEmpty(r0) || TextUtils.isEmpty(trim))) {
                            try {
                                this.yL.c(r0, Integer.parseInt(trim));
                            } catch (NumberFormatException e2) {
                                ae.T("Error parsing int configuration value: " + trim);
                            }
                        }
                    } else {
                        continue;
                    }
                }
                eventType = xmlResourceParser.next();
            }
        } catch (XmlPullParserException e3) {
            ae.T("Error parsing tracker configuration file: " + e3);
        } catch (IOException e4) {
            ae.T("Error parsing tracker configuration file: " + e4);
        }
        return this.yL.er();
    }

    public T x(int i) {
        try {
            return a(this.mContext.getResources().getXml(i));
        } catch (NotFoundException e) {
            ae.W("inflate() called with unknown resourceId: " + e);
            return null;
        }
    }
}
