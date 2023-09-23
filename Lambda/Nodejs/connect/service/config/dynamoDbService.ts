import { callDynamoDb } from '../../config/dynamoDbConfig';
import logger from '../../config/winstonConfig';
import { DynamoDbRequest } from '../../model/config/dynamoDbModel';
import { DynamodbSampleScanResponse } from '../../model/sample/dynamoDbSampleModel';

const dynamoDbService = {
    scanSample: async (request: DynamoDbRequest): Promise<DynamodbSampleScanResponse> => {
        const response = await callDynamoDb(request);
        return response.Count > 0 ? { ...response } : null;
    },
};

export default dynamoDbService;
