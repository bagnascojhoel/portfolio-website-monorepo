import type BffAxiosClient from '@infra/common/AxiosClient';
import type { Project } from 'model/Project';
import type ProjectService from '@model/ProjectService';
import UnrecoverableError from '@model/UnrecoverableError';

type ProjectsDto = {
  projects: ProjectDto[];
};

type ProjectDto = {
  uniqueName: string;
  title: string;
  description: string;
  tags: string[];
  repositoryUrl: string;
  websiteUrl?: string;
};

export default class AxiosProjectService implements ProjectService {
  private readonly httpClient: BffAxiosClient;

  constructor(httpClient: BffAxiosClient) {
    this.httpClient = httpClient;
  }

  async findAll(): Promise<Project[]> {
    let response = await this.httpClient.get('/projects');

    let responseBody: ProjectsDto;
    if (response.status === 200) {
      responseBody = response.data;
    } else {
      throw new UnrecoverableError({
        name: 'error-getting-projects',
        message: 'Could not get projects from BFF',
      });
    }

    return responseBody.projects.map((dto) => ({
      projectId: dto.uniqueName,
      githubUrl: dto.repositoryUrl,
      ...dto,
    }));
  }
}
