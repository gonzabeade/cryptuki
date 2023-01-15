import {PaginatorPropsValues} from "../../types/PaginatedResults";

export type Link = {
    rel: string;
    href: string;
    page: number;
}

/**
 * first and last are always present
 * next is present when available
 * prev is present when available
 * PREV, NEXT, FIRST, LAST
 * @param link
 */


export function getLinkHeaders(link:string):Link[] {
    const links:string[] = link.split(',');
    const linkHeaders:Link[] = [];

    if(links.length >= 2){

        linkHeaders.push({
            rel: "first",
            href: links[links.length - 2],
            page: Number(getFirstPage(links[links.length - 2]))
        })
        linkHeaders.push({
            rel: "last",
            href: links[links.length - 1],
            page: Number(getLastPage(links[links.length - 1]))
        })


        if(link.includes("rel=\"next\"")){
            linkHeaders.push({
                rel: "next",
                href: links[links.length - 3],
                page: Number(getNextPage(links[links.length - 3]))
            })
        }

        if(link.includes("rel=\"prev\"")){
            linkHeaders.push({
                rel: "prev",
                href: links[links.length - 4],
                page: Number(getPrevPage(links[links.length - 4]))
            })
        }
    }

    return linkHeaders;
}

function getLastPage(link:string):string{
    return link.substring(0,link.indexOf("rel=\"last\""));
}
function getPrevPage(link:string):string {
    return link.substring(0,link.indexOf("rel=\"prev\""));
}
function getNextPage(link:string):string{
    return link.substring(0,link.indexOf("rel=\"next\""));
}
function getFirstPage(link:string):string{
    return link.substring(0,link.indexOf("rel=\"first\""));
}

export function getPaginatorProps(link:Link[]):PaginatorPropsValues {
    if (link && link.length <= 2) {
        return {
            actualPage: 1,
            totalPages: 1,
            nextUri: "",
            prevUri: ""
        }
    }
    //if we have 3 components, we have either next or prev
    if (link.length === 3) {
        if (link[1].rel === "next") {
            return {
                actualPage: link[0].page,
                totalPages: link[2].page,
                nextUri: link[1].href,
                prevUri: ""
            }
        } else {
            return {
                actualPage: link[2].page,
                totalPages: link[2].page,
                nextUri: "",
                prevUri: link[1].href
            }
        }
    } else {
        return {
            actualPage: link[1].page + 1,
            totalPages: link[3].page,
            nextUri: link[2].href,
            prevUri: link[1].href
        }
    }
}
