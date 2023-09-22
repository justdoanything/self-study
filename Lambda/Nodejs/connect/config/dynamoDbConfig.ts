import { DynamoDbRequest } from '../model/config/dynamoDbModel';
import logger from './winstonConfig';
import { DynamoDbMethod } from '../enum/config/dynamoDbEnum';
import { DocumentClient } from 'aws-sdk/clients/dynamodb';
import { Aws } from '../enum/config/awsEnum';

const dynamoDbClient = new DocumentClient({ region: Aws.REGION });

export const callDynamoDb = async (request: DynamoDbRequest): Promise<any> => {
    logger.info(`Request DynamoDb : [${request.method}] ${JSON.stringify(request)}`);

    let response;
    switch (request.method) {
        case DynamoDbMethod.DELETE:
            if (!request.key) throw new Error("Missing 'key' parameter");
            break;

        case DynamoDbMethod.GET:
            if (!request.key) throw new Error("Missing 'key' parameter");
            break;

        case DynamoDbMethod.PUT:
            if (!request.key) throw new Error("Missing 'key' parameter");
            break;

        case DynamoDbMethod.QUERY:
            break;

        case DynamoDbMethod.SCAN:
            break;

        case DynamoDbMethod.UPDATE:
            if (!request.key) throw new Error("Missing 'key' parameter");
            break;

        default:
            break;
    }

    logger.info(`Response DynamoDb : [${request.method}] ${JSON.stringify(response)}`);

    return response;
};
