import axios from 'axios'; 
import { paths } from '../common/constants';

export default axios.create({
        baseURL: paths.BASE_URL
    }
)

