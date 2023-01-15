import {Link} from "../common/utils/utils";

export interface PaginatedResults<T> {
    items: T[];
    paginatorProps: PaginatorPropsValues;
}

export type PaginatorPropsValues = {
    actualPage:number,
    totalPages:number,
    nextUri:string,
    prevUri:string
}