import React, {useEffect} from 'react';
import {useLocation, useNavigate} from "react-router-dom";
import {useAuth} from "../../contexts/AuthContext";
import useUserService from "../../hooks/useUserService";
import {toast} from "react-toastify";

type LoggedGateProps = {
    children: React.ReactNode,
    admin?: boolean
}
const LoggedGate = ({children, admin}:LoggedGateProps) => {
    const navigate = useNavigate();
    const location = useLocation();
    const userService = useUserService();
    const {user} = useAuth();

    useEffect(()=>{
            if(!userService.getLoggedInUser() || !userService.getRole()){
                navigate('/login', {
                    state: {
                        url: location.pathname
                    }
                })
            }
            if(admin && userService.getRole() !== "ROLE_ADMIN") {
                navigate('/error');
            }
            if(!admin && userService.getRole() !== "ROLE_USER") {
                navigate('/error');
            }

    }, [user]);

    return (
        <>
            {children}
        </>
    );
};

export default LoggedGate;