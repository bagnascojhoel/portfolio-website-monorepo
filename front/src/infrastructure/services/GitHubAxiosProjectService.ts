import type GitHubAxiosClient from '@infrastructure/common/GitHubAxiosClient';
import Project from 'types/Project';
import type ProjectService from '@services/ProjectService';

const DEFAULT_PER_PAGE = 30;
const USERNAME = 'bagnascojhoel';
const DESCRIPTION_FILE_NAME = "portfolio-description.json";

type GithubRepositoryDto = {
  repositoryId: string
  url: string
}

type ThisIsJhoelDto = {
  title: string
  tags: string[]
  description: string
  githubUrl: string
  websiteUrl?: string
}
// TODO Add proxy layer for localStorage cache
export default class GitHubAxiosProjectService implements ProjectService {
  private readonly githubClient: GitHubAxiosClient;

  constructor(githubClient: GitHubAxiosClient) {
    this.githubClient = githubClient;
  }

  private async findRepositories(): Promise<GithubRepositoryDto[]> {
    const response = await this.githubClient.get(
      `/users/${USERNAME}/repos?per_page=${DEFAULT_PER_PAGE}&sort=pushed&direction=desc`
    );
    return response.data.map(({ name, html_url }) => ({ repositoryId: name, url: html_url }));
  }

  async *findAll(): AsyncGenerator<Project> {
    const repositories = await this.findRepositories();

    for (let repo of repositories) {
      let response;
      try {
        response = await this.githubClient.get(`/repos/${USERNAME}/${repo.repositoryId}/contents/${DESCRIPTION_FILE_NAME}`, {
          useGlobalErrorHandler: false,
        });
      } catch (error) {
        continue;
      }

      const content: ThisIsJhoelDto = JSON.parse(atob(response.data.content));
      yield new Project(
        repo.repositoryId,
        content.title,
        content.description,
        content.tags,
        repo.url,
        content.websiteUrl
      )
    }
  }
}
