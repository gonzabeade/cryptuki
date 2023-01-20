import React, {useEffect} from 'react';
import {useLocation, useNavigate} from "react-router-dom";
import {useAuth} from "../../contexts/AuthContext";
import useUserService from "../../hooks/useUserService";

type LoggedGateProps = {
    children: React.ReactNode
}
const LoggedGate = ({children}:LoggedGateProps) => {
    const navigate = useNavigate();
    const location = useLocation();
    const userService = useUserService();
    const {user} = useAuth();
    useEffect(()=>{
        if(!userService.getLoggedInUser()){
            navigate('/login', {
                state: {
                    url: location.pathname
                }
            })
        }
    }, [user]);

    return (
        <>
            {children}
        </>
    );
};

export default LoggedGate;