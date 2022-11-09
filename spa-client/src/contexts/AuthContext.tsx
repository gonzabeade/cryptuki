import UserModel from '../types/UserModel';
import { createContext, useContext, useState } from 'react';

interface AuthContextType {
    user: UserModel | null;
    setUser: React.Dispatch<React.SetStateAction<UserModel | null>>;
    signin: (
        user: UserModel,
        rememberMe: boolean,
        callback: VoidFunction
    ) => void;
    signout: (callback: VoidFunction) => void;
}

export function AuthProvider({ children }: { children: React.ReactNode }) {
    let [user, setUser] = useState<UserModel | null>(null);

    let signin = (
        newUser: UserModel,
        rememberMe: boolean,
        callback: VoidFunction
    ) => {
        return () => {
            setUser(newUser);
            if (!localStorage.getItem("user"))
                localStorage.setItem("user", JSON.stringify(newUser));
            localStorage.setItem("accessToken", newUser.accessToken!);
            localStorage.setItem("refreshToken", newUser.refreshToken!);
            localStorage.setItem("isAdmin", newUser.admin ? "true" : "false");
            localStorage.setItem("rememberMe", rememberMe ? "true" : "false");
            callback();
        };
    };

    let signout = (callback: VoidFunction) => {
        return () => {
            setUser(null);
            localStorage.removeItem("user");
            localStorage.removeItem("token");
            localStorage.removeItem("isAdmin");
            localStorage.removeItem("rememberMe");
            // removeCookie("basic-token");
            callback();
        };
    };

    let value = { user, setUser, signin, signout};

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}


const AuthContext = createContext<AuthContextType>(null!);
export default AuthContext; 