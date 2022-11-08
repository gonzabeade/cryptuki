import axios from 'axios';
import { paths } from '../common/constants';

export const axiosPrivate = axios.create({
    baseURL: paths.BASE_URL
});



