package org.xmlrpc.android;

import android.util.Log;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.Socket;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class XMLRPCServer extends XMLRPCCommon {
    private static final String NEWLINES = "\n\n";
    private static final String RESPONSE = "HTTP/1.1 200 OK\nConnection: close\nContent-Type: text/xml\nContent-Length: ";
    private XMLRPCSerializer iXMLRPCSerializer = new XMLRPCSerializer();

    public MethodCall readMethodCall(Socket socket) throws IOException, XmlPullParserException {
        MethodCall methodCall = new MethodCall();
        XmlPullParser pullParser = xmlPullParserFromSocket(socket.getInputStream());
        pullParser.nextTag();
        pullParser.require(2, null, "methodCall");
        pullParser.nextTag();
        pullParser.require(2, null, "methodName");
        methodCall.setMethodName(pullParser.nextText());
        pullParser.nextTag();
        pullParser.require(2, null, "params");
        pullParser.nextTag();
        do {
            pullParser.require(2, null, "param");
            pullParser.nextTag();
            methodCall.params.add(this.iXMLRPCSerializer.deserialize(pullParser));
            pullParser.nextTag();
            pullParser.require(3, null, "param");
            pullParser.nextTag();
        } while (!pullParser.getName().equals("params"));
        return methodCall;
    }

    XmlPullParser xmlPullParserFromSocket(InputStream socketInputStream) throws IOException, XmlPullParserException {
        String xmlRpcText = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(socketInputStream));
        String line;
        do {
            line = br.readLine();
            if (line == null) {
                break;
            }
        } while (line.length() > 0);
        while (br.ready()) {
            xmlRpcText = new StringBuilder(String.valueOf(xmlRpcText)).append(br.readLine()).toString();
        }
        InputStream inputStream = new ByteArrayInputStream(xmlRpcText.getBytes(HttpRequest.CHARSET_UTF8));
        XmlPullParser pullParser = XmlPullParserFactory.newInstance().newPullParser();
        pullParser.setInput(new InputStreamReader(inputStream));
        return pullParser;
    }

    public void respond(Socket socket, Object[] params) throws IOException {
        String content = methodResponse(params);
        String response = new StringBuilder(RESPONSE).append(content.length()).append(NEWLINES).append(content).toString();
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(response.getBytes());
        outputStream.flush();
        outputStream.close();
        socket.close();
        Log.d("XMLRPC", "response:" + response);
    }

    private String methodResponse(Object[] params) throws IllegalArgumentException, IllegalStateException, IOException {
        StringWriter bodyWriter = new StringWriter();
        this.serializer.setOutput(bodyWriter);
        this.serializer.startDocument(null, null);
        this.serializer.startTag(null, "methodResponse");
        serializeParams(params);
        this.serializer.endTag(null, "methodResponse");
        this.serializer.endDocument();
        return bodyWriter.toString();
    }
}
