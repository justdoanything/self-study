import AWS from 'aws-sdk';
import { Aws } from '../enum/config/awsEnum';
import { S3Request } from '../model/config/s3Model';
import { S3Method } from '../enum/config/s3Enum';
import logger from './winstonConfig';

const s3Client = new AWS.S3({
    region: Aws.REGION,
});

export const callS3 = async (s3Request: S3Request): Promise<any> => {
    let response;

    try {
        switch (s3Request.method) {
            case S3Method.GET:
                response = await s3Client
                    .getObject({
                        Bucket: s3Request.bucket,
                        Key: s3Request.key,
                    })
                    .promise();
                break;

            case S3Method.PUT:
                if (!s3Request.body) throw new Error(`Missing 'body' parameter`);
                response = await s3Client
                    .putObject({
                        Bucket: s3Request.bucket,
                        Key: s3Request.key,
                        Body: s3Request.body,
                    })
                    .promise();
                break;

            case S3Method.DELETE:
                response = await s3Client
                    .deleteObject({
                        Bucket: s3Request.bucket,
                        Key: s3Request.key,
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

    return response;
};
