package ar.edu.itba.paw.model;

import java.time.LocalDate;
import java.util.*;


public class ComplainFilter {

    /* Attributes that determine paging */
    private int page = 0;
    private int pageSize = 1;

    /* Attributes that discriminate complains */
    private Collection<String> restrictedToComplainerUsernames = new HashSet<>();
    private Collection<String> restrictedToModeratorUsernames = new HashSet<>();
    private Collection<Integer> restrictedToComplainIds = new HashSet<>();
    private Collection<Integer> restrictedToTradeIds = new HashSet<>();
    private Collection<Integer> restrictedToOfferIds = new HashSet<>();
    private Collection<ComplainStatus> complainStatus = EnumSet.noneOf(ComplainStatus.class);
    private LocalDate from;
    private LocalDate to;

    public ComplainFilter restrictedToComplainerUsername(String username) {
        if (username != null)
            restrictedToComplainerUsernames.add(username);
        return this;
    }

    public ComplainFilter restrictedToComplainerUsernames(Collection<String> usernames) {
        if (usernames != null)
            restrictedToComplainerUsernames.addAll(usernames);
        return this;
    }

    public ComplainFilter restrictedToModeratorUsername(String username) {
        if (username != null)
            restrictedToModeratorUsernames.add(username);
        return this;
    }

    public ComplainFilter restrictedToModeratorUsernames(Collection<String> usernames) {
        if (usernames != null)
            restrictedToModeratorUsernames.addAll(usernames);
        return this;
    }

    public ComplainFilter restrictedToComplainId(Integer id) {
        if (id != null)
            restrictedToComplainIds.add(id);
        return this;
    }

    public ComplainFilter restrictedToTradeId(Integer id) {
        if (id != null)
            restrictedToTradeIds.add(id);
        return this;
    }

    public ComplainFilter restrictedToOfferId(Integer id) {
        if (id != null)
            restrictedToOfferIds.add(id);
        return this;
    }

    public ComplainFilter from(LocalDate from) {
        this.from = from;
        return this;
    }

    public ComplainFilter to(LocalDate to) {
        this.to = to;
        return this;
    }

    public ComplainFilter withComplainStatus(ComplainStatus complainStatus) {
        this.complainStatus.add(complainStatus);
        return this;
    }

    public ComplainFilter withComplainStatus(String complainStatus) {
        this.complainStatus.add(ComplainStatus.valueOf(complainStatus));
        return this;
    }

    public ComplainFilter withPage(int page) {
        this.page = page;
        return this;
    }

    public ComplainFilter withPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }
    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public Collection<String> getRestrictedToComplainerUsernames() {
        return Collections.unmodifiableCollection(restrictedToComplainerUsernames);
    }

    public Collection<String> getRestrictedToModeratorUsernames() {
        return Collections.unmodifiableCollection(restrictedToModeratorUsernames);
    }

    public Collection<Integer> getRestrictedToComplainIds() {
        return Collections.unmodifiableCollection(restrictedToComplainIds);
    }

    public Collection<Integer> getRestrictedToTradeIds() {
        return Collections.unmodifiableCollection(restrictedToTradeIds);
    }

    public Collection<Integer> getRestrictedToOfferIds() {
        return Collections.unmodifiableCollection(restrictedToOfferIds);
    }

    public Collection<ComplainStatus> getComplainStatus() {
        return Collections.unmodifiableCollection(complainStatus);
    }

    public Optional<LocalDate> getFrom() {
        return Optional.ofNullable(from);
    }

    public Optional<LocalDate> getTo() {
        return Optional.ofNullable(to);
    }
}