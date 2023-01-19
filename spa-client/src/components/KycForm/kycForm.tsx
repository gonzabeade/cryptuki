import React, {useEffect, useState} from 'react';
import {toast} from "react-toastify";
import {useForm} from "react-hook-form";
import {UploadFormValues} from "../UploadForm/uploadForm";

export interface UploadKycValues {
    names:string,
    surnames:string,
    emissionCountry:string,
    documentNumber:number,
    idType:string,
    idPictures:FileList,
    facePictures:FileList
}

const KycForm = () => {

    //Form
    const { register, handleSubmit, formState: { errors }, getValues } = useForm<UploadKycValues>();

    function onSubmit(data:UploadKycValues) {
        console.log(data);
    }

    //TODO HTML
    //-Mirar como hacer para que el form se expanda a toda la pantalla
    //-Mirar como poner el enctype="multipart/form-data" en los inputs de archivos y si es necesario

    return (
        <div className="flex flex-row mx-auto">
            <form className="flex flex-col w-1/2 mt-20 mb-10 py-12 px-4 justify-start mx-auto rounded-lg bg-stormd/[0.9] border-2 border-polard" onSubmit={handleSubmit(onSubmit)}>
                <div className="flex">
                    <div className="flex flex-col mx-auto">
                        <h1 className="font-sans font-semibold text-polar text-2xl text-center">
                            Verify your Identity
                        </h1>
                        <h2 className="text-start text-lg font-semibold font-sans text-polar mt-8">
                            Each seller must verify it's identity. We will get back to you by mail after verifying the information in this form
                        </h2>
                        <h2 className="text-start text-xl font-semibold font-sans text-polar underline mt-8">
                            Section 1 : Passport/ID information
                        </h2>
                        <h2 className="text-start text-lg font-semibold font-sans text-polar mt-2">
                            Input the personal data regarding this passport/id.
                        </h2>
                        <h2 className="text-start text-lg font-semibold font-sans text-polar mt-2">
                            If your country is not present on this list, we can't verify your information yet!
                        </h2>
                        <div className="flex flex-col mx-5 mt-4 items-start">
                            <div className="flex flex-col mr-10 my-4 w-1/2">
                                <label className="text-start text-xl font-bold font-sans text-polar my-2">Names</label>
                                <input className="p-3 rounded-lg shadow" placeholder="e.g. Pedro Martin"
                                       {...register("names",{
                                           required: "You must enter a name",
                                           minLength: {value: 1, message: "Passport/id number must contain at least 1 character"},
                                           maxLength: {value: 140, message: "Passport/id number must contain no more than 140 characters"}
                                       })}
                                />
                                {errors && errors.names && <p className="text-red-600 mx-auto mt-2">{errors.names.message}</p> }
                            </div>
                            <div className="flex flex-col mr-10 my-4 w-1/2">
                                <label className="text-start text-xl font-bold font-sans text-polar my-2">Surnames</label>
                                <input className="p-3 rounded-lg shadow" placeholder="e.g. Juarez"
                                       {...register("surnames", {
                                           required: "You must enter a surname",
                                           minLength: {value: 1, message: "Passport/id number must contain at least 1 character"},
                                           maxLength: {value: 140, message: "Passport/id number must contain no more than 140 characters"}
                                       })}
                                />
                                {errors && errors.surnames && <p className="text-red-600 mx-auto mt-2">{errors.surnames.message}</p> }
                            </div>
                            <div className="flex flex-col mr-10 my-4 w-1/2">
                                <label className="text-start text-xl font-bold font-sans text-polar my-2">Emission country</label>
                                <select className="rounded-lg p-3 bg-white" {...register("emissionCountry")}>
                                    <option value="ARG">Argentina</option>
                                    <option value="CHL">Chile</option>
                                    <option value="URY">Uruguay</option>
                                </select>
                            </div>
                        </div>
                        <div className="flex flex-row mx-5 mt-4 items-end">
                            <div className="flex flex-col mr-10 my-4 w-1/2">
                                <label className="text-start text-xl font-bold font-sans text-polar my-2">
                                    Document Number
                                </label>
                                <input type="text" placeholder="e.g. 45089768" className="rounded-lg p-3"
                                       {...register("documentNumber", {
                                           required: "You must enter a passport/id number",
                                           minLength: {value: 1, message: "Passport/id number must contain at least 1 character"},
                                           maxLength: {value: 140, message: "Passport/id number must contain no more than 140 characters"}
                                       })}
                                />
                                {errors && errors.documentNumber && <p className="text-red-600 mx-auto mt-2">{errors.documentNumber.message}</p> }
                            </div>
                            <div className="flex flex-col mr-10 my-4 w-1/2">
                                <label className="text-start text-xl font-bold font-sans text-polar my-2">
                                    Passport/ID Type
                                </label>
                                <select className="rounded-lg p-3 bg-white" {...register("idType")}>
                                    <option value="ID">National Id</option>
                                    <option value="PASSPORT">Passport</option>
                                </select>
                            </div>
                        </div>
                        <div className="flex flex-col mr-18 my-4 w-1/2">
                            <label className="text-start text-xl font-bold font-sans text-polar my-2">
                               Passport/ID picture
                            </label>
                            <input type="file" className="rounded-lg p-3" accept="image/png, image/gif, image/jpeg"
                                   {...register("idPictures", {
                                       validate:{
                                           validateExistance: fileList => fileList.length == 1 && fileList[0] != null|| 'File should not be empty',
                                           validateSize: fileList => fileList[0].size > 0 && fileList[0].size < 10000000 || 'File should be smaller than 10 MB'
                                       }}
                                   )}
                            />
                            {errors && errors.idPictures && <p className="text-red-600 mx-auto mt-2">{errors.idPictures.message}</p> }
                        </div>
                        <h2 className="text-start text-xl font-semibold font-sans text-polar underline mt-8">
                            Section 2: Face and Picture data validation
                        </h2>
                        <h2 className="text-start text-lg font-semibold font-sans text-polar mt-2">
                            For safety purposes, to validate you're using your password,  we require you to have a picture taken of yourself, showing half of your body and complete face holding your document.
                        </h2>
                        <div className="flex flex-row mx-5 mt-4 items-center">
                           <div className="flex flex-col my-10 mx-auto">
                               <div className="flex flex-col mx-5 my-4 px-16">
                                   <label className="text-center text-xl font-bold font-sans text-polar my-2">
                                       Upload picture
                                   </label>
                                   <input type="file" className="rounded-lg p-3" accept="image/png, image/gif, image/jpeg"
                                   {...register("facePictures", {
                                       validate:{
                                           validateExistance: fileList => fileList.length == 1 && fileList[0] != null|| 'File should not be empty',
                                           validateSize: fileList => fileList[0].size > 0 && fileList[0].size < 10000000 || 'File should be smaller than 10 MB'
                                       }}
                                   )}
                                  />
                                   {errors && errors.facePictures && <p className="text-red-600 mx-auto mt-2">{errors.facePictures.message}</p>}
                               </div>
                           </div>
                        </div>
                        <div className="flex flex-row w-full justify-center mt-10">
                            <button type="submit" className="w-1/5 rounded-lg bg-frostl py-3 px-5 text-white cursor-pointer shadow-lg">
                                Send
                            </button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    );
};

export default KycForm;