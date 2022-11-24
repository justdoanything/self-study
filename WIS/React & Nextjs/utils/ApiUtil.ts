import axios, {AxiosInstance} from "axios";
import {Endpoint, EndpointPort} from "../models/api/ApiModel";

const getAxiosInstance = (endPointName: string, isLoading: boolean, param?: any): AxiosInstance => {
    if(isLoading){
        // show Loading Component
    }

    setHAxiosHeader();

    let url = process.env.API_BASE_URL;
    switch(endPointName) {
        case Endpoint.BACKEND:
            url += ':' + EndpointPort.BACKEND.toString();
            break;
        default:
            break;
    }

    const instance = axios.create({
        baseURL: url,
        params: param || {},
    });

    instance.interceptors.request.use(

    )

    return instance;
}

const setHAxiosHeader = () => {
    axios.defaults.headers.post['Content-Type'] = 'application/json';
}