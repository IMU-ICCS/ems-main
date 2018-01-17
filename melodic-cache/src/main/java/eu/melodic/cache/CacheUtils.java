package eu.melodic.cache;

import lombok.NonNull;

/**
 * Created by pszkup on 16.01.18.
 */
public class CacheUtils {

    public static String createCacheKey(@NonNull String cdoResourcePath){
        return cdoResourcePath.substring(cdoResourcePath.indexOf("/") + 1);
    }

}
