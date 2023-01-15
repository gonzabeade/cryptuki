import { paths } from "../common/constants";
import { AxiosInstance } from "axios";
import {ComplainModel} from "../types/ComplainModel";
import {ComplainResolutionForm, CreateComplainForm} from "../components/CreateComplaintForm";

export class ComplainService{

    private readonly basePath = paths.BASE_URL + paths.COMPLAINTS;
    private readonly axiosInstance : ()=>AxiosInstance;

    public constructor(axiosInstance: ()=>AxiosInstance) {
        this.axiosInstance = axiosInstance;
    }

    public async getComplaints(page?:number):Promise<ComplainModel[]>{
        let params= new URLSearchParams();
        if(page) params.append("page",page.toString()!);
        const resp = await this.axiosInstance().
            get<ComplainModel[]>(this.basePath,{ params:params });
        return resp.data;
    }

    public async getComplaintById(complainId:number):Promise<ComplainModel>{
        const resp = await this.axiosInstance().get<ComplainModel>(this.basePath + complainId);
        return resp.data;
    }

    public async createComplain(newComplain:CreateComplainForm){
        const resp = await this.axiosInstance().put<ComplainModel>(this.basePath , {
            tradeId: newComplain.tradeId,
            message: newComplain.message,
        })
        return resp.data;
    }

    public async createComplainResolution(newComplainResolution:ComplainResolutionForm){
        const resp = await this.axiosInstance().put<ComplainModel>(this.basePath , {
            resolution: newComplainResolution.resolution,
            comments: newComplainResolution.comments,
        })
        return resp.data;
    }

}