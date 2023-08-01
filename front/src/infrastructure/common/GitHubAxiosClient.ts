import GithubApiErrorStore from "@stores/github-api-error";
import AbstractAxiosClient from "./AxiosClient";

export default class GitHubAxiosClient extends AbstractAxiosClient {
    constructor(githubUrl: string) {
        super({ errorHandler: GithubApiErrorStore, baseURL: githubUrl });
    }
}