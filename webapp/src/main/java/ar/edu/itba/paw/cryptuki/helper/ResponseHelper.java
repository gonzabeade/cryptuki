package ar.edu.itba.paw.cryptuki.helper;


import javax.ws.rs.core.*;

import static java.lang.Math.ceil;

public class ResponseHelper {

    private ResponseHelper() {
        // Helper class
    }


    // TODO: decide whether it is the best way of not repeating code
    // TODO: decide whether the pageSize should be constantly repeated
    public static Response.ResponseBuilder genLinks(Response.ResponseBuilder rb, UriInfo uriInfo, int page, int pageSize, long totalCount) {

        int maxPage = (int) ceil((float) totalCount / pageSize) - 1;
        if ( page > 0)
            rb.link(uriInfo.getAbsolutePathBuilder().queryParam("page", page-1).queryParam("per_page", pageSize).build(), "prev");

        if ( page < maxPage)
            rb.link(uriInfo.getAbsolutePathBuilder().queryParam("page", page+1).queryParam("per_page", pageSize).build(), "next");

        // TODO: ask whether should add last and first if already in last and first
        rb.link(uriInfo.getAbsolutePathBuilder().queryParam("page", 0).queryParam("per_page", pageSize).build(), "first");
        rb.link(uriInfo.getAbsolutePathBuilder().queryParam("page", maxPage).queryParam("per_page", pageSize).build(), "last");
        return rb;
    }


}
