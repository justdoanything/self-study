import { S3Method } from '../../enum/config/s3Enum';

export interface S3Request {
    method: S3Method;
    bucket: string;
    key: string;
    body?: string;
}
