import axios, { AxiosInstance, AxiosRequestConfig, InternalAxiosRequestConfig } from 'axios/index';
import { ApiRequest, ApiResponse, QueryParams } from '../model/config/apiModel';
import { ApiMethod } from '../enum/config/apiEnum';

const getAxiosInstance = (params: any): AxiosInstance => {
    axios.defaults.headers.post['Content-Type'] = 'application/json';

    const axiosInstance = axios.create({
        headers: { 'Cache-control': 'no-cache' },
        params: params || {},
    });

    axiosInstance.interceptors.request.use(
        (axiosConfig: InternalAxiosRequestConfig): InternalAxiosRequestConfig => {
            // Request에 대한 공통처리 코드
            return axiosConfig;
        },
        (error: any): Promise<any> => {
            // Error에 대한 공통처리 코드
            return Promise.reject(error);
        },
    );

    axiosInstance.interceptors.response.use(
        async (response: any): Promise<any> => {
            // Response에 대한 공통처리 코드
            return response.data;
        },
        async (error: any): Promise<any> => {
            return error;
        },
    );

    return axiosInstance;
};

const makeQueryString = (query?: QueryParams): string => {
    if (!query) return '';

    const keys = Object.keys(query);
    const queryString = keys
        .filter((key) => query[key] !== null && query[key] !== undefined)
        .map((key) => `${key}=${encodeURIComponent(query[key])}`)
        .join('&');
    return queryString ? `?${queryString}` : '';
};

export const callApi = async (apiRequest: ApiRequest): Promise<ApiResponse> => {
    const url: string = apiRequest.url + makeQueryString(apiRequest.params?.queryParms);

    let response: ApiResponse = {
        statusCode: '',
        data: {},
    };
    switch (apiRequest.method) {
        case ApiMethod.GET:
            response = await getAxiosInstance(apiRequest.params).get(url);
            break;
        case ApiMethod.POST:
            response = await getAxiosInstance(apiRequest.params).post(url, apiRequest.params?.bodyParams);
            break;
        case ApiMethod.PUT:
            response = await getAxiosInstance(apiRequest.params).put(url, apiRequest.params?.bodyParams);
            break;
        case ApiMethod.DELETE:
            response = await getAxiosInstance(apiRequest.params).delete(url);
            break;
        case ApiMethod.PATCH:
            response = await getAxiosInstance(apiRequest.params).patch(url, apiRequest.params?.bodyParams);
            break;
        default:
            break;
    }

    return response;
};
