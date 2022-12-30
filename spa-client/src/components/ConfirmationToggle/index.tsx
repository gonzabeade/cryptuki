import React from 'react';

type ConfirmationToggleProps = {
    title:string
}
const ConfirmationToggle :React.FC<ConfirmationToggleProps>= ({title}) => {
    function hideConfirmation(){
        document.getElementById("confirmationMessage")?.classList.add("hidden")
    }
    return (
        <div className="flex flex-row mt-10" id="confirmationMessage">
            <div className="mx-auto bg-ngreen rounded-lg flex flex-row p-3">
                <svg xmlns="http://www.w3.org/2000/svg" className="h-10 w-10 mx-auto my-auto" fill="none"
                     viewBox="0 0 24 24" stroke="white" strokeWidth="2">
                    <path strokeLinecap="round" strokeLinejoin="round" d="M5 13l4 4L19 7"/>
                </svg>
                <p className="text-white p-3 text-xl text-white">{title}</p>
                <div onClick={()=>hideConfirmation()} className="hover:cursor-pointer my-auto">
                    <svg xmlns="http://www.w3.org/2000/svg" className="h-10 w-10 " fill="none" viewBox="0 0 24 24"
                         stroke="white" strokeWidth="2">
                        <path strokeLinecap="round" strokeLinejoin="round"
                              d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"/>
                    </svg>
                </div>
            </div>
        </div>
    );
};

export default ConfirmationToggle;