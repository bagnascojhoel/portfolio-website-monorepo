import type { Project } from 'model/Project';

export default interface ProjectService {
  findAll(): Promise<Project[]>;
}
