import { callRds } from '../../config/rdsConfig';
import { RdsRequest } from '../../model/config/rdsModel';

const rdsService = {
    selectSample: async (request: RdsRequest): Promise<any> => {
        const response = await callRds(request);
        return response;
    },
};

export default rdsService;
