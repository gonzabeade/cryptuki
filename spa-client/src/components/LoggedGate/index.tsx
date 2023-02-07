import React, {useEffect, useState} from 'react';
import {useLocation, useNavigate} from "react-router-dom";
import {useAuth} from "../../contexts/AuthContext";
import useUserService from "../../hooks/useUserService";
import {set} from "react-hook-form";
import Loader from "../Loader";

type LoggedGateProps = {
    children: React.ReactNode,
    admin?: boolean
}
const LoggedGate = ({children, admin}:LoggedGateProps) => {
    const navigate = useNavigate();
    const location = useLocation();
    const userService = useUserService();
    const [loading, setLoading] = useState(true);
    const {user} = useAuth();

    useEffect(()=>{
            if(!userService.getLoggedInUser() || !userService.getRole()){
                navigate("/login", {
                    state: {
                        url: location.pathname
                    }
                })
                return;
            }

            if((admin && userService.getRole() !== "ROLE_ADMIN") || (admin === undefined && userService.getRole() === "ROLE_ADMIN")) {
                navigate('/forbidden');
                return;
            }
            setLoading(false);

    }, [user]);

    return (
        <> {loading ?
            <div>
                <Loader/>
            </div>
            :
            children}
        </>
    );
};

export default LoggedGate;