package org.xmlrpc.android;

import android.util.Xml;
import java.io.IOException;
import org.xmlpull.v1.XmlSerializer;

class XMLRPCCommon {
    protected IXMLRPCSerializer iXMLRPCSerializer = new XMLRPCSerializer();
    protected XmlSerializer serializer = Xml.newSerializer();

    XMLRPCCommon() {
    }

    public void setSerializer(IXMLRPCSerializer serializer) {
        this.iXMLRPCSerializer = serializer;
    }

    protected void serializeParams(Object[] params) throws IllegalArgumentException, IllegalStateException, IOException {
        if (params != null && params.length != 0) {
            this.serializer.startTag(null, "params");
            for (Object serialize : params) {
                this.serializer.startTag(null, "param").startTag(null, "value");
                this.iXMLRPCSerializer.serialize(this.serializer, serialize);
                this.serializer.endTag(null, "value").endTag(null, "param");
            }
            this.serializer.endTag(null, "params");
        }
    }
}
