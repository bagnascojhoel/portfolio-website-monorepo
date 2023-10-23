import type {
    AxiosInstance,
    AxiosResponse,
    Method,
    AxiosRequestConfig,
} from 'axios';

class DefaultRequestConfig {
    useGlobalErrorHandler?: boolean = true;
}

export class AxiosClient {
    private readonly axios: AxiosInstance;

    constructor(axios: AxiosInstance) {
        this.axios = axios;
    }

    get(path, config?: AxiosRequestConfig): Promise<AxiosResponse> {
        return this.request('GET', path, config);
    }

    private async request(
        method: Method,
        path: string,
        config?: AxiosRequestConfig,
    ) {
        const actualConfig = { ...new DefaultRequestConfig(), ...config };

        return await this.axios.request({
            method,
            url: path,
            ...actualConfig,
        });
    }
}
