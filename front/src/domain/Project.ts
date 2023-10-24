export type Project = {
    projectId: string;
    title: string;
    description: string;
    tags: string[];
    githubUrl: string;
    websiteUrl?: string;
    complexity: Complexity;
    lastChangedDateTime: Date;
    startsOpen: boolean;
};

type Complexity = {
    code: string;
    text: string;
};

export const ComplexityCode = {
    'complexity-extreme': 3,
    'complexity-high': 2,
    'complexity-medium': 1,
    'complexity-low': 0,
};
