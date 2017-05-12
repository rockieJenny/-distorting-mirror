package org.xmlrpc.android;

import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class XMLRPCClient extends XMLRPCCommon {
    private HttpClient client;
    private HttpParams httpParams;
    private HttpPost postMethod;

    public XMLRPCClient(URI uri) {
        this.postMethod = new HttpPost(uri);
        this.postMethod.addHeader(HttpRequest.HEADER_CONTENT_TYPE, "text/xml");
        this.httpParams = this.postMethod.getParams();
        HttpProtocolParams.setUseExpectContinue(this.httpParams, false);
        this.client = new DefaultHttpClient();
    }

    public XMLRPCClient(String url) {
        this(URI.create(url));
    }

    public XMLRPCClient(URL url) {
        this(URI.create(url.toExternalForm()));
    }

    public XMLRPCClient(URI uri, String username, String password) {
        this(uri);
        ((DefaultHttpClient) this.client).getCredentialsProvider().setCredentials(new AuthScope(uri.getHost(), uri.getPort(), AuthScope.ANY_REALM), new UsernamePasswordCredentials(username, password));
    }

    public XMLRPCClient(String url, String username, String password) {
        this(URI.create(url), username, password);
    }

    public XMLRPCClient(URL url, String username, String password) {
        this(URI.create(url.toExternalForm()), username, password);
    }

    public void setBasicAuthentication(String username, String password) {
        ((DefaultHttpClient) this.client).getCredentialsProvider().setCredentials(new AuthScope(this.postMethod.getURI().getHost(), this.postMethod.getURI().getPort(), AuthScope.ANY_REALM), new UsernamePasswordCredentials(username, password));
    }

    public Object callEx(String method, Object[] params) throws XMLRPCException {
        try {
            this.postMethod.setEntity(new StringEntity(methodCall(method, params)));
            HttpResponse response = this.client.execute(this.postMethod);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new XMLRPCException("HTTP status code: " + statusCode + " != " + 200);
            }
            XmlPullParser pullParser = XmlPullParserFactory.newInstance().newPullParser();
            HttpEntity entity = response.getEntity();
            pullParser.setInput(new InputStreamReader(new BufferedInputStream(entity.getContent())));
            pullParser.nextTag();
            pullParser.require(2, null, "methodResponse");
            pullParser.nextTag();
            String tag = pullParser.getName();
            if (tag.equals("params")) {
                pullParser.nextTag();
                pullParser.require(2, null, "param");
                pullParser.nextTag();
                Object obj = this.iXMLRPCSerializer.deserialize(pullParser);
                entity.consumeContent();
                return obj;
            } else if (tag.equals("fault")) {
                pullParser.nextTag();
                Map<String, Object> map = (Map) this.iXMLRPCSerializer.deserialize(pullParser);
                String faultString = (String) map.get("faultString");
                int faultCode = ((Integer) map.get("faultCode")).intValue();
                entity.consumeContent();
                throw new XMLRPCFault(faultString, faultCode);
            } else {
                entity.consumeContent();
                throw new XMLRPCException("Bad tag <" + tag + "> in XMLRPC response - neither <params> nor <fault>");
            }
        } catch (XMLRPCException e) {
            throw e;
        } catch (Exception e2) {
            e2.printStackTrace();
            throw new XMLRPCException(e2);
        }
    }

    private String methodCall(String method, Object[] params) throws IllegalArgumentException, IllegalStateException, IOException {
        StringWriter bodyWriter = new StringWriter();
        this.serializer.setOutput(bodyWriter);
        this.serializer.startDocument(null, null);
        this.serializer.startTag(null, "methodCall");
        this.serializer.startTag(null, "methodName").text(method).endTag(null, "methodName");
        serializeParams(params);
        this.serializer.endTag(null, "methodCall");
        this.serializer.endDocument();
        return bodyWriter.toString();
    }

    public Object call(String method) throws XMLRPCException {
        return callEx(method, null);
    }

    public Object call(String method, Object p0) throws XMLRPCException {
        return callEx(method, new Object[]{p0});
    }

    public Object call(String method, Object p0, Object p1) throws XMLRPCException {
        return callEx(method, new Object[]{p0, p1});
    }

    public Object call(String method, Object p0, Object p1, Object p2) throws XMLRPCException {
        return callEx(method, new Object[]{p0, p1, p2});
    }

    public Object call(String method, Object p0, Object p1, Object p2, Object p3) throws XMLRPCException {
        return callEx(method, new Object[]{p0, p1, p2, p3});
    }

    public Object call(String method, Object p0, Object p1, Object p2, Object p3, Object p4) throws XMLRPCException {
        return callEx(method, new Object[]{p0, p1, p2, p3, p4});
    }

    public Object call(String method, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) throws XMLRPCException {
        return callEx(method, new Object[]{p0, p1, p2, p3, p4, p5});
    }

    public Object call(String method, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) throws XMLRPCException {
        return callEx(method, new Object[]{p0, p1, p2, p3, p4, p5, p6});
    }

    public Object call(String method, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) throws XMLRPCException {
        return callEx(method, new Object[]{p0, p1, p2, p3, p4, p5, p6, p7});
    }
}
