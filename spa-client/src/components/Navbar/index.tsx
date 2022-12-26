import './styles.css';

import icons from "../../assets";
import { Link } from 'react-router-dom';
import useUserService from '../../hooks/useUserService';
import {useEffect, useState} from 'react';

const Navbar = () => {

    const userService = useUserService();

    const [username, setUsername] = useState<string | null>(userService.getLoggedInUser());


    useEffect(()=>{
        // open
        const burger = document.querySelectorAll('.navbar-burger');
        const menu = document.querySelectorAll('.navbar-menu');

        if (burger.length && menu.length) {
            for (var i = 0; i < burger.length; i++) {
                burger[i].addEventListener('click', function () {
                    for (var j = 0; j < menu.length; j++) {
                        menu[j].classList.toggle('hidden');
                    }
                });
            }
        }

        // close
        const close = document.querySelectorAll('.navbar-close');
        const backdrop = document.querySelectorAll('.navbar-backdrop');

        if (close.length) {
            for (var i = 0; i < close.length; i++) {
                close[i].addEventListener('click', function () {
                    for (var j = 0; j < menu.length; j++) {
                        menu[j].classList.toggle('hidden');
                    }
                });
            }
        }

        if (backdrop.length) {
            for (var i = 0; i < backdrop.length; i++) {
                backdrop[i].addEventListener('click', function () {
                    for (var j = 0; j < menu.length; j++) {
                        menu[j].classList.toggle('hidden');
                    }
                });
            }
        }
    });

    return (
        <>
            <nav className="relative px-4 py-4 flex justify-between items-center bg-white">
                <Link to="/" className="text-3xl font-bold leading-none hover:cursor-pointer">
                    <svg xmlns="http://www.w3.org/2000/svg" width="185" height="36" viewBox="0 0 185 36" fill="none">
                        <path
                            d="M14.9006 29.224C12.9326 29.224 11.0846 28.852 9.35659 28.108C7.62859 27.388 6.10459 26.38 4.78459 25.084C3.48859 23.764 2.46859 22.24 1.72459 20.512C1.00459 18.784 0.644594 16.936 0.644594 14.968C0.644594 12.976 1.00459 11.128 1.72459 9.424C2.46859 7.696 3.48859 6.184 4.78459 4.888C6.10459 3.568 7.62859 2.548 9.35659 1.828C11.0846 1.084 12.9326 0.711999 14.9006 0.711999V29.224ZM37.3223 24.3965C37.9434 24.3965 38.4941 24.2793 38.9746 24.0449C39.4551 23.7988 39.8301 23.459 40.0996 23.0254C40.3809 22.5801 40.5273 22.0586 40.5391 21.4609H45.3027C45.291 22.7969 44.9336 23.9863 44.2305 25.0293C43.5273 26.0605 42.584 26.875 41.4004 27.4727C40.2168 28.0586 38.8926 28.3516 37.4277 28.3516C35.9512 28.3516 34.6621 28.1055 33.5605 27.6133C32.4707 27.1211 31.5625 26.4414 30.8359 25.5742C30.1094 24.6953 29.5645 23.6758 29.2012 22.5156C28.8379 21.3438 28.6562 20.0898 28.6562 18.7539V18.2441C28.6562 16.8965 28.8379 15.6426 29.2012 14.4824C29.5645 13.3105 30.1094 12.291 30.8359 11.4238C31.5625 10.5449 32.4707 9.85938 33.5605 9.36719C34.6504 8.875 35.9277 8.62891 37.3926 8.62891C38.9512 8.62891 40.3164 8.92773 41.4883 9.52539C42.6719 10.123 43.5977 10.9785 44.2656 12.0918C44.9453 13.1934 45.291 14.5 45.3027 16.0117H40.5391C40.5273 15.3789 40.3926 14.8047 40.1348 14.2891C39.8887 13.7734 39.5254 13.3633 39.0449 13.0586C38.5762 12.7422 37.9961 12.584 37.3047 12.584C36.5664 12.584 35.9629 12.7422 35.4941 13.0586C35.0254 13.3633 34.6621 13.7852 34.4043 14.3242C34.1465 14.8516 33.9648 15.4551 33.8594 16.1348C33.7656 16.8027 33.7188 17.5059 33.7188 18.2441V18.7539C33.7188 19.4922 33.7656 20.2012 33.8594 20.8809C33.9531 21.5605 34.1289 22.1641 34.3867 22.6914C34.6562 23.2188 35.0254 23.6348 35.4941 23.9395C35.9629 24.2441 36.5723 24.3965 37.3223 24.3965ZM53.3008 13.1289V28H48.2383V8.98047H53.002L53.3008 13.1289ZM59.0312 8.85742L58.9434 13.5508C58.6973 13.5156 58.3984 13.4863 58.0469 13.4629C57.707 13.4277 57.3965 13.4102 57.1152 13.4102C56.4004 13.4102 55.7793 13.5039 55.252 13.6914C54.7363 13.8672 54.3027 14.1309 53.9512 14.4824C53.6113 14.834 53.3535 15.2617 53.1777 15.7656C53.0137 16.2695 52.9199 16.8438 52.8965 17.4883L51.877 17.1719C51.877 15.9414 52 14.8105 52.2461 13.7793C52.4922 12.7363 52.8496 11.8281 53.3184 11.0547C53.7988 10.2812 54.3848 9.68359 55.0762 9.26172C55.7676 8.83984 56.5586 8.62891 57.4492 8.62891C57.7305 8.62891 58.0176 8.65234 58.3105 8.69922C58.6035 8.73438 58.8438 8.78711 59.0312 8.85742ZM67.3809 25.8555L72.4258 8.98047H77.8574L70.2109 30.8652C70.0469 31.3457 69.8242 31.8613 69.543 32.4121C69.2734 32.9629 68.9043 33.4844 68.4355 33.9766C67.9785 34.4805 67.3984 34.8906 66.6953 35.207C66.0039 35.5234 65.1543 35.6816 64.1465 35.6816C63.666 35.6816 63.2734 35.6523 62.9688 35.5938C62.6641 35.5352 62.3008 35.4531 61.8789 35.3477V31.6387C62.0078 31.6387 62.1426 31.6387 62.2832 31.6387C62.4238 31.6504 62.5586 31.6562 62.6875 31.6562C63.3555 31.6562 63.9004 31.5801 64.3223 31.4277C64.7441 31.2754 65.084 31.041 65.3418 30.7246C65.5996 30.4199 65.8047 30.0215 65.957 29.5293L67.3809 25.8555ZM65.2715 8.98047L69.4023 22.7617L70.123 28.123L66.6777 28.4922L59.8398 8.98047H65.2715ZM84.9414 12.6367V35.3125H79.8789V8.98047H84.5723L84.9414 12.6367ZM96.9824 18.2793V18.6484C96.9824 20.0312 96.8184 21.3145 96.4902 22.498C96.1738 23.6816 95.7051 24.7129 95.084 25.5918C94.4629 26.459 93.6895 27.1387 92.7637 27.6309C91.8496 28.1113 90.7949 28.3516 89.5996 28.3516C88.4395 28.3516 87.4316 28.1172 86.5762 27.6484C85.7207 27.1797 85 26.5234 84.4141 25.6797C83.8398 24.8242 83.377 23.834 83.0254 22.709C82.6738 21.584 82.4043 20.377 82.2168 19.0879V18.1211C82.4043 16.7383 82.6738 15.4727 83.0254 14.3242C83.377 13.1641 83.8398 12.1621 84.4141 11.3184C85 10.4629 85.7148 9.80078 86.5586 9.33203C87.4141 8.86328 88.416 8.62891 89.5645 8.62891C90.7715 8.62891 91.832 8.85742 92.7461 9.31445C93.6719 9.77148 94.4453 10.4277 95.0664 11.2832C95.6992 12.1387 96.1738 13.1582 96.4902 14.3418C96.8184 15.5254 96.9824 16.8379 96.9824 18.2793ZM91.9023 18.6484V18.2793C91.9023 17.4707 91.832 16.7266 91.6914 16.0469C91.5625 15.3555 91.3516 14.752 91.0586 14.2363C90.7773 13.7207 90.4023 13.3223 89.9336 13.041C89.4766 12.748 88.9199 12.6016 88.2637 12.6016C87.5723 12.6016 86.9805 12.7129 86.4883 12.9355C86.0078 13.1582 85.6152 13.4805 85.3105 13.9023C85.0059 14.3242 84.7773 14.8281 84.625 15.4141C84.4727 16 84.3789 16.6621 84.3438 17.4004V19.8438C84.4023 20.7109 84.5664 21.4902 84.8359 22.1816C85.1055 22.8613 85.5215 23.4004 86.084 23.7988C86.6465 24.1973 87.3848 24.3965 88.2988 24.3965C88.9668 24.3965 89.5293 24.25 89.9863 23.957C90.4434 23.6523 90.8125 23.2363 91.0938 22.709C91.3867 22.1816 91.5918 21.5723 91.709 20.8809C91.8379 20.1895 91.9023 19.4453 91.9023 18.6484ZM109.445 8.98047V12.5664H98.3711V8.98047H109.445ZM101.113 4.28711H106.176V22.2695C106.176 22.8203 106.246 23.2422 106.387 23.5352C106.539 23.8281 106.762 24.0332 107.055 24.1504C107.348 24.2559 107.717 24.3086 108.162 24.3086C108.479 24.3086 108.76 24.2969 109.006 24.2734C109.264 24.2383 109.48 24.2031 109.656 24.168L109.674 27.8945C109.24 28.0352 108.771 28.1465 108.268 28.2285C107.764 28.3105 107.207 28.3516 106.598 28.3516C105.484 28.3516 104.512 28.1699 103.68 27.8066C102.859 27.4316 102.227 26.834 101.781 26.0137C101.336 25.1934 101.113 24.1152 101.113 22.7793V4.28711ZM123.578 23.4473V8.98047H128.641V28H123.877L123.578 23.4473ZM124.141 19.5449L125.635 19.5098C125.635 20.7754 125.488 21.9531 125.195 23.043C124.902 24.1211 124.463 25.0586 123.877 25.8555C123.291 26.6406 122.553 27.2559 121.662 27.7012C120.771 28.1348 119.723 28.3516 118.516 28.3516C117.59 28.3516 116.734 28.2227 115.949 27.9648C115.176 27.6953 114.508 27.2793 113.945 26.7168C113.395 26.1426 112.961 25.4102 112.645 24.5195C112.34 23.6172 112.188 22.5332 112.188 21.2676V8.98047H117.25V21.3027C117.25 21.8652 117.314 22.3398 117.443 22.7266C117.584 23.1133 117.777 23.4297 118.023 23.6758C118.27 23.9219 118.557 24.0977 118.885 24.2031C119.225 24.3086 119.6 24.3613 120.01 24.3613C121.053 24.3613 121.873 24.1504 122.471 23.7285C123.08 23.3066 123.508 22.7324 123.754 22.0059C124.012 21.2676 124.141 20.4473 124.141 19.5449ZM137.57 0.982422V28H132.508V0.982422H137.57ZM149.559 8.98047L141.297 18.4023L136.867 22.8848L135.021 19.2285L138.537 14.7637L143.477 8.98047H149.559ZM144.303 28L138.678 19.2109L142.176 16.1523L150.139 28H144.303ZM157.082 8.98047V28H152.002V8.98047H157.082ZM151.686 4.02344C151.686 3.28516 151.943 2.67578 152.459 2.19531C152.975 1.71484 153.666 1.47461 154.533 1.47461C155.389 1.47461 156.074 1.71484 156.59 2.19531C157.117 2.67578 157.381 3.28516 157.381 4.02344C157.381 4.76172 157.117 5.37109 156.59 5.85156C156.074 6.33203 155.389 6.57227 154.533 6.57227C153.666 6.57227 152.975 6.33203 152.459 5.85156C151.943 5.37109 151.686 4.76172 151.686 4.02344ZM170.098 29.224V0.711999C172.09 0.711999 173.938 1.084 175.642 1.828C177.37 2.548 178.882 3.568 180.178 4.888C181.498 6.184 182.518 7.696 183.238 9.424C183.982 11.128 184.354 12.976 184.354 14.968C184.354 16.936 183.982 18.784 183.238 20.512C182.518 22.24 181.498 23.764 180.178 25.084C178.882 26.38 177.37 27.388 175.642 28.108C173.938 28.852 172.09 29.224 170.098 29.224Z"
                            fill="#4C566A"/>
                    </svg>
                </Link>
                <div className="lg:hidden">
                    <button className="navbar-burger flex items-center text-polard p-3">
                        <svg className="block h-4 w-4 fill-current" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                            <title>Mobile menu</title>
                            <path d="M0 3h20v2H0V3zm0 6h20v2H0V9zm0 6h20v2H0v-2z"></path>
                        </svg>
                    </button>
                </div>
                <ul className="hidden absolute top-1/2 left-1/2 transform -translate-y-1/2 -translate-x-1/2 lg:flex lg:mx-auto lg:flex lg:items-center lg:w-auto lg:space-x-6">
                    <li>
                        <Link to="/" className="text-sm text-gray-400 hover:text-gray-500 hover:cursor-pointer">Marketplace P2P</Link>
                    </li>
                    <li>
                        <Link className="text-sm text-gray-400 hover:cursor-pointer hover:text-gray-500"   to="/contact">Contact</Link>
                    </li>
                </ul>
                <Link className="hidden lg:inline-block lg:ml-auto lg:mr-3 py-2 px-6 bg-gray-50 hover:bg-gray-100 text-sm text-gray-900 font-bold  hover:cursor-pointer rounded-xl transition duration-200"
                   to="/login">Sign In</Link>
                <Link className="hidden lg:inline-block py-2 px-6 bg-frostdr hover:bg-blue-600 text-sm text-white font-bold rounded-xl transition duration-200 hover:cursor-pointer"
                   to="/register">Sign up</Link>
            </nav>
            <div className="navbar-menu relative z-50 hidden">
                <div className="navbar-backdrop fixed inset-0 bg-gray-800 opacity-25"></div>
                <nav
                    className="fixed top-0 left-0 bottom-0 flex flex-col w-5/6 max-w-sm py-6 px-6 bg-white border-r overflow-y-auto">
                    <div className="flex items-center mb-8">
                        <a className="mr-auto text-3xl font-bold leading-none" href="#">
                            <svg xmlns="http://www.w3.org/2000/svg" width="185" height="36" viewBox="0 0 185 36" fill="none">
                                <path
                                    d="M14.9006 29.224C12.9326 29.224 11.0846 28.852 9.35659 28.108C7.62859 27.388 6.10459 26.38 4.78459 25.084C3.48859 23.764 2.46859 22.24 1.72459 20.512C1.00459 18.784 0.644594 16.936 0.644594 14.968C0.644594 12.976 1.00459 11.128 1.72459 9.424C2.46859 7.696 3.48859 6.184 4.78459 4.888C6.10459 3.568 7.62859 2.548 9.35659 1.828C11.0846 1.084 12.9326 0.711999 14.9006 0.711999V29.224ZM37.3223 24.3965C37.9434 24.3965 38.4941 24.2793 38.9746 24.0449C39.4551 23.7988 39.8301 23.459 40.0996 23.0254C40.3809 22.5801 40.5273 22.0586 40.5391 21.4609H45.3027C45.291 22.7969 44.9336 23.9863 44.2305 25.0293C43.5273 26.0605 42.584 26.875 41.4004 27.4727C40.2168 28.0586 38.8926 28.3516 37.4277 28.3516C35.9512 28.3516 34.6621 28.1055 33.5605 27.6133C32.4707 27.1211 31.5625 26.4414 30.8359 25.5742C30.1094 24.6953 29.5645 23.6758 29.2012 22.5156C28.8379 21.3438 28.6562 20.0898 28.6562 18.7539V18.2441C28.6562 16.8965 28.8379 15.6426 29.2012 14.4824C29.5645 13.3105 30.1094 12.291 30.8359 11.4238C31.5625 10.5449 32.4707 9.85938 33.5605 9.36719C34.6504 8.875 35.9277 8.62891 37.3926 8.62891C38.9512 8.62891 40.3164 8.92773 41.4883 9.52539C42.6719 10.123 43.5977 10.9785 44.2656 12.0918C44.9453 13.1934 45.291 14.5 45.3027 16.0117H40.5391C40.5273 15.3789 40.3926 14.8047 40.1348 14.2891C39.8887 13.7734 39.5254 13.3633 39.0449 13.0586C38.5762 12.7422 37.9961 12.584 37.3047 12.584C36.5664 12.584 35.9629 12.7422 35.4941 13.0586C35.0254 13.3633 34.6621 13.7852 34.4043 14.3242C34.1465 14.8516 33.9648 15.4551 33.8594 16.1348C33.7656 16.8027 33.7188 17.5059 33.7188 18.2441V18.7539C33.7188 19.4922 33.7656 20.2012 33.8594 20.8809C33.9531 21.5605 34.1289 22.1641 34.3867 22.6914C34.6562 23.2188 35.0254 23.6348 35.4941 23.9395C35.9629 24.2441 36.5723 24.3965 37.3223 24.3965ZM53.3008 13.1289V28H48.2383V8.98047H53.002L53.3008 13.1289ZM59.0312 8.85742L58.9434 13.5508C58.6973 13.5156 58.3984 13.4863 58.0469 13.4629C57.707 13.4277 57.3965 13.4102 57.1152 13.4102C56.4004 13.4102 55.7793 13.5039 55.252 13.6914C54.7363 13.8672 54.3027 14.1309 53.9512 14.4824C53.6113 14.834 53.3535 15.2617 53.1777 15.7656C53.0137 16.2695 52.9199 16.8438 52.8965 17.4883L51.877 17.1719C51.877 15.9414 52 14.8105 52.2461 13.7793C52.4922 12.7363 52.8496 11.8281 53.3184 11.0547C53.7988 10.2812 54.3848 9.68359 55.0762 9.26172C55.7676 8.83984 56.5586 8.62891 57.4492 8.62891C57.7305 8.62891 58.0176 8.65234 58.3105 8.69922C58.6035 8.73438 58.8438 8.78711 59.0312 8.85742ZM67.3809 25.8555L72.4258 8.98047H77.8574L70.2109 30.8652C70.0469 31.3457 69.8242 31.8613 69.543 32.4121C69.2734 32.9629 68.9043 33.4844 68.4355 33.9766C67.9785 34.4805 67.3984 34.8906 66.6953 35.207C66.0039 35.5234 65.1543 35.6816 64.1465 35.6816C63.666 35.6816 63.2734 35.6523 62.9688 35.5938C62.6641 35.5352 62.3008 35.4531 61.8789 35.3477V31.6387C62.0078 31.6387 62.1426 31.6387 62.2832 31.6387C62.4238 31.6504 62.5586 31.6562 62.6875 31.6562C63.3555 31.6562 63.9004 31.5801 64.3223 31.4277C64.7441 31.2754 65.084 31.041 65.3418 30.7246C65.5996 30.4199 65.8047 30.0215 65.957 29.5293L67.3809 25.8555ZM65.2715 8.98047L69.4023 22.7617L70.123 28.123L66.6777 28.4922L59.8398 8.98047H65.2715ZM84.9414 12.6367V35.3125H79.8789V8.98047H84.5723L84.9414 12.6367ZM96.9824 18.2793V18.6484C96.9824 20.0312 96.8184 21.3145 96.4902 22.498C96.1738 23.6816 95.7051 24.7129 95.084 25.5918C94.4629 26.459 93.6895 27.1387 92.7637 27.6309C91.8496 28.1113 90.7949 28.3516 89.5996 28.3516C88.4395 28.3516 87.4316 28.1172 86.5762 27.6484C85.7207 27.1797 85 26.5234 84.4141 25.6797C83.8398 24.8242 83.377 23.834 83.0254 22.709C82.6738 21.584 82.4043 20.377 82.2168 19.0879V18.1211C82.4043 16.7383 82.6738 15.4727 83.0254 14.3242C83.377 13.1641 83.8398 12.1621 84.4141 11.3184C85 10.4629 85.7148 9.80078 86.5586 9.33203C87.4141 8.86328 88.416 8.62891 89.5645 8.62891C90.7715 8.62891 91.832 8.85742 92.7461 9.31445C93.6719 9.77148 94.4453 10.4277 95.0664 11.2832C95.6992 12.1387 96.1738 13.1582 96.4902 14.3418C96.8184 15.5254 96.9824 16.8379 96.9824 18.2793ZM91.9023 18.6484V18.2793C91.9023 17.4707 91.832 16.7266 91.6914 16.0469C91.5625 15.3555 91.3516 14.752 91.0586 14.2363C90.7773 13.7207 90.4023 13.3223 89.9336 13.041C89.4766 12.748 88.9199 12.6016 88.2637 12.6016C87.5723 12.6016 86.9805 12.7129 86.4883 12.9355C86.0078 13.1582 85.6152 13.4805 85.3105 13.9023C85.0059 14.3242 84.7773 14.8281 84.625 15.4141C84.4727 16 84.3789 16.6621 84.3438 17.4004V19.8438C84.4023 20.7109 84.5664 21.4902 84.8359 22.1816C85.1055 22.8613 85.5215 23.4004 86.084 23.7988C86.6465 24.1973 87.3848 24.3965 88.2988 24.3965C88.9668 24.3965 89.5293 24.25 89.9863 23.957C90.4434 23.6523 90.8125 23.2363 91.0938 22.709C91.3867 22.1816 91.5918 21.5723 91.709 20.8809C91.8379 20.1895 91.9023 19.4453 91.9023 18.6484ZM109.445 8.98047V12.5664H98.3711V8.98047H109.445ZM101.113 4.28711H106.176V22.2695C106.176 22.8203 106.246 23.2422 106.387 23.5352C106.539 23.8281 106.762 24.0332 107.055 24.1504C107.348 24.2559 107.717 24.3086 108.162 24.3086C108.479 24.3086 108.76 24.2969 109.006 24.2734C109.264 24.2383 109.48 24.2031 109.656 24.168L109.674 27.8945C109.24 28.0352 108.771 28.1465 108.268 28.2285C107.764 28.3105 107.207 28.3516 106.598 28.3516C105.484 28.3516 104.512 28.1699 103.68 27.8066C102.859 27.4316 102.227 26.834 101.781 26.0137C101.336 25.1934 101.113 24.1152 101.113 22.7793V4.28711ZM123.578 23.4473V8.98047H128.641V28H123.877L123.578 23.4473ZM124.141 19.5449L125.635 19.5098C125.635 20.7754 125.488 21.9531 125.195 23.043C124.902 24.1211 124.463 25.0586 123.877 25.8555C123.291 26.6406 122.553 27.2559 121.662 27.7012C120.771 28.1348 119.723 28.3516 118.516 28.3516C117.59 28.3516 116.734 28.2227 115.949 27.9648C115.176 27.6953 114.508 27.2793 113.945 26.7168C113.395 26.1426 112.961 25.4102 112.645 24.5195C112.34 23.6172 112.188 22.5332 112.188 21.2676V8.98047H117.25V21.3027C117.25 21.8652 117.314 22.3398 117.443 22.7266C117.584 23.1133 117.777 23.4297 118.023 23.6758C118.27 23.9219 118.557 24.0977 118.885 24.2031C119.225 24.3086 119.6 24.3613 120.01 24.3613C121.053 24.3613 121.873 24.1504 122.471 23.7285C123.08 23.3066 123.508 22.7324 123.754 22.0059C124.012 21.2676 124.141 20.4473 124.141 19.5449ZM137.57 0.982422V28H132.508V0.982422H137.57ZM149.559 8.98047L141.297 18.4023L136.867 22.8848L135.021 19.2285L138.537 14.7637L143.477 8.98047H149.559ZM144.303 28L138.678 19.2109L142.176 16.1523L150.139 28H144.303ZM157.082 8.98047V28H152.002V8.98047H157.082ZM151.686 4.02344C151.686 3.28516 151.943 2.67578 152.459 2.19531C152.975 1.71484 153.666 1.47461 154.533 1.47461C155.389 1.47461 156.074 1.71484 156.59 2.19531C157.117 2.67578 157.381 3.28516 157.381 4.02344C157.381 4.76172 157.117 5.37109 156.59 5.85156C156.074 6.33203 155.389 6.57227 154.533 6.57227C153.666 6.57227 152.975 6.33203 152.459 5.85156C151.943 5.37109 151.686 4.76172 151.686 4.02344ZM170.098 29.224V0.711999C172.09 0.711999 173.938 1.084 175.642 1.828C177.37 2.548 178.882 3.568 180.178 4.888C181.498 6.184 182.518 7.696 183.238 9.424C183.982 11.128 184.354 12.976 184.354 14.968C184.354 16.936 183.982 18.784 183.238 20.512C182.518 22.24 181.498 23.764 180.178 25.084C178.882 26.38 177.37 27.388 175.642 28.108C173.938 28.852 172.09 29.224 170.098 29.224Z"
                                    fill="#4C566A"/>
                            </svg>
                        </a>
                        <button className="navbar-close">
                            <svg className="h-6 w-6 text-gray-400 cursor-pointer hover:text-gray-500"
                                 xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2"
                                      d="M6 18L18 6M6 6l12 12"></path>
                            </svg>
                        </button>
                    </div>
                    <div>
                        <ul>
                            <li className="mb-1">
                                <Link className="block p-4 text-sm font-semibold text-gray-400 hover:bg-blue-50 hover:text-polar  hover:cursor-pointer rounded font-roboto"
                                   to="/">Marketplace P2P</Link>
                            </li>
                            <li className="mb-1">
                                <Link className="block p-4 text-sm font-semibold text-gray-400 hover:bg-blue-50 hover:text-polar  hover:cursor-pointer rounded"
                                   to="/contact">Contact</Link>
                            </li>
                        </ul>
                    </div>
                    <div className="mt-auto">
                        <div className="pt-6">
                            <Link className="block px-4 py-3 mb-3 leading-loose text-xs text-center font-semibold leading-none bg-gray-50 hover:bg-gray-100  hover:cursor-pointer rounded-xl "
                               to="/login">Sign in</Link>
                            <Link className="block px-4 py-3 mb-2 leading-loose text-xs text-center text-white font-semibold bg-frostdr hover:bg-blue-700  hover:cursor-pointer rounded-xl"
                                  to="/register">Sign Up</Link>
                        </div>
                    </div>
                </nav>
            </div>
        </>

    );
    // return (
    //     <div className="flex w-full bg-white p-7">
    //         <nav className="flex flex-row">
    //             <Link  className="my-auto" to="/">
    //                 <img src={icons.logo} alt="cryptuki logo"/>
    //             </Link>
    //             <div className="flex flex-row my-auto mx-auto justify-between">
    //                 <Link to="/" className="hover:cursor-pointer justify-around">
    //                     <h1>Mercado P2P</h1>
    //                 </Link>
    //                 <Link to="/contact" className="hover:cursor-pointer">
    //                     <h1>Contáctate</h1>
    //                 </Link>
    //                 {
    //                     !username &&
    //                     <div >
    //                         <Link to="/login">
    //                             <button className="gray bold">Inicia sesión</button>
    //                         </Link>
    //                         <Link to="/register">
    //                             <button className="dark bold">Regístrate</button>
    //                         </Link>
    //                     </div>
    //                 }
    //                 {
    //                     username &&
    //                     <div>
    //                         <Link to="/seller">
    //                             <button className="gray bold">Panel de vendedor</button>
    //                         </Link>
    //                         <Link onClick={ () => {setUsername(userService.getLoggedInUser())}} to="/buyer">
    //                             <div>
    //                                 <img src={icons.profile} alt="profile icon"/>
    //                                 <p>{username}</p>
    //                             </div>
    //                         </Link>
    //                         <img onClick={() => {localStorage.removeItem("refreshToken"); localStorage.removeItem("accessToken"); setUsername(null); } } src={icons.logout} alt="logout"/>
    //                     </div>
    //                 }
    //             </div>
    //         </nav>
    //     </div>

    // )

};



export default Navbar; 