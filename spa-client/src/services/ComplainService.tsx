import { paths } from "../common/constants";
import { AxiosInstance } from "axios";
import {ComplainModel} from "../types/ComplainModel";
import {SolveComplaintFormModel} from "../components/SolveComplaintForm/SolveComplaintForm";
import {CreateComplainForm} from "../views/Support";
import {PaginatedResults} from "../types/PaginatedResults";
import {processPaginatedResults} from "../common/utils/utils";

export class ComplainService{

    private readonly basePath = paths.BASE_URL + paths.COMPLAINTS;
    private readonly axiosInstance : ()=>AxiosInstance;

    public constructor(axiosInstance: ()=>AxiosInstance) {
        this.axiosInstance = axiosInstance;
    }

    public async getComplaintsByUrl(url:string):Promise<PaginatedResults<ComplainModel>>{
        const resp = await this.axiosInstance().get<ComplainModel[]>(url);
        return processPaginatedResults(resp);
    }

    public async getComplaints():Promise<PaginatedResults<ComplainModel>>{
        let params= new URLSearchParams();
        params.append("status","PENDING");
        const resp = await this.axiosInstance().
            get<ComplainModel[]>(this.basePath,{ params:params });
        return processPaginatedResults(resp);
    }

    public async getComplaintById(complainId:number):Promise<ComplainModel>{
        const resp = await this.axiosInstance().get<ComplainModel>(this.basePath + '/' + complainId);
        return resp.data;
    }

    public async createComplain(newComplain:CreateComplainForm){
        const resp = await this.axiosInstance().post<ComplainModel>(this.basePath , {
            tradeId: newComplain.tradeId,
            message: newComplain.message,
        })
        return resp.data;
    }

    public async createComplainResolution(SolveComplaintFormModel:SolveComplaintFormModel){
        const resp = await this.axiosInstance().post<ComplainModel>(this.basePath + '/' + SolveComplaintFormModel.complainId + '/resolution', {
            resolution: SolveComplaintFormModel.resolution,
            comments: SolveComplaintFormModel.comments,
        })
        return resp.data;
    }

}