import { ScanCommand } from '@aws-sdk/client-dynamodb';
import { dbClient } from './libs/dynamoClient.js';

const tableName = 'beaver_amazon_connect_reservation';

export const getReservation = async (customerPhoneNumber) => {
  try {
    const response = await dbClient.send(
      new ScanCommand({
        TableName: tableName,
        ExpressionAttributeValues: {
          ':pn:': customerPhoneNumber,
        },
        FilterExpression: 'phoneNumber= :pn',
      })
    );
    return response;
  } catch (err) {
    console.log('Exception : ' + err);
  }
};
