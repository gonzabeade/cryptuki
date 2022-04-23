package ar.edu.itba.paw.service;

public interface Paginator<T>{
    boolean isPageValid(int pageNumber);
    int getPageCount();
    Iterable<T> getPagedObjects(int pageNumber);
}
