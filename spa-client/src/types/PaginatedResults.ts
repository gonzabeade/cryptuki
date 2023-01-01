export interface PaginatedResults<T> {
    items: T[];
    page: number;
    totalPages: number;
}