import { ApiMethod } from '../../enum/config/apiEnum';
import axios, { AxiosInstance, AxiosRequestConfig } from 'axios';

export interface QueryParams {
    [key: string]: string | number | boolean;
}

export interface BodyParams {
    queryParams?: QueryParams;
    bodyParams?: object;
}

export interface ApiRequest {
    url: string;
    method: ApiMethod;
    params?: BodyParams;
}

export interface ApiResponse<T = any> {
    statusCode: string;
    data?: T;
}
