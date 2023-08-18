import type { AxiosInstance, AxiosResponse, Method } from 'axios';
import type { RequestConfig } from './api';

class DefaultRequestConfig implements RequestConfig {
  useGlobalErrorHandler?: boolean = true;
}

export default class AxiosClient {
  private readonly axios: AxiosInstance;

  constructor(axios: AxiosInstance) {
    this.axios = axios;
  }

  get(path, config?: RequestConfig): Promise<AxiosResponse> {
    return this.request('GET', path, config);
  }

  post(path, config?: RequestConfig) {
    return this.request('POST', path, config);
  }

  put(path, config?: RequestConfig) {
    return this.request('PUT', path, config);
  }

  delete(path, config?: RequestConfig) {
    return this.request('DELETE', path, config);
  }

  private async request(method: Method, path: string, config?: RequestConfig) {
    const actualConfig = { ...new DefaultRequestConfig(), ...config };

    return await this.axios.request({
      method,
      url: path,
      ...actualConfig,
    });
  }
}
