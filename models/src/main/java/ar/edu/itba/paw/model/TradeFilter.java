package ar.edu.itba.paw.model;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;

public class TradeFilter {

    /* Attributes that discriminate trades */
    private final Collection<String> cryptoCodes = new HashSet<>();
    private final Collection<Integer> restrictedToIds = new HashSet<>();
    private final Collection<String> restrictedToUsernames = new HashSet<>();
    private final Collection<Location> locations = EnumSet.noneOf(Location.class);
    private Collection<TradeStatus> status = EnumSet.noneOf(TradeStatus.class);

    /* Attributes that determine paging */
    private int page = 0;
    private int pageSize = 1;

    /****/


    public TradeFilter withTradeStatus(TradeStatus offerStatus) {
        if (offerStatus != null)
            status.add(offerStatus);
        return this;
    }

    public TradeFilter withTradeStatus(String tradeStatus) {
        if (tradeStatus != null)
            status.add(TradeStatus.valueOf(tradeStatus));
        return this;
    }

    public TradeFilter withLocation(String location) {
        if (location != null)
            locations.add(Location.valueOf(location));
        return this;
    }

    public TradeFilter withLocation(Location location) {
        if (location != null)
            locations.add(location);
        return this;
    }

    public TradeFilter withCryptoCode(String cryptoCode) {
        if (cryptoCode != null)
            cryptoCodes.add(cryptoCode);
        return this;
    }

    public TradeFilter restrictedToUsername(String username) {
        if (username != null)
            restrictedToUsernames.add(username);
        return this;
    }

    public TradeFilter restrictedToId(Integer offerId) {
        if  (offerId != null)
            restrictedToIds.add(offerId);
        return this;
    }

    public TradeFilter withPage(int page) {
        this.page = page;
        return this;
    }

    public TradeFilter withPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    /****/

    public Collection<String> getCryptoCodes() {
        return Collections.unmodifiableCollection(cryptoCodes);
    }

    public Collection<Integer> getRestrictedToIds() {
        return Collections.unmodifiableCollection(restrictedToIds);
    }

    public Collection<String> getRestrictedToUsernames() {
        return Collections.unmodifiableCollection(restrictedToUsernames);
    }

    public Collection<Location> getLocations() {
        return Collections.unmodifiableCollection(locations);
    }

    public Collection<TradeStatus> getStatus() {
        return Collections.unmodifiableCollection(status);
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

}
