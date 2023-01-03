package ar.edu.itba.paw.cryptuki.utils;

import ar.edu.itba.paw.cryptuki.annotation.validation.ValueOfEnum;
import ar.edu.itba.paw.model.ComplainFilter;
import ar.edu.itba.paw.model.ComplainStatus;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import java.time.LocalDate;
import java.util.List;

public class ComplainBeanParam {
    @QueryParam("page")
    @DefaultValue("0")
     private int page;
    @QueryParam("per_page")
    @DefaultValue("5")
    private int pageSize;
    @QueryParam("status")
    @ValueOfEnum(enumClass = ComplainStatus.class)
    private String complainStatus;
    @QueryParam("with_offer")
    private Integer offerId;
    @QueryParam("with_trade")
    private Integer tradeId;
    @QueryParam("complainer")
    private List<String> complainerUsernames;
    @QueryParam("moderator")
    private List<String> moderatorUsernames;
    @QueryParam("from_date")
    private String fromDate;
    @QueryParam("to_date")
    private String toDate;

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

    public String getComplainStatus() {
        return complainStatus;
    }

    public void setComplainStatus(String complainStatus) {
        this.complainStatus = complainStatus;
    }

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

    public Integer getTradeId() {
        return tradeId;
    }

    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
    }

    public List<String> getComplainerUsernames() {
        return complainerUsernames;
    }

    public void setComplainerUsernames(List<String> complainerUsernames) {
        this.complainerUsernames = complainerUsernames;
    }

    public List<String> getModeratorUsernames() {
        return moderatorUsernames;
    }

    public void setModeratorUsernames(List<String> moderatorUsernames) {
        this.moderatorUsernames = moderatorUsernames;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public ComplainFilter toComplainFilter(){
        ComplainFilter complainFilter = new ComplainFilter()
                .withPage(page)
                .withPageSize(pageSize)
                .restrictedToComplainerUsernames(complainerUsernames)
                .restrictedToModeratorUsernames(moderatorUsernames)
                .restrictedToOfferId(offerId)
                .restrictedToTradeId(tradeId);
        if(fromDate != null)
            complainFilter.from(LocalDate.parse(fromDate));
        if(toDate != null )
            complainFilter.to(LocalDate.parse(toDate));
        if(complainStatus!=null)
            complainFilter.withComplainStatus(ComplainStatus.valueOf(complainStatus));
        return complainFilter;
    }

}
