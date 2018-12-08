package cn.panda.game.pandastore.tool;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

public class MyAppGlideModule implements GlideModule
{
    @Override
    public void applyOptions (Context context, GlideBuilder glideBuilder)
    {
        int memoryCacheSizeBytes = 1024 * 1024 * 20; // 20mb
        int diskCacheSizeBytes = 1024 * 1024 * 100;  //100 MB
        glideBuilder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes)).setDiskCache(new InternalCacheDiskCacheFactory(context, diskCacheSizeBytes));
    }

    @Override
    public void registerComponents (Context context, Glide glide) {

    }

}
