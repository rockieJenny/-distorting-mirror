package com.flurry.sdk;

import android.support.v4.widget.ExploreByTouchHelper;
import android.text.TextUtils;
import android.util.Xml;
import com.inmobi.commons.analytics.db.AnalyticsEvent;
import com.inmobi.commons.analytics.db.AnalyticsSQLiteHelper;
import java.io.Closeable;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class da {
    private static final String a = null;
    private static a b = a.PARSING_UNKNOWN;

    enum a {
        PARSING_UNKNOWN,
        PARSING_STARTED,
        PARSING_COMPLETE,
        PARSING_ERROR
    }

    public static cy a(String str) {
        Closeable stringReader;
        cy a;
        Throwable e;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        a(a.PARSING_UNKNOWN);
        try {
            stringReader = new StringReader(str);
            try {
                XmlPullParser newPullParser = Xml.newPullParser();
                newPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", false);
                newPullParser.setInput(stringReader);
                newPullParser.nextTag();
                a = a(newPullParser, new com.flurry.sdk.cy.a(), new ArrayList());
                if (a != null && a.c() && a.f() == null) {
                    a = new com.flurry.sdk.cy.a().a().b();
                }
                hp.a(stringReader);
            } catch (Exception e2) {
                e = e2;
                try {
                    if (a().equals(a.PARSING_ERROR)) {
                        gd.a(3, "VASTXmlParser", "Not a VAST Ad");
                        a = null;
                    } else {
                        gd.a(3, "VASTXmlParser", "Error parsing VAST XML: " + str, e);
                        a = new com.flurry.sdk.cy.a().a().b();
                    }
                    hp.a(stringReader);
                    return a;
                } catch (Throwable th) {
                    e = th;
                    hp.a(stringReader);
                    throw e;
                }
            }
        } catch (Exception e3) {
            e = e3;
            stringReader = null;
            if (a().equals(a.PARSING_ERROR)) {
                gd.a(3, "VASTXmlParser", "Not a VAST Ad");
                a = null;
            } else {
                gd.a(3, "VASTXmlParser", "Error parsing VAST XML: " + str, e);
                a = new com.flurry.sdk.cy.a().a().b();
            }
            hp.a(stringReader);
            return a;
        } catch (Throwable th2) {
            e = th2;
            stringReader = null;
            hp.a(stringReader);
            throw e;
        }
        return a;
    }

    private static cy a(XmlPullParser xmlPullParser, com.flurry.sdk.cy.a aVar, List<dg> list) throws XmlPullParserException, IOException, IllegalArgumentException {
        xmlPullParser.require(2, a, "VAST");
        a(a.PARSING_STARTED);
        int c = c(xmlPullParser);
        if (a(c)) {
            aVar.a(c);
            while (xmlPullParser.next() != 3) {
                if (xmlPullParser.getEventType() == 2) {
                    if (xmlPullParser.getName().equals("Ad")) {
                        list.add(a(xmlPullParser, new com.flurry.sdk.dg.a()));
                    } else {
                        b(xmlPullParser);
                    }
                }
            }
            aVar.a((List) list);
            aVar.a(a((List) list));
            if (a((List) list)) {
                a(a.PARSING_COMPLETE);
            } else {
                a(a.PARSING_UNKNOWN);
            }
            return aVar.b();
        }
        a(a.PARSING_ERROR);
        throw new IllegalArgumentException();
    }

    private static dg a(XmlPullParser xmlPullParser, com.flurry.sdk.dg.a aVar) throws IOException, XmlPullParserException {
        Object attributeValue;
        xmlPullParser.require(2, a, "Ad");
        aVar.a(xmlPullParser.getAttributeValue(a, AnalyticsEvent.EVENT_ID));
        try {
            attributeValue = xmlPullParser.getAttributeValue(a, "sequence");
            if (!TextUtils.isEmpty(attributeValue)) {
                aVar.a(Integer.parseInt(attributeValue));
            }
        } catch (NumberFormatException e) {
            gd.a(3, "VASTXmlParser", "Could not identify Ad Sequence");
        }
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() == 2) {
                String name = xmlPullParser.getName();
                attributeValue = -1;
                switch (name.hashCode()) {
                    case -2101083431:
                        if (name.equals("InLine")) {
                            attributeValue = null;
                            break;
                        }
                        break;
                    case -1034806157:
                        if (name.equals("Wrapper")) {
                            attributeValue = 1;
                            break;
                        }
                        break;
                }
                switch (attributeValue) {
                    case null:
                        aVar.a(a(xmlPullParser, new com.flurry.sdk.di.a(), new com.flurry.sdk.dh.a(), new ArrayList(), new ArrayList()));
                        break;
                    case 1:
                        aVar.a(a(xmlPullParser, new com.flurry.sdk.di.a(), new com.flurry.sdk.dh.a(), new ArrayList(), new ArrayList(), new ArrayList()));
                        break;
                    default:
                        b(xmlPullParser);
                        break;
                }
            }
        }
        return aVar.a();
    }

    private static di a(XmlPullParser xmlPullParser, com.flurry.sdk.di.a aVar, com.flurry.sdk.dh.a aVar2, List<String> list, List<String> list2) throws IOException, XmlPullParserException {
        aVar.a(db.InLine);
        xmlPullParser.require(2, a, "InLine");
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() == 2) {
                String name = xmlPullParser.getName();
                int i = -1;
                switch (name.hashCode()) {
                    case -1692490108:
                        if (name.equals("Creatives")) {
                            i = 0;
                            break;
                        }
                        break;
                    case -1633884078:
                        if (name.equals("AdSystem")) {
                            i = 1;
                            break;
                        }
                        break;
                    case 67232232:
                        if (name.equals("Error")) {
                            i = 4;
                            break;
                        }
                        break;
                    case 501930965:
                        if (name.equals("AdTitle")) {
                            i = 2;
                            break;
                        }
                        break;
                    case 2114088489:
                        if (name.equals("Impression")) {
                            i = 3;
                            break;
                        }
                        break;
                }
                switch (i) {
                    case 0:
                        aVar.d(a(xmlPullParser, new ArrayList()));
                        break;
                    case 1:
                        aVar2.a(xmlPullParser.getAttributeValue(a, "version"));
                        aVar2.b(a(xmlPullParser));
                        aVar.a(aVar2.a());
                        break;
                    case 2:
                        aVar.a(a(xmlPullParser));
                        break;
                    case 3:
                        a((List) list, a(xmlPullParser));
                        break;
                    case 4:
                        a((List) list2, a(xmlPullParser));
                        break;
                    default:
                        b(xmlPullParser);
                        break;
                }
            }
        }
        aVar.b(list);
        aVar.c(list2);
        return aVar.a();
    }

    private static di a(XmlPullParser xmlPullParser, com.flurry.sdk.di.a aVar, com.flurry.sdk.dh.a aVar2, List<String> list, List<String> list2, List<String> list3) throws IOException, XmlPullParserException {
        aVar.a(db.Wrapper);
        xmlPullParser.require(2, a, "Wrapper");
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() == 2) {
                String name = xmlPullParser.getName();
                int i = -1;
                switch (name.hashCode()) {
                    case -1692490108:
                        if (name.equals("Creatives")) {
                            i = 0;
                            break;
                        }
                        break;
                    case -1633884078:
                        if (name.equals("AdSystem")) {
                            i = 1;
                            break;
                        }
                        break;
                    case -587420703:
                        if (name.equals("VASTAdTagURI")) {
                            i = 2;
                            break;
                        }
                        break;
                    case 67232232:
                        if (name.equals("Error")) {
                            i = 4;
                            break;
                        }
                        break;
                    case 2114088489:
                        if (name.equals("Impression")) {
                            i = 3;
                            break;
                        }
                        break;
                }
                switch (i) {
                    case 0:
                        aVar.d(a(xmlPullParser, new ArrayList()));
                        break;
                    case 1:
                        aVar2.a(xmlPullParser.getAttributeValue(a, "version"));
                        aVar2.b(a(xmlPullParser));
                        aVar.a(aVar2.a());
                        break;
                    case 2:
                        a((List) list, a(xmlPullParser));
                        break;
                    case 3:
                        a((List) list2, a(xmlPullParser));
                        break;
                    case 4:
                        a((List) list3, a(xmlPullParser));
                        break;
                    default:
                        b(xmlPullParser);
                        break;
                }
            }
        }
        aVar.a((List) list);
        aVar.b(list2);
        aVar.c(list3);
        return aVar.a();
    }

    private static List<dj> a(XmlPullParser xmlPullParser, List<dj> list) throws IOException, XmlPullParserException {
        xmlPullParser.require(2, a, "Creatives");
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("Creative")) {
                    list.add(a(xmlPullParser, new com.flurry.sdk.dj.a()));
                } else {
                    b(xmlPullParser);
                }
            }
        }
        return list;
    }

    private static dj a(XmlPullParser xmlPullParser, com.flurry.sdk.dj.a aVar) throws IOException, XmlPullParserException {
        xmlPullParser.require(2, a, "Creative");
        aVar.a(xmlPullParser.getAttributeValue(a, AnalyticsEvent.EVENT_ID));
        String attributeValue = xmlPullParser.getAttributeValue(a, "sequence");
        if (attributeValue != null) {
            try {
                aVar.a(Math.round(Float.parseFloat(attributeValue)));
            } catch (NumberFormatException e) {
                gd.a(3, "VASTXmlParser", "Could not identify Creative sequence");
            }
        }
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("Linear")) {
                    aVar.a(dc.Linear);
                    aVar.a(a(xmlPullParser, new com.flurry.sdk.dk.a()));
                } else {
                    b(xmlPullParser);
                }
            }
        }
        return aVar.a();
    }

    private static dk a(XmlPullParser xmlPullParser, com.flurry.sdk.dk.a aVar) throws IOException, XmlPullParserException {
        xmlPullParser.require(2, a, "Linear");
        String attributeValue = xmlPullParser.getAttributeValue(a, "skipoffset");
        if (attributeValue != null) {
            aVar.b(cz.a(attributeValue));
        }
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() == 2) {
                String name = xmlPullParser.getName();
                int i = -1;
                switch (name.hashCode()) {
                    case -2049897434:
                        if (name.equals("VideoClicks")) {
                            i = 2;
                            break;
                        }
                        break;
                    case -1927368268:
                        if (name.equals("Duration")) {
                            i = 0;
                            break;
                        }
                        break;
                    case -385055469:
                        if (name.equals("MediaFiles")) {
                            i = 3;
                            break;
                        }
                        break;
                    case 611554000:
                        if (name.equals("TrackingEvents")) {
                            i = 1;
                            break;
                        }
                        break;
                }
                switch (i) {
                    case 0:
                        aVar.a(cz.a(a(xmlPullParser)));
                        break;
                    case 1:
                        aVar.a(b(xmlPullParser, new fu()));
                        break;
                    case 2:
                        aVar.b(a(xmlPullParser, new fu()));
                        break;
                    case 3:
                        dl a = cz.a(b(xmlPullParser, new ArrayList()));
                        if (a == null) {
                            break;
                        }
                        aVar.a(a);
                        break;
                    default:
                        b(xmlPullParser);
                        break;
                }
            }
        }
        return aVar.a();
    }

    private static fu<df, String> a(XmlPullParser xmlPullParser, fu<df, String> fuVar) throws IOException, XmlPullParserException {
        xmlPullParser.require(2, a, "VideoClicks");
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() == 2) {
                String name = xmlPullParser.getName();
                int i = -1;
                switch (name.hashCode()) {
                    case -617879491:
                        if (name.equals("ClickThrough")) {
                            i = 0;
                            break;
                        }
                        break;
                    case -135761801:
                        if (name.equals("CustomClick")) {
                            i = 2;
                            break;
                        }
                        break;
                    case 2107600959:
                        if (name.equals("ClickTracking")) {
                            i = 1;
                            break;
                        }
                        break;
                }
                switch (i) {
                    case 0:
                        fuVar.a(df.ClickThrough, a(xmlPullParser));
                        break;
                    case 1:
                        fuVar.a(df.ClickTracking, a(xmlPullParser));
                        break;
                    case 2:
                        fuVar.a(df.CustomClick, a(xmlPullParser));
                        break;
                    default:
                        b(xmlPullParser);
                        break;
                }
            }
        }
        return fuVar;
    }

    private static fu<de, String> b(XmlPullParser xmlPullParser, fu<de, String> fuVar) throws IOException, XmlPullParserException {
        xmlPullParser.require(2, a, "TrackingEvents");
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("Tracking")) {
                    dm a = a(xmlPullParser, new com.flurry.sdk.dm.a());
                    if (!TextUtils.isEmpty(a.b())) {
                        fuVar.a(a.a(), a.b());
                    }
                } else {
                    b(xmlPullParser);
                }
            }
        }
        return fuVar;
    }

    private static dm a(XmlPullParser xmlPullParser, com.flurry.sdk.dm.a aVar) throws IOException, XmlPullParserException {
        xmlPullParser.require(2, a, "Tracking");
        aVar.a(de.a(xmlPullParser.getAttributeValue(a, "event")));
        aVar.a(a(xmlPullParser));
        return aVar.a();
    }

    private static List<dl> b(XmlPullParser xmlPullParser, List<dl> list) throws IOException, XmlPullParserException {
        xmlPullParser.require(2, a, "MediaFiles");
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("MediaFile")) {
                    list.add(a(xmlPullParser, new com.flurry.sdk.dl.a()));
                } else {
                    b(xmlPullParser);
                }
            }
        }
        return list;
    }

    private static dl a(XmlPullParser xmlPullParser, com.flurry.sdk.dl.a aVar) throws IOException, XmlPullParserException {
        xmlPullParser.require(2, a, "MediaFile");
        aVar.a(xmlPullParser.getAttributeValue(a, AnalyticsEvent.EVENT_ID));
        aVar.d(xmlPullParser.getAttributeValue(a, AnalyticsSQLiteHelper.EVENT_LIST_TYPE));
        aVar.b(xmlPullParser.getAttributeValue(a, "apiFramework"));
        aVar.a(dd.a(xmlPullParser.getAttributeValue(a, "delivery")));
        try {
            aVar.b(Integer.parseInt(xmlPullParser.getAttributeValue(a, "height")));
        } catch (NumberFormatException e) {
            gd.a(3, "VASTXmlParser", "Could not identify MediaFile height");
        }
        try {
            aVar.c(Integer.parseInt(xmlPullParser.getAttributeValue(a, "width")));
        } catch (NumberFormatException e2) {
            gd.a(3, "VASTXmlParser", "Could not identify MediaFile width");
        }
        try {
            aVar.a(Integer.parseInt(xmlPullParser.getAttributeValue(a, "bitrate")));
        } catch (NumberFormatException e3) {
            gd.a(3, "VASTXmlParser", "Could not identify MediaFile bitRate");
        }
        aVar.b(Boolean.parseBoolean(xmlPullParser.getAttributeValue(a, "scalable")));
        aVar.a(Boolean.parseBoolean(xmlPullParser.getAttributeValue(a, "maintainAspectRatio")));
        aVar.c(a(xmlPullParser));
        return aVar.a();
    }

    private static String a(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        if (xmlPullParser.next() != 4) {
            return null;
        }
        String trim = xmlPullParser.getText().trim();
        xmlPullParser.nextTag();
        return trim;
    }

    private static void a(List<String> list, String str) {
        if (list != null && !TextUtils.isEmpty(str)) {
            list.add(str);
        }
    }

    private static void b(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        if (xmlPullParser.getEventType() != 2) {
            throw new IllegalStateException();
        }
        int i = 1;
        while (i != 0) {
            switch (xmlPullParser.next()) {
                case 2:
                    i++;
                    break;
                case 3:
                    i--;
                    break;
                default:
                    break;
            }
        }
    }

    public static boolean a(List<dg> list) {
        if (list == null || list.isEmpty()) {
            return false;
        }
        for (dg c : list) {
            di c2 = c.c();
            if (c2 != null) {
                if (!db.InLine.equals(c2.a())) {
                }
            }
            return false;
        }
        return true;
    }

    private static boolean a(int i) {
        if (i < 1 || i > 3) {
            return false;
        }
        return true;
    }

    private static int c(XmlPullParser xmlPullParser) {
        int i = ExploreByTouchHelper.INVALID_ID;
        Object attributeValue = xmlPullParser.getAttributeValue(a, "version");
        gd.a(3, "VASTXmlParser", "Version" + attributeValue);
        if (!TextUtils.isEmpty(attributeValue)) {
            String[] split = attributeValue.split("\\.");
            if (split.length > 0 && !TextUtils.isEmpty(split[0])) {
                try {
                    i = Integer.parseInt(split[0]);
                } catch (NumberFormatException e) {
                    gd.a(3, "VASTXmlParser", "Could not detect VAST version " + split[0]);
                }
            }
        }
        return i;
    }

    private static void a(a aVar) {
        gd.a(3, "VASTXmlParser", "Setting VAST parse state as: " + aVar.name());
        b = aVar;
    }

    private static a a() {
        return b;
    }
}
