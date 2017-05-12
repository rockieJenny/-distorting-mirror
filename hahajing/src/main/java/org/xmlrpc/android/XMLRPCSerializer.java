package org.xmlrpc.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

class XMLRPCSerializer implements IXMLRPCSerializer {
    static SimpleDateFormat dateFormat = new SimpleDateFormat(IXMLRPCSerializer.DATETIME_FORMAT);

    XMLRPCSerializer() {
    }

    public void serialize(XmlSerializer serializer, Object object) throws IOException {
        if ((object instanceof Integer) || (object instanceof Short) || (object instanceof Byte)) {
            serializer.startTag(null, IXMLRPCSerializer.TYPE_I4).text(object.toString()).endTag(null, IXMLRPCSerializer.TYPE_I4);
        } else if (object instanceof Long) {
            serializer.startTag(null, IXMLRPCSerializer.TYPE_I8).text(object.toString()).endTag(null, IXMLRPCSerializer.TYPE_I8);
        } else if ((object instanceof Double) || (object instanceof Float)) {
            serializer.startTag(null, IXMLRPCSerializer.TYPE_DOUBLE).text(object.toString()).endTag(null, IXMLRPCSerializer.TYPE_DOUBLE);
        } else if (object instanceof Boolean) {
            serializer.startTag(null, IXMLRPCSerializer.TYPE_BOOLEAN).text(((Boolean) object).booleanValue() ? "1" : "0").endTag(null, IXMLRPCSerializer.TYPE_BOOLEAN);
        } else if (object instanceof String) {
            serializer.startTag(null, IXMLRPCSerializer.TYPE_STRING).text(object.toString()).endTag(null, IXMLRPCSerializer.TYPE_STRING);
        } else if ((object instanceof Date) || (object instanceof Calendar)) {
            serializer.startTag(null, IXMLRPCSerializer.TYPE_DATE_TIME_ISO8601).text(dateFormat.format(object)).endTag(null, IXMLRPCSerializer.TYPE_DATE_TIME_ISO8601);
        } else if (object instanceof byte[]) {
            serializer.startTag(null, IXMLRPCSerializer.TYPE_BASE64).text(new String(Base64Coder.encode((byte[]) object))).endTag(null, IXMLRPCSerializer.TYPE_BASE64);
        } else if (object instanceof List) {
            serializer.startTag(null, IXMLRPCSerializer.TYPE_ARRAY).startTag(null, "data");
            for (Object o : (List) object) {
                serializer.startTag(null, "value");
                serialize(serializer, o);
                serializer.endTag(null, "value");
            }
            serializer.endTag(null, "data").endTag(null, IXMLRPCSerializer.TYPE_ARRAY);
        } else if (object instanceof Object[]) {
            serializer.startTag(null, IXMLRPCSerializer.TYPE_ARRAY).startTag(null, "data");
            Object[] objects = (Object[]) object;
            for (Object o2 : objects) {
                serializer.startTag(null, "value");
                serialize(serializer, o2);
                serializer.endTag(null, "value");
            }
            serializer.endTag(null, "data").endTag(null, IXMLRPCSerializer.TYPE_ARRAY);
        } else if (object instanceof Map) {
            serializer.startTag(null, IXMLRPCSerializer.TYPE_STRUCT);
            for (Entry<String, Object> entry : ((Map) object).entrySet()) {
                String key = (String) entry.getKey();
                Object value = entry.getValue();
                serializer.startTag(null, IXMLRPCSerializer.TAG_MEMBER);
                serializer.startTag(null, IXMLRPCSerializer.TAG_NAME).text(key).endTag(null, IXMLRPCSerializer.TAG_NAME);
                serializer.startTag(null, "value");
                serialize(serializer, value);
                serializer.endTag(null, "value");
                serializer.endTag(null, IXMLRPCSerializer.TAG_MEMBER);
            }
            serializer.endTag(null, IXMLRPCSerializer.TYPE_STRUCT);
        } else if (object instanceof XMLRPCSerializable) {
            serialize(serializer, ((XMLRPCSerializable) object).getSerializable());
        } else {
            throw new IOException("Cannot serialize " + object);
        }
    }

