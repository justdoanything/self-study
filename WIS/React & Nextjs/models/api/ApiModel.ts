
export enum HttpMethod {
    GET = 'GET',
    POST = 'POST',
    PUT = 'PUT',
    DELETE = 'DELETE',
    PATCH = 'PATCH'
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