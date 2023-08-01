export type project = {
  readonly projectId: string;
  readonly title: string;
  readonly description: string;
  readonly tags: string[];
  readonly githubUrl: string;
  readonly websiteUrl?: string;
};
