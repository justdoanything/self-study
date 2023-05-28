import { DynamoDBClient } from '@aws-sdk/client-dynamodb';
const REGION = 'ap-northeast-2';
export const dbClient = new DynamoDBClient({ region: REGION });
