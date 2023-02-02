package ar.edu.itba.paw.cryptuki.utils;

import ar.edu.itba.paw.cryptuki.annotation.validation.CollectionOfEnum;
import ar.edu.itba.paw.cryptuki.annotation.validation.OneOrTheOther;
import ar.edu.itba.paw.model.TradeStatus;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import java.util.List;

@OneOrTheOther(
        field1 = "buyer",
        field2= "offer"
)
public class TradeBeanParam {

    @QueryParam("page")
    @DefaultValue("0")
    private int page;
    @QueryParam("per_page")
    @DefaultValue("5")
    private int pageSize;
    @QueryParam("status")
    @CollectionOfEnum(enumClass = TradeStatus.class)
    private List<String> status;
    @QueryParam("from_offer")
    private Integer offer;
    @QueryParam("buyer")
    private String buyer;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public Integer getOffer() {
        return offer;
    }

    public void setOffer(Integer offer) {
        this.offer = offer;
    }
}
