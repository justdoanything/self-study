import AWS from 'aws-sdk';
import { RdsMethod } from '../../enum/config/rdsEnum';

export interface RdsRequest {
    method: RdsMethod;
    query: string;
    param: Array<AWS.RDSDataService.SqlParameter>;
}
