import type { Project } from '@domain/Project';

export interface ProjectRepository {
    findAll(): Promise<Project[]>;
}
