import { DynamoDbRequest } from '../model/config/dynamoDbModel';
import logger from './winstonConfig';
import { DynamoDbMethod } from '../enum/config/dynamoDbEnum';
import { DocumentClient } from 'aws-sdk/clients/dynamodb';
import { Aws } from '../enum/config/awsEnum';

const dynamoDbClient = new DocumentClient({ region: Aws.REGION });

export const callDynamoDb = async (request: DynamoDbRequest): Promise<any> => {
    logger.info(`Request DynamoDb : [${request.method}] ${JSON.stringify(request)}`);

    let response;

    try {
        switch (request.method) {
            case DynamoDbMethod.DELETE:
                if (!request.key) throw new Error(`Missing 'key' parameter`);
                response = await dynamoDbClient
                    .delete({
                        TableName: request.tableName,
                        Key: request.key,
                        ...request.param,
                    })
                    .promise();
                break;

            case DynamoDbMethod.GET:
                if (!request.key) throw new Error(`Missing 'key' parameter`);
                response = await dynamoDbClient
                    .get({
                        TableName: request.tableName,
                        Key: request.key,
                        ...request.param,
                    })
                    .promise();
                break;

            case DynamoDbMethod.PUT:
                if (!request.item) throw new Error(`Missing 'item' parameter`);
                response = await dynamoDbClient
                    .put({
                        TableName: request.tableName,
                        Item: request.item,
                        ...request.param,
                    })
                    .promise();
                break;

            case DynamoDbMethod.QUERY:
                response = await dynamoDbClient
                    .query({
                        TableName: request.tableName,
                        ...request.param,
                    })
                    .promise();
                break;

            case DynamoDbMethod.SCAN:
                response = await dynamoDbClient
                    .scan({
                        TableName: request.tableName,
                        ...request.param,
                    })
                    .promise();
                break;

            case DynamoDbMethod.UPDATE:
                if (!request.key) throw new Error(`Missing 'key' parameter`);
                response = await dynamoDbClient
                    .update({
                        TableName: request.tableName,
                        Key: request.key,
                        ...request.param,
                    })
                    .promise();
                break;

            default:
                break;
        }
    } catch (exception) {
        logger.error(`Exception : ${exception}`);
        throw new Error(`${exception}`);
    }

    logger.info(`Response DynamoDb : [${request.method}] ${JSON.stringify(response)}`);

    return response;
};
