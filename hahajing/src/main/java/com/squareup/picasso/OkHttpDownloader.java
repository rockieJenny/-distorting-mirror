package com.squareup.picasso;

import android.content.Context;
import android.net.Uri;
import com.inmobi.monetization.internal.Constants;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;
import com.squareup.picasso.Downloader.Response;
import com.squareup.picasso.Downloader.ResponseException;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class OkHttpDownloader implements Downloader {
    static final String RESPONSE_SOURCE_ANDROID = "X-Android-Response-Source";
    static final String RESPONSE_SOURCE_OKHTTP = "OkHttp-Response-Source";
    private final OkUrlFactory urlFactory;

    public OkHttpDownloader(Context context) {
        this(Utils.createDefaultCacheDir(context));
    }

    public OkHttpDownloader(File cacheDir) {
        this(cacheDir, Utils.calculateDiskCacheSize(cacheDir));
    }

    public OkHttpDownloader(Context context, long maxSize) {
        this(Utils.createDefaultCacheDir(context), maxSize);
    }

    public OkHttpDownloader(File cacheDir, long maxSize) {
        this(new OkHttpClient());
        try {
            this.urlFactory.client().setCache(new Cache(cacheDir, maxSize));
        } catch (IOException e) {
        }
    }

    public OkHttpDownloader(OkHttpClient client) {
        this.urlFactory = new OkUrlFactory(client);
    }

    protected HttpURLConnection openConnection(Uri uri) throws IOException {
        HttpURLConnection connection = this.urlFactory.open(new URL(uri.toString()));
        connection.setConnectTimeout(Constants.HTTP_TIMEOUT);
        connection.setReadTimeout(20000);
        return connection;
    }

    protected OkHttpClient getClient() {
        return this.urlFactory.client();
    }

    public Response load(Uri uri, boolean localCacheOnly) throws IOException {
        HttpURLConnection connection = openConnection(uri);
        connection.setUseCaches(true);
        if (localCacheOnly) {
            connection.setRequestProperty(HttpRequest.HEADER_CACHE_CONTROL, "only-if-cached,max-age=2147483647");
        }
        int responseCode = connection.getResponseCode();
        if (responseCode >= 300) {
            connection.disconnect();
            throw new ResponseException(responseCode + " " + connection.getResponseMessage());
        }
        String responseSource = connection.getHeaderField(RESPONSE_SOURCE_OKHTTP);
        if (responseSource == null) {
            responseSource = connection.getHeaderField(RESPONSE_SOURCE_ANDROID);
        }
        long contentLength = (long) connection.getHeaderFieldInt(HttpRequest.HEADER_CONTENT_LENGTH, -1);
        return new Response(connection.getInputStream(), Utils.parseResponseSourceHeader(responseSource), contentLength);
    }

    public void shutdown() {
        Cache cache = this.urlFactory.client().getCache();
        if (cache != null) {
            try {
                cache.close();
            } catch (IOException e) {
            }
        }
    }
}
