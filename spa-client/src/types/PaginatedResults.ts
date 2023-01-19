export interface PaginatedResults<T> {
    items: T[];
    paginatorProps?: PaginatorPropsValues;
    params?:URLSearchParams;
}

export type PaginatorPropsValues = {
    actualPage:number,
    totalPages:number,
    nextUri:string,
    prevUri:string
}
export type Link = {
    rel: string;
    href: string;
    page: number;
}