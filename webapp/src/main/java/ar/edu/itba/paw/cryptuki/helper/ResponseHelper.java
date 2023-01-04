package ar.edu.itba.paw.cryptuki.helper;


import javax.ws.rs.core.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.ceil;

public class ResponseHelper {

    public static final int MAX_AGE = 10000;

    private ResponseHelper() {
        // Helper class
    }

    public static void genLinks(Response.ResponseBuilder rb, UriInfo uriInfo, int page, int pageSize, long totalCount) {

        int maxPage = (int) ceil((float) totalCount / pageSize) - 1;

        if ( page > 0)
            rb.link(uriInfo.getRequestUriBuilder().replaceQueryParam("page", page-1).build(), "prev");

        if ( page < maxPage)
            rb.link(uriInfo.getRequestUriBuilder().replaceQueryParam("page", page+1).build(), "next");

        rb.link(uriInfo.getRequestUriBuilder().replaceQueryParam("page", 0).build(), "first");
        rb.link(uriInfo.getRequestUriBuilder().replaceQueryParam("page", maxPage).build(), "last");
    }

    public static void setUnconditionalCache(Response.ResponseBuilder responseBuilder) {
        final CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(MAX_AGE);
        responseBuilder.cacheControl(cacheControl);
    }


}
