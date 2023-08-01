import type Project from "types/Project";

export default interface ProjectService {
    findAll(): AsyncGenerator<Project>;
}