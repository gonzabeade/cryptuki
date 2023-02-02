import React, {useEffect, useState} from 'react';
import {PaginatorPropsValues} from "../../types/PaginatedResults";
import i18n from "../../i18n";


type PaginatorProps =  {
    paginatorProps:PaginatorPropsValues,
    callback:Function,
}

const Paginator:React.FC<PaginatorProps> = ({paginatorProps, callback }) => {


    async function prevPage(){
        //throw event to get new offers
        await callback(paginatorProps.prevUri);

    }
    async function nextPage(){
        await callback(paginatorProps.nextUri);
    }

    return (
        <div className = {"flex mx-auto pt-4"}>
            {paginatorProps.actualPage + 1 === 1 && <button disabled className="cursor-not-allowed bg-gray-200 p-3 rounded-lg font-roboto font-bold w-25">{i18n.t('previous')}</button> }
            {paginatorProps.actualPage + 1 !== 1 && <button onClick={prevPage} className="bg-gray-200 p-3 rounded-lg font-roboto font-bold w-25">{i18n.t('previous')}</button> }
            <div className="flex my-auto mx-3 justify-evenly">
                <h4 className="mx-1 font-bold">{paginatorProps.actualPage + 1}</h4>
                <h4 className="mx-1">{i18n.t('ofPages')}</h4>
                <h4 className="mx-1 font-bold"> {paginatorProps.totalPages + 1}</h4>
            </div>

            {paginatorProps.actualPage + 1 === paginatorProps.totalPages + 1 && <button disabled className="cursor-not-allowed  bg-gray-200 p-3 rounded-lg font-roboto font-bold w-25">{i18n.t('next')}</button> }
            {paginatorProps.actualPage + 1 !== paginatorProps.totalPages + 1&& <button onClick={nextPage} className="bg-gray-200 p-3 rounded-lg font-roboto font-bold w-25">{i18n.t('next')}</button> }
        </div>
    );
};

export default Paginator;