import type { AxiosClient } from '@infrastructure/AxiosClient';
import type { Project } from '@domain/Project';
import type { ProjectRepository } from '@domain/ProjectRepository';
import UnrecoverableError from '@domain/UnrecoverableError';

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

export default class AxiosProjectRepository implements ProjectRepository {
    private readonly httpClient: AxiosClient;

    constructor(httpClient: AxiosClient) {
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
