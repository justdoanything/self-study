import { DynamoDbMethod } from '../enum/config/dynamoDbEnum';
import { AmazonConnectRequest } from '../model/connect/amazonConnectModel';
import dynamoDbService from '../service/config/dynamoDbService';
import { DynamodbSampleScanResponse } from '../model/sample/dynamoDbSampleModel';

export const dynamoDbScanSampleHandler = async (request: AmazonConnectRequest): Promise<DynamodbSampleScanResponse> => {
    const customerNumber = request.Details.Parameters?.customerNumber;
    const dynamoDbRequest = {
        method: DynamoDbMethod.SCAN,
        tableName: 'sample_table',
        param: {
            FilterExpression: 'phoneNumber = :phoneNumber',
            ExpressionAttributeValues: {
                ':phoneNumber': customerNumber,
            },
        },
    };
    return await dynamoDbService.scanSample(dynamoDbRequest);
};
