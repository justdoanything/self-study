import axios, { AxiosInstance, AxiosRequestConfig } from "axios";

export enum Method {
    GET = "GET",
    POST = "POST",
    PUT = "PUT",
    DELETE = "DELETE",
    PATCH = "PATCH"
}

export interface QueryParam {
    [key: string]: string | number | boolean;
}

export interface BodyParam {
    queryParam?: QueryParam;
    bodyParam?: object;
}

export interface ApiRequest {
    url: string;
    method: Method;
    param?: BodyParam;
}

export interface ApiResponse<T = any> {
    statusCode: string;
    data?: T;
}

const getAxiosInstance = (param: any): AxiosInstance => {

    /* Method 별 공통 Header 설정 */
    axios.defaults.headers.post["Content-Type"] = "application/json";

    const axiosInstance = axios.create({
        params: param || {},
        headers: { "Cache-Controle": "no-cache" }
    });

    axiosInstance.interceptors.request.use(
        (axiosConfig: AxiosRequestConfig): AxiosRequestConfig => {
            if(axiosConfig?.headers){
                /* request 공통 Interceptor 설정 */
                axiosConfig.headers["key"] = "value";
            }
            return axiosConfig;
        },
        (error: any): Promise<any> => {
            return Promise.reject(error);
        }
    );

    axiosInstance.interceptors.response.use(
        async (response: any): Promise<any> => {
            /* response 공통 Interceptor 설정 */
            return response.data;
        },
        async (error: any): Promise<any> => {
            return error;
        }
    );

    return axiosInstance;
}

const getQueryString = (query?: QueryParam): string => {
    if(!query) return "";
    const key = Object.keys(query);
    const queryString = key.filter((key) => query[key] !== null && query[key] !== undefined)
        .map((key) => `${key}=${encodeURIComponent(query[key] as string)}`)
        .join("&");
    return queryString ? `?${queryString}` : "";
}

export const callApi = async (apiRequest: ApiRequest): Promise<ApiResponse> => {
    const url: string = apiRequest.url + getQueryString(apiRequest.param?.queryParam);

    let response: ApiResponse = {
        statusCode: "ERROR",
        data: {}
    };

    switch (apiRequest.method) {
        case Method.GET :
            response = await getAxiosInstance(apiRequest.param).get(url);
            break;
        case Method.POST:
            response = await getAxiosInstance(apiRequest.param).post(url, apiRequest.param?.bodyParam);
            break;
        case Method.PUT:
            response = await getAxiosInstance(apiRequest.param).put(url, apiRequest.param?.bodyParam);
            break;
        case Method.DELETE:
            response = await getAxiosInstance(apiRequest.param).delete(url);
            break;
        case Method.PATCH:
            response = await getAxiosInstance(apiRequest.param).patch(url, apiRequest.param?.bodyParam);
            break;
        default:
            break;
    }
    return response;
}