import { S3Request } from '../../model/config/s3Model';
import { callS3 } from '../../config/s3Config';

const s3Service = {
    sampleGet: async (request: S3Request): Promise<any> => {
        const response = await callS3(request);
        return response;
    },
};
export default s3Service;
