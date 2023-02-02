import React from 'react';
import {useForm} from "react-hook-form";
import useUserService from "../../hooks/useUserService";
import {useNavigate} from "react-router-dom";
import i18n from "../../i18n";

// const PWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,24}$/;
const PWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z]).{8,24}$/;

export type changePasswordForm = {
    password: string;
    repeatPassword: string;
}


const ChangePassword = () => {
    const { register, handleSubmit, formState: { errors } } = useForm<changePasswordForm>();
    const userService = useUserService();
    const navigate = useNavigate();

    async function onSubmit(data:changePasswordForm){
        try{
            console.log(data)
        }catch (e) {

        }

    }


    return (
        <div className="flex mt-10 mb-10">
            <form id="changePasswordForm" onSubmit={handleSubmit(onSubmit)}
                  className=" py-12 px-36 rounded-lg bg-stormd/[0.9] flex flex-col justify-center mx-auto border-2 border-polard"
                  >

                <h2 className="text-center text-4xl font-semibold font-sans text-polar">{i18n.t('changePassword')}</h2>

                <div className="flex flex-col mt-3">
                    <label htmlFor="password"
                           className="text-center text-xl font-bold font-sans text-polar my-2">{i18n.t('password')}</label>

                    <div className="flex flex-col">
                        <input
                            placeholder="Password"
                            type="password"
                            id="password"
                            className="rounded-lg p-3 w-full"
                            {...register("password",{required: true, pattern: {value: PWD_REGEX, message: "Password must contain at least 8 characters, one uppercase, one lowercase, one number and one special character"}})}
                        />
                        {errors && errors.password && <span className="text-red-500">{errors.password.message}</span>}
                    </div>
                </div>
                <div className="flex flex-col mt-3">
                    <label htmlFor="repeatPassword" className="text-center text-xl font-bold font-sans text-polar my-2">{i18n.t('repeatPassword')}</label>

                    <div className="flex flex-col">
                        <input
                            type="password"
                            id="confirm_pwd"
                            placeholder="Repeat new password"
                            className="rounded-lg p-3  w-full"
                            {...register("repeatPassword",{required: true, pattern: {value: PWD_REGEX, message: "Password must contain at least 8 characters, one uppercase, one lowercase, one number and one special character"}})}
                        />
                        {errors && errors.repeatPassword && <span className="text-red-500">{errors.repeatPassword.message}</span>}
                    </div>

                </div>


                <div className="flex flex-row justify-center mt-6">
                    <button type="submit"
                           className="rounded-lg bg-frost py-3 px-5 text-white cursor-pointer shadow-lg">
                        {i18n.t('confirm')}
                    </button>
                </div>
            </form>
        </div>
    );
};

export default ChangePassword;