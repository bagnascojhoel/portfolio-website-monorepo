import App from './App.svelte';

import GitHubAxiosProjectService from '@infrastructure/services/GitHubAxiosProjectService';
import GitHubAxiosClient from '@infrastructure/common/GitHubAxiosClient';
import TailwindThemeConfiguration from '@infrastructure/configurations/TailwindThemeConfiguration';

if (process.env.MOCK_ENABLED === 'true') {
  const worker = require('./infrastructure/mocks/server');
  worker.default.start();
}

const githubClient = new GitHubAxiosClient(process.env.GITHUB_URL);

const context = new Map();
context.set('ProjectService', new GitHubAxiosProjectService(githubClient));
context.set('Theme', new TailwindThemeConfiguration().getTheme());

const app = new App({
  context,
  target: document.body
});

export default app;
