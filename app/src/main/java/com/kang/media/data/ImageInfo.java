package com.kang.media.data;

import android.net.Uri;

public class ImageInfo {
    private String name = "null";
    private Uri uri;
    private String path = "null";
    private long orid;

    public ImageInfo(String name, Uri uri, String path, long orid) {
        this.name = name;
        this.uri = uri;
        this.path = path;
        this.orid = orid;
    }

    public String getName() {
        return name;
    }

    public Uri getUri() {
        return uri;
    }

    public String getPath() {
        return path;
    }

    public long getOrid() {
        return orid;
    }
}
