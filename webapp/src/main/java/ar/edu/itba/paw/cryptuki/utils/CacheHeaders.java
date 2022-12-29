package ar.edu.itba.paw.cryptuki.utils;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;
import java.util.concurrent.TimeUnit;

public class CacheHeaders {

    private static Integer MAX_AGE = 60*60*24; // in seconds.
    public static void setUnconditionalCache(Response.ResponseBuilder responseBuilder) {
        final CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(MAX_AGE);
        cacheControl.setNoTransform(false);
        responseBuilder.cacheControl(cacheControl);
    }
}
