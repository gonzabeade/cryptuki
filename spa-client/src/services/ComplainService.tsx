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
        const resp = await this.axiosInstance().get<ComplainModel[]>(url,{
            headers:{'Accept':'application/vnd.cryptuki.v1.complaint-list+json'}
        });
        return processPaginatedResults(resp);
    }

    public async getComplaints():Promise<PaginatedResults<ComplainModel>>{
        let params= new URLSearchParams();
        params.append("status","PENDING");
        const resp = await this.axiosInstance().
            get<ComplainModel[]>(this.basePath,{ params:params ,
            headers:{'Accept':'application/vnd.cryptuki.v1.complaint-list+json'}
            });
        return processPaginatedResults(resp);
    }

    public async getComplaintById(complainId:number):Promise<ComplainModel>{
        const resp = await this.axiosInstance()
            .get<ComplainModel>(this.basePath + '/' + complainId,
                {
                    headers:{'Accept':'application/vnd.cryptuki.v1.complaint+json'}
                });
        return resp.data;
    }

    public async createComplain(newComplain:CreateComplainForm){
        const resp = await this.axiosInstance().post<ComplainModel>(this.basePath , {
            tradeId: newComplain.tradeId,
            message: newComplain.message,
        },{
            headers:{
                'Accept':'application/vnd.cryptuki.v1.complaint+json',
                'Content-Type':'application/vnd.cryptuki.v1.complaint+json'
            }
        })
        return resp.data;
    }

    public async createComplainResolution(SolveComplaintFormModel:SolveComplaintFormModel){
        const resp = await this.axiosInstance().post<ComplainModel>(this.basePath + '/' + SolveComplaintFormModel.complainId + '/resolution', {
            resolution: SolveComplaintFormModel.resolution,
            comments: SolveComplaintFormModel.comments,
        },{
            headers:{
                'Accept':'application/vnd.cryptuki.v1.complaint-resolution+json',
                'Content-Type':'application/vnd.cryptuki.v1.complaint-resolution+json'
            }
        })
        return resp.data;
    }

}