export type Project = {
  readonly projectId: string;
  readonly title: string;
  readonly description: string;
  readonly tags: string[];
  readonly repositoryUrl: string;
  readonly websiteUrl?: string;
};
