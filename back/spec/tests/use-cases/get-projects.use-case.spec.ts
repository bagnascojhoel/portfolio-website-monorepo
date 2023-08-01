import { Project } from "@src/model/project";
import { getProjects } from "@src/use-cases/get-projects.use-case";
import * as CodeRepositoryRepository from "@src/repositories/github-repository.repository";
import * as ProjectDescriptionFileRepository from "@src/repositories/project-description-file.repository";
import { GithubRepository } from "@src/model/github-repository";
import { GithubProjectDescription } from "@src/model/github-project-description";

describe("GetProjectsUseCase", () => {
  it("should return list of projects", async () => {
    const codeRepository: GithubRepository = {
      repositoryId: "project1",
      url: "repository-url",
    };
    const projectDescriptionFile: GithubProjectDescription = {
      id: "project1",
      description: "description",
      githubUrl: "repository-url",
      tags: ["tag1"],
      title: "title",
      websiteUrl: "website-url",
    };
    const expected: Project[] = [
      {
        title: "title",
        tags: ["tag1"],
        description: "description",
        repositoryUrl: "repository-url",
        websiteUrl: "website-url",
        projectId: "project1",
      },
    ];

    spyOn(
      ProjectDescriptionFileRepository,
      "getGithubProjectDescription"
    ).and.resolveTo(projectDescriptionFile);
    spyOn(CodeRepositoryRepository, "getGithubRepositories").and.resolveTo([
      codeRepository,
    ]);
    const actual = await getProjects();

    expect(actual).toEqual(expected);
  });
});