    public Object deserialize(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(2, null, "value");
        if (parser.isEmptyElementTag()) {
            return "";
        }
        Object obj;
        boolean hasType = true;
        String typeNodeName = null;
        try {
            parser.nextTag();
            typeNodeName = parser.getName();
            if (typeNodeName.equals("value") && parser.getEventType() == 3) {
                return "";
            }
        } catch (XmlPullParserException e) {
            hasType = false;
        }
        if (!hasType) {
            obj = parser.getText();
        } else if (typeNodeName.equals(IXMLRPCSerializer.TYPE_INT) || typeNodeName.equals(IXMLRPCSerializer.TYPE_I4)) {
            obj = Integer.valueOf(Integer.parseInt(parser.nextText()));
        } else if (typeNodeName.equals(IXMLRPCSerializer.TYPE_I8)) {
            obj = Long.valueOf(Long.parseLong(parser.nextText()));
        } else if (typeNodeName.equals(IXMLRPCSerializer.TYPE_DOUBLE)) {
            obj = Double.valueOf(Double.parseDouble(parser.nextText()));
        } else if (typeNodeName.equals(IXMLRPCSerializer.TYPE_BOOLEAN)) {
            obj = parser.nextText().equals("1") ? Boolean.TRUE : Boolean.FALSE;
        } else if (typeNodeName.equals(IXMLRPCSerializer.TYPE_STRING)) {
            obj = parser.nextText();
        } else if (typeNodeName.equals(IXMLRPCSerializer.TYPE_DATE_TIME_ISO8601)) {
            String value = parser.nextText();
            try {
                obj = dateFormat.parseObject(value);
            } catch (ParseException e2) {
                throw new IOException("Cannot deserialize dateTime " + value);
            }
        } else if (typeNodeName.equals(IXMLRPCSerializer.TYPE_BASE64)) {
            BufferedReader reader = new BufferedReader(new StringReader(parser.nextText()));
            StringBuffer sb = new StringBuffer();
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line);
            }
            obj = Base64Coder.decode(sb.toString());
        } else if (typeNodeName.equals(IXMLRPCSerializer.TYPE_ARRAY)) {
            parser.nextTag();
            parser.require(2, null, "data");
            parser.nextTag();
            List<Object> list = new ArrayList();
            while (parser.getName().equals("value")) {
                list.add(deserialize(parser));
                parser.nextTag();
            }
            parser.require(3, null, "data");
            parser.nextTag();
            parser.require(3, null, IXMLRPCSerializer.TYPE_ARRAY);
            obj = list.toArray();
        } else if (typeNodeName.equals(IXMLRPCSerializer.TYPE_STRUCT)) {
            parser.nextTag();
            Map<String, Object> map = new HashMap();
            while (parser.getName().equals(IXMLRPCSerializer.TAG_MEMBER)) {
                String memberName = null;
                Object obj2 = null;
                while (true) {
                    parser.nextTag();
                    String name = parser.getName();
                    if (!name.equals(IXMLRPCSerializer.TAG_NAME)) {
                        if (!name.equals("value")) {
                            break;
                        }
                        obj2 = deserialize(parser);
                    } else {
                        memberName = parser.nextText();
                    }
                }
                if (!(memberName == null || obj2 == null)) {
                    map.put(memberName, obj2);
                }
                parser.require(3, null, IXMLRPCSerializer.TAG_MEMBER);
                parser.nextTag();
            }
            parser.require(3, null, IXMLRPCSerializer.TYPE_STRUCT);
            Map<String, Object> obj3 = map;
        } else {
            throw new IOException("Cannot deserialize " + parser.getName());
        }
        parser.nextTag();
        parser.require(3, null, "value");
        return obj;
    }
}
