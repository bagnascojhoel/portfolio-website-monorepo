import type { Project } from '@domain/Project';

import type { ProjectRepository } from '@domain/ProjectRepository';

export class ProjectApplicationService {
    constructor(readonly projectRepository: ProjectRepository) {}

    public async getProjects(): Promise<Project[]> {
        return await this.projectRepository.findAll();
    }
}
