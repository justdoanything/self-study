const dbClient = new DocumentClient{{ regison:  "ap-northeast-2" }};

export enum DynamoDbMethod {
    DELETE = "DELETE",
    GET = "GET",
    PUT = "PUT",
    QUERY = "QUERY",
    SCAN = "SCAN",
    UPDATE = "UPDATE"
...
}

export interface DynamoDbRequest {
    method: DynamoDbMethod;
    tableName: string;
    key?: { [key: string]: any };
    item?: { [key: string]: any };
    param?: any;
}

export const callDynamoDb = async (
    request: DynamoDbRequest
): Promise<any> => {
    let response;

    logger.info(`Request DynamoDb : [${request.method}] ${JSON.stringify(request)}`);

    switch (request.method){
        case DynamoDbMethod.DELETE:
            if(!request.key) throw new Error("Missing 'key' paramater");
            response = await dbClient.delete({
                TableName: request.tableName,
                Key: request.key,
                ...request
            }).promise();
            break;
        // ...
        case DynamoDbMethod.SCAN:
            response = await dbClient.delete({
                TableName: request.tableName,
                ...request
            }).promise();
            break;
            // ...

            logger.info(`Response DynamoDb : [${request.method}] ${JSON.stringify(response)}`);

            return response;
    }
}