package cn.panda.game.pandastore.tool;

import android.content.Context;
import com.migu.video.components.glide.Glide;
import com.migu.video.components.glide.GlideBuilder;
import com.migu.video.components.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.migu.video.components.glide.load.engine.cache.LruResourceCache;
import com.migu.video.components.glide.module.MGSVGlideModule;

public class MyAppGlideModule implements MGSVGlideModule
{
    @Override
    public void applyOptions (Context context, GlideBuilder glideBuilder)
    {
        int memoryCacheSizeBytes = 1024 * 1024 * 20; // 20mb
        int diskCacheSizeBytes = 1024 * 1024 * 100;  //100 MB
        glideBuilder.setMemoryCache(new LruResourceCache (memoryCacheSizeBytes)).setDiskCache(new InternalCacheDiskCacheFactory (context, diskCacheSizeBytes));
    }

    @Override
    public void registerComponents (Context context, Glide glide) {

    }

}
