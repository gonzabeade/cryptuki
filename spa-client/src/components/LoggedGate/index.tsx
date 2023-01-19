import React, {useEffect} from 'react';
import {useLocation, useNavigate} from "react-router-dom";
import {useAuth} from "../../contexts/AuthContext";

type LoggedGateProps = {
    children: React.ReactNode
}
const LoggedGate = ({children}:LoggedGateProps) => {
    const navigate = useNavigate();
    const location = useLocation();
    const {user} = useAuth();

    useEffect(()=>{
        if(!user){
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