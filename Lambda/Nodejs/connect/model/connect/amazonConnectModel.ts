export interface AmazonConnectRequest {
    Name: string;
    Details: {
        ContactData: {
            Attributes: Record<string, string>;
            Channel: string;
            ContactId: string;
            CustomerEndpoint: {
                Address: string;
                Type: string;
            };
        };
        Parameters?: Record<string, string> | null | undefined;
    };
}
