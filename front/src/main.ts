import axios from 'axios';
import App from './presentation/App.svelte';
import { ProjectApplicationService } from '@application/ProjectApplicationService';

import { AxiosClient } from '@infrastructure/AxiosClient';
import TailwindThemeConfiguration from '@infrastructure/TailwindThemeConfiguration';
import AxiosProjectRepository from '@infrastructure/AxiosProjectRepository';

const bffClient = new AxiosClient(
    axios.create({ baseURL: process.env.API_BASE_URL }),
);

const context = new Map();
const projectRepository = new AxiosProjectRepository(bffClient);
context.set(
    'ProjectApplicationService',
    new ProjectApplicationService(projectRepository),
);
context.set('Theme', new TailwindThemeConfiguration().getTheme());

const app = new App({
    context,
    target: document.body,
});

export default app;
