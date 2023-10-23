import axios from 'axios';
import App from './App.svelte';

import AxiosClient from '@infrastructure/AxiosClient';
import TailwindThemeConfiguration from '@infrastructure/TailwindThemeConfiguration';
import AxiosProjectService from '@infrastructure/AxiosProjectService';

const bffClient = new AxiosClient(
    axios.create({ baseURL: process.env.API_BASE_URL }),
);

const context = new Map();
context.set('ProjectService', new AxiosProjectService(bffClient));
context.set('Theme', new TailwindThemeConfiguration().getTheme());

const app = new App({
    context,
    target: document.body,
});

export default app;
