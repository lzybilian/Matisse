package com.zhihu.matisse.sample;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.zhihu.matisse.engine.ImageEngine;

public class GlideWebviewSubEngine implements ImageEngine {

    @Override
    public void loadThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
        String path=getPath(context, uri);
        Glide.with(context)
                .load(TextUtils.isEmpty(path) ? uri : path)
                .placeholder(placeholder)
                .override(resize, resize)
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void loadGifThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView,
                                 Uri uri) {
        String path=getPath(context, uri);
        Glide.with(context)
                .load(TextUtils.isEmpty(path) ? uri : path)
                .placeholder(placeholder)
                .override(resize, resize)
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void loadImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        String path=getPath(context, uri);
        Glide.with(context)
                .load(TextUtils.isEmpty(path) ? uri : path)
                .priority(Priority.NORMAL)
                .fitCenter()
                .into(imageView);
    }

    @Override
    public void loadGifImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        String path=getPath(context, uri);
        Glide.with(context)
                .load(TextUtils.isEmpty(path) ? uri : path)
                .priority(Priority.NORMAL)
                .into(imageView);
    }

    @Override
    public boolean supportAnimatedGif() {
        return true;
    }

    /**
     * 转换成路径
     * @param context
     * @param uri
     * @return
     */
    private String getPath(Context context, Uri uri) {
        String res = "";
        String scheme = uri.getScheme();
        if (scheme.equals(ContentResolver.SCHEME_FILE)) {
            res = uri.getPath();
        } else {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    res = cursor.getString(column_index);
                }
                cursor.close();
            }
        }
        return res;
    }

}