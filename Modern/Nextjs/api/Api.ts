import {callApi, Method} from "./ApiUtil";

const post = {
    findPost: async (): Promise<any> => {
        return callApi({
            url: "https://jsonplaceholder.typicode.com/posts",
            method: Method.GET
        });
    }
};

export default post;