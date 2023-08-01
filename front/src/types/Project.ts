export default class Project {
    constructor(
        readonly projectId: string,
        readonly title: string,
        readonly description: string,
        readonly tags: string[],
        readonly githubUrl: string,
        readonly websiteUrl?: string
    ) {

    }
}