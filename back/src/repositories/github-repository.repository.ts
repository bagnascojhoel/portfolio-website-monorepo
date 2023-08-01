import { GithubRepository } from "@src/model/github-repository";

const DEFAULT_PER_PAGE = 30;
const USERNAME = "bagnascojhoel";

export async function getGithubRepositories(): Promise<GithubRepository[]> {
  const res = await fetch(
    `/users/${USERNAME}/repos?per_page=${DEFAULT_PER_PAGE}&sort=pushed&direction=desc`
  );
  const json: { name: string; html_url: string }[] = await res.json();

  return json.map(({ name, html_url }) => ({
    repositoryId: name,
    url: html_url,
  }));
}
