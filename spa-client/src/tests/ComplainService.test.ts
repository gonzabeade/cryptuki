import {ComplainService} from "../services/ComplainService";
import axios from "axios";
import * as utils from "../common/utils/utils";
import {paths} from "../common/constants";
import {CreateComplainForm} from "../views/Support";
import {SolveComplaintFormModel} from "../components/SolveComplaintForm/SolveComplaintForm";

jest.mock('axios')

beforeEach(() => {
    axios.get = jest.fn().mockResolvedValue({data: []})
    axios.post = jest.fn().mockResolvedValue({data: []})
})

test("get complaints by URL", () => {
    const complainService = new ComplainService(() => axios)
    jest.spyOn(utils, 'processPaginatedResults').mockReturnThis();
    const test_url = "test_url"

    complainService.getComplaintsByUrl(test_url)

    expect(axios.get).toHaveBeenCalledWith(test_url)
    expect(axios.get).toHaveBeenCalledTimes(1)
})

test("get complaints", () => {
    const complainService = new ComplainService(() => axios)
    jest.spyOn(utils, 'processPaginatedResults').mockReturnThis();

    let params = new URLSearchParams()
    params.append("status","PENDING");

    complainService.getComplaints()

    expect(axios.get).toHaveBeenCalledWith(paths.BASE_URL + paths.COMPLAINTS, {"params": params})
    expect(axios.get).toHaveBeenCalledTimes(1)
})

test("get complaints by id", () => {
    const complainService = new ComplainService(() => axios)
    axios.get = jest.fn().mockResolvedValue({data: []})

    complainService.getComplaintById(1)

    expect(axios.get).toHaveBeenCalledWith(paths.BASE_URL + paths.COMPLAINTS + "/1")
    expect(axios.get).toHaveBeenCalledTimes(1)
})

test("create complaint", () => {
    const complaintService = new ComplainService(() => axios)
    const complainForm : CreateComplainForm = {
        email : "scastagnino@itba.edu.ar",
        tradeId : 1,
        message : "This is a new complaint"
    }

    complaintService.createComplain(complainForm)

    expect(axios.post).toHaveBeenCalledWith(
        paths.BASE_URL + paths.COMPLAINTS,
        {
            tradeId : complainForm.tradeId,
            message : complainForm.message
        }
    )
    expect(axios.post).toHaveBeenCalledTimes(1)
})

test("create complaint resolution", () => {
    const complaintService = new ComplainService(() => axios)
    const resolutionForm : SolveComplaintFormModel = {
        comments : "These are comments",
        resolution : "This es a resolution",
        complainId : 1
    }

    complaintService.createComplainResolution(resolutionForm)

    expect(axios.post).toHaveBeenCalledWith(
        paths.BASE_URL + paths.COMPLAINTS + "/1/resolution",
        {
            resolution : resolutionForm.resolution,
            comments : resolutionForm.comments
        }
    )
    expect(axios.post).toHaveBeenCalledTimes(1)
})
