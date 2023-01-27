import React, {useEffect, useState} from 'react';
import Navbar from "../Navbar";
import AdminNavBar from "../AdminNavBar/AdminNavBar";
import useUserService from "../../hooks/useUserService";
import {useAuth} from "../../contexts/AuthContext";

const NavbarAll = () => {
    const [isAdmin, setIsAdmin] = useState<boolean>(false);
    const userService  = useUserService();
    const {user} = useAuth();

    useEffect(()=>{
        if(userService.getRole()  && userService.getRole() === "ROLE_ADMIN"){
            setIsAdmin(true);
        }else{
            setIsAdmin(false);
        }
    }, [user]);
    return (
        <>
            {isAdmin ? <AdminNavBar/> : <Navbar/>}
        </>


    );
};

export default NavbarAll;