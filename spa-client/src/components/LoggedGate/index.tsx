import React, {useEffect} from 'react';
import {useLocation, useNavigate} from "react-router-dom";

type LoggedGateProps = {
    children: React.ReactNode
}
const LoggedGate = ({children}:LoggedGateProps) => {
    const navigate = useNavigate();
    const location = useLocation();

    useEffect(()=>{
        //if not authenticated , redirect to login and save url in state
        if(!localStorage.getItem("accessToken") && !localStorage.getItem("refreshToken")){
            navigate('/login', {
                state: {
                    url: location.pathname
                }
            })
        }
    }, [navigate, location]);

    return (
        <>
            {children}
        </>
    );
};

export default LoggedGate;