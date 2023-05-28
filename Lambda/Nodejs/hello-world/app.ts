import { getReservation } from './src/scan.js';

export const lambdaHandler = async (event: any): Promise<any> => {
    const customerPhoneNumber = event.Details.ContactData.CustomerEndpoint.Address;
    let response;
    try {
        console.log(customerPhoneNumber);
        const responseBody = getReservation(customerPhoneNumber);
        response = {
            statusCode: 200,
            body: responseBody,
        };
    } catch (err: unknown) {
        console.log(err);
        response = {
            statusCode: 500,
            body: JSON.stringify({
                message: err instanceof Error ? err.message : 'some error happened',
            }),
        };
    }

    return response;
};
