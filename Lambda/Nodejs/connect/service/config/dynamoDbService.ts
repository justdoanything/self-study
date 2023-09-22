const dynamoDbService = {
    scanSample: async (
        request: AmazonConnectRequest
    ): Promse<AmazonConnectResponse> => {
        try {
            const response = await callDynamoDb(request);
            return response.Count > 0 ? { ...response } : null;
        } catch(exceptoin) {
            logger.error("Excepton : " + exception);
            throw new Error(exception);
        }
    }
}

export default dynamoDbService;
