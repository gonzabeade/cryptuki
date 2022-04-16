package ar.edu.itba.paw.service;

public interface PaginatorService<T>{
    boolean validatePage(int pageNumber);
    int getPageCount();
    Iterable<T> getPagedObjects(int pageNumber);
}
