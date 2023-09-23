import { ApiMethod } from '../../enum/config/apiEnum';
import { ApiResponse } from '../../model/config/apiModel';
import { callApi } from '../../config/apiConfig';
import { ApiSampleGetRequest, ApiSamplePostRequest } from '../../model/sample/apiSampleModel';
import { ServiceUrl } from '../../enum/sample/sampleApiEnum';

const apiService = {
    sampleGetApi: async (request: ApiSampleGetRequest): Promise<ApiResponse> => {
        return callApi({
            url: ServiceUrl.sampleGetApi,
            method: ApiMethod.GET,
            params: {
                queryParams: { ...request },
            },
        });
    },

    samplePostApi: async (request: ApiSamplePostRequest): Promise<ApiResponse> => {
        const response: ApiResponse = await callApi({
            url: ServiceUrl.samplePostApi,
            method: ApiMethod.POST,
            params: {
                bodyParams: request,
            },
        });
        return response.statusCode === 'OK' ? response.data : null;
    },
};

export default apiService;
