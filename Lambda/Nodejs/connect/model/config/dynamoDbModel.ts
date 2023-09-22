import { DynamoDbMethod } from '../../enum/config/dynamoDbEnum';

export interface DynamoDbRequest {
    method: DynamoDbMethod;
    tableName: string;
    key?: { [key: string]: any };
    item?: { [key: string]: any };
    param?: any;
}
