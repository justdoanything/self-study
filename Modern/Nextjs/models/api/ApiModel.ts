
export enum HttpMethod {
    GET = 'GET',
    POST = 'POST',
    PUT = 'PUT',
    DELETE = 'DELETE',
    PATCH = 'PATCH'
}

export enum HttpStatusCode {
    OK = 'OK',
    FAIL = 'FAIL',
    SUCCESS = 'SUCCESS',
    SESSION_EXPIRE = 'SESSION_EXPIRE'
}

export enum Endpoint {
    BACKEND = 'back-end'
}

export enum EndpointPort {
    BACKEND = 8080
}

export interface ApiRequestQueryParam {
    [key: string]: string | number | boolean;
}

export interface ApiRequestBodyParam {
    queryParam?: ApiRequestQueryParam;
    bodyParam?: object;
}

export interface ApiRequestOption {
    isLoading?: boolean;
}

export interface ApiRequest {
    endpoint: Endpoint;
    url: string;
    method: HttpMethod;
    param?: ApiRequestBodyParam;
    option?: ApiRequestOption;
}

export interface CommonResponse<T = any> {
    result: string;
    status: string;
    data?: T;
}