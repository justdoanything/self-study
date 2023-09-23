import AWS from 'aws-sdk';
import { RdsRequest } from '../model/config/rdsModel';
import logger from './winstonConfig';
import { RdsMethod } from '../enum/config/rdsEnum';
import { Aws } from '../enum/config/awsEnum';

const rdsClient = new AWS.RDSDataService({ region: Aws.REGION });

const database = process.env.RDS_DATABASE || '';
const resourceArn = process.env.RDS_RESOURCE_ARN || '';
const secretArn = process.env.RDS_SECRET_ARN || '';

export const callRds = async (rdsRequest: RdsRequest): Promise<any> => {
    logger.info(`Request RdsDb : [${rdsRequest.method}] ${JSON.stringify(rdsRequest)}`);

    try {
        switch (rdsRequest.method) {
            case RdsMethod.SELECT:
                if (!rdsRequest.query.toUpperCase().startsWith(RdsMethod.SELECT))
                    throw new Error(`SELECT Query must be started with 'SELECT'`);
                break;

            case RdsMethod.INSERT:
                if (!rdsRequest.query.toUpperCase().startsWith(RdsMethod.INSERT))
                    throw new Error(`INSERT Query must be started with 'INSERT'`);
                break;

            case RdsMethod.UPDATE:
                if (!rdsRequest.query.toUpperCase().startsWith(RdsMethod.UPDATE))
                    throw new Error(`UPDATE Query must be started with 'UPDATE'`);
                break;

            case RdsMethod.DELETE:
                if (!rdsRequest.query.toUpperCase().startsWith(RdsMethod.DELETE))
                    throw new Error(`DELETE Query must be started with 'DELETE'`);
                break;
        }

        const response = await rdsClient
            .executeStatement({
                database: database,
                resourceArn: resourceArn,
                secretArn: secretArn,
                sql: rdsRequest.query,
                parameters: rdsRequest.param,
            })
            .promise();

        logger.info(`Response RdsDb : [${rdsRequest.method}] ${JSON.stringify(response.records || [])}`);

        return response.records || [];
    } catch (exception) {
        logger.error(`Exception : ${exception}`);
        throw new Error(`${exception}`);
    }
};
