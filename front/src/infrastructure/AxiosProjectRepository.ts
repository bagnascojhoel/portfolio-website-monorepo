import type { AxiosClient } from '@infrastructure/AxiosClient';
import type { Project } from '@domain/Project';
import type { ProjectRepository } from '@domain/ProjectRepository';
import UnrecoverableError from '@domain/UnrecoverableError';

type ProjectsDto = {
    projects: ProjectDto[];
};

type ProjectDto = {
    repositoryId: string;
    title: string;
    description: string;
    tags: string[];
    repositoryUrl: string;
    websiteUrl?: string;
    startsOpen: boolean;
    lastChangedDateTime: Date;
    complexity: ComplexityDto;
};

type ComplexityDto = {
    code: string;
    text: string;
};

export default class AxiosProjectRepository implements ProjectRepository {
    constructor(private readonly httpClient: AxiosClient) {}

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
            ...dto,
            projectId: dto.repositoryId,
            githubUrl: dto.repositoryUrl,
            lastChangedDateTime: new Date(dto.lastChangedDateTime),
        }));
    }
}
