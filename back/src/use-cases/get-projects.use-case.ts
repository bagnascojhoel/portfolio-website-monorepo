import { GithubProjectDescription } from "@src/model/github-project-description";
import { Project } from "@src/model/project";
import { getGithubRepositories } from "@src/repositories/github-repository.repository";
import { getGithubProjectDescription } from "@src/repositories/project-description-file.repository";

export async function getProjects(): Promise<Project[]> {
  const githubRepositories = await getGithubRepositories();
  const descriptionPromises: Promise<GithubProjectDescription | undefined>[] =
    githubRepositories.map((repo) =>
      getGithubProjectDescription(repo.repositoryId)
    );

  return (await Promise.all(descriptionPromises))
    .filter((desc) => desc !== undefined)
    .map((desc) => ({
      projectId: desc!.id,
      description: desc!.description,
      repositoryUrl: desc!.githubUrl,
      tags: desc!.tags,
      title: desc!.title,
      websiteUrl: desc!.websiteUrl,
    }));
}
