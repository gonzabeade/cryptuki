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
 * FIRST, LAST, PREV, NEXT
 * @param link
 */


export function getLinkHeaders(link:string):Link[] {
    const links:string[] = link.split(',');
    const linkHeaders:Link[] = [];


    if(links.length >= 2){

        linkHeaders.push({
            rel: "first",
            href: links[links.length-2].substring(links[links.length-2].indexOf("<")+1, links[links.length-2].indexOf(">")),
            page: Number(getPage(links[links.length - 2]))
        })
        linkHeaders.push({
            rel: "last",
            href: links[links.length-1].substring(links[links.length-1].indexOf("<")+1, links[links.length-1].indexOf(">")),
            page: Number(getPage(links[links.length-1]))
        })

        if(links.length <= 3){
            if(link.includes("rel=\"next\"")){
                linkHeaders.push({
                    rel: "next",
                    href: links[0].substring(links[0].indexOf("<")+1, links[0].indexOf(">")),
                    page: Number(getPage(links[0]))
                })
            }else{
                if(link.includes("rel=\"prev\"")){
                    linkHeaders.push({
                        rel: "prev",
                        href: links[0].substring(links[0].indexOf("<")+1, links[0].indexOf(">")),
                        page: Number(getPage(links[0]))
                    })
                }
            }
        }else{
            linkHeaders.push({
                rel: "next",
                href: links[1].substring(links[1].indexOf("<")+1, links[1].indexOf(">")),
                page: Number(getPage(links[1]))
            })
            linkHeaders.push({
                rel: "prev",
                href: links[0].substring(links[0].indexOf("<")+1, links[0].indexOf(">")),
                page: Number(getPage(links[0]))
            })
        }
    }
    return linkHeaders;
}

function getPage(link:string):string|null{
  return new URLSearchParams(link).get("page");
}

export function getPaginatorProps(link:Link[]):PaginatorPropsValues {
    if (link && link.length <= 2) {
        return {
            actualPage: 0,
            totalPages: 0,
            nextUri: "",
            prevUri: ""
        }
    }
    //if we have 3 components, we have either next or prev

    if (link.length === 3) {

        if (link[2].rel === "next") {
            return {
                actualPage: link[0].page,
                totalPages: link[1].page ,
                nextUri: link[2].href,
                prevUri: ""
            }
        } else {
            return {
                actualPage: link[1].page ,
                totalPages: link[1].page ,
                nextUri: "",
                prevUri: link[2].href
            }
        }
    } else {
        return {
            actualPage: link[2].page + 1,
            totalPages: link[1].page,
            nextUri: link[3].href,
            prevUri: link[2].href
        }
    }
}
