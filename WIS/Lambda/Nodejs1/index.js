import { getReservation } from './src/scan.js';

exports.handler = (event, context, callback) => {
  const customerPhoneNumber =
    event.Details.ContactData.CustomerEndpoint.Address;

  const response = getReservation(customerPhoneNumber);

  return response;
  // callback(null, response);
};
