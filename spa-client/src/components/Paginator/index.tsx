import React, {useState} from 'react';


type PaginatorProps =  {
    totalPages:number,
    actualPage:number,
    callback:Function
}

const Paginator:React.FC<PaginatorProps> = ({totalPages, actualPage, callback}) => {

    let [pageActive, setPageActive] = useState<number>(actualPage);

    async function prevPage(){
        //throw event to get new offers
        await callback(pageActive - 1);
        setPageActive(pageActive - 1);

    }
    async function nextPage(){
        await callback(pageActive + 1);
        setPageActive(pageActive + 1)
    }

    return (
        <div className = {"flex mx-auto pt-4"}>
            {pageActive === 1 && <button disabled className="cursor-not-allowed bg-gray-200 p-3 rounded-lg font-roboto font-bold w-25">Previous</button> }
            {pageActive !== 1 && <button onClick={prevPage} className="bg-gray-200 p-3 rounded-lg font-roboto font-bold w-25">Previous</button> }
            <div className="flex my-auto mx-3 justify-evenly">
                <h4 className="mx-1 font-bold">{pageActive}</h4>
                <h4 className="mx-1">of</h4>
                <h4 className="mx-1 font-bold"> {totalPages}</h4>
            </div>

            {pageActive === totalPages && <button disabled className="cursor-not-allowed  bg-gray-200 p-3 rounded-lg font-roboto font-bold w-25">Next</button> }
            {pageActive !== totalPages && <button onClick={nextPage} className="bg-gray-200 p-3 rounded-lg font-roboto font-bold w-25">Next</button> }
        </div>
    );
};

export default Paginator;