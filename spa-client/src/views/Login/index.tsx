import {Link, useLocation, useNavigate, useNavigation} from "react-router-dom";
import {paths, sleep} from "../../common/constants";
import {withBasicAuthorization} from "../../hooks/useAxios";
import {useForm} from "react-hook-form";
import useUserService from "../../hooks/useUserService";
import {toast} from "react-toastify";
import useTradeService from "../../hooks/useTradeService";
import {useEffect} from "react";
import {useAuth} from "../../contexts/AuthContext";
import i18n from "../../i18n";
import {AxiosError, AxiosResponse} from "axios";


type LoginFormValues = {
    username: string;
    password: string;
}

const Login = () => {

    const { register, handleSubmit, formState: { errors } } = useForm<LoginFormValues>();
    const tradeService = useTradeService();
    const navigate = useNavigate();
    const location = useLocation();
    const {user, signin} = useAuth();
    const userService = useUserService();

   useEffect(()=>{
       if(user){
           navigate('/');
       }
   }, []);

    async function onSubmit(data:LoginFormValues){
        withBasicAuthorization(data.username, data.password);
        try{
            // dummy call to get the token
            const resp = await tradeService.getLastTransactions(data.username);
            toast.success("Successfully logged in!");
            signin(await userService.getUser(data.username), ()=>{
                if(location.state && location.state.url) {
                    navigate(location.state.url);
                }else if( userService.getRole() === "ROLE_ADMIN"){
                    navigate("/admin")
                }else{
                    navigate('/')
                }
            });
            await sleep(500);
        }catch (e){
            const error:AxiosError = e as AxiosError;
            if(error.response?.data){
                const data = error.response.data as {message:string} ;
                toast.error(i18n.t(`${data.message}`));
            }else{
                toast.error(i18n.t(`${error.code}`))
            }
        }
    }

    return (
        <div className=" w-full flex justify-center">
            <form onSubmit={handleSubmit(onSubmit)} className="flex
            flex-col mx-auto mt-24 w-[600px]
            bg-whitesmoke shadow-lg rounded-lg px-24 pt-10  pb-16
            border-frostdr border-t-8">
                <h1 className="font-sans font-roboto font-bold text-xl mx-auto text-polar">{i18n.t('signin')} </h1>
                <h4 className="text-center font-lato text-black/[.4] text-sm mb-4">{i18n.t('welcome')}</h4>
                <input
                    placeholder={i18n.t('username')!}
                    type="text"
                    id="username"
                    autoComplete="off"
                    {...register("username", {required: true, minLength: {
                            value: 3,
                            message:"Username must be at least 3 characters long"
                        }, maxLength: {
                            value: 23,
                            message:"Username must be at most 23 characters long"
                        }})}
                    className="p-2 m-2 rounded-lg"
                />
                {errors && errors.username && <span className="text-red-500">{errors.username.message}</span>}
                <input
                    placeholder={i18n.t('password')!}
                    type="password"
                    id="password"
                    {...register("password",{required: true, minLength: {value: 6, message: "Password must contain at least 6 characters."
                        }, maxLength:{
                            value:24,
                            message:"Password must contain max 24 characters"
                        }})}
                    className="p-2 m-2 rounded-lg"
                />
                {errors && errors.password && <span className="text-red-500">{errors.password.message}</span>}

                
                <button type="submit" className="bg-frostdr text-white mx-auto mb-auto mt-6 py-2 px-4 rounded-lg font-lato font-bold hover:bg-polar">{i18n.t('SignIn')}</button>
                <p className="font-lato font-light mx-auto text-xs mt-2 text-black/[.4]">{i18n.t('noAccount')}<br/></p>
                <p className=" hover:cursor-pointer text-polar hover:text-blue-400 font-bold font-frostdr  mx-auto text-xs font-lato">{/*put router link here*/}<Link
                    to="/register">{i18n.t('createAccount')}</Link>
                </p>
                <p className=" hover:cursor-pointer text-polar hover:text-blue-400 font-bold font-frostdr  mx-auto text-xs font-lato">{/*put router link here*/}<Link
                    to="/recoverPassword">{i18n.t('ForgotPassword')}</Link>
                </p>
            </form>
        </div>
    )
}

export default Login; 