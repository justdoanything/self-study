const apiService = {
    sampleGetApi: async (
        getSampleRequest: GetSampleRequest
    ): Promise<ApiResponse> => {
        return callApi({
            url: "",
            method: ApiMethod.GET,
            param: {
                bodyParam: getSampleRequest
            }
        })
    }

    samplePostApi: async (
        postSampleRequest: PostSampleRequest
    ): Promise<ApiResponse> => {
        const response: ApiResponse = await callApi({
            url: "",
            method: ApiMethod.POST,
            param: {
                bodyParam: postSampleRequest
            }
        });
        return response.statusCode === 200 ? response.data : null;
    }
}

export default apiService;