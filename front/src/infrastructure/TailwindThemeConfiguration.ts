import type { Screens } from '@domain/Theme';

import Theme, { type Colors } from '@domain/Theme';
import resolveConfig from 'tailwindcss/resolveConfig';
import tailwindConfig from './tailwind-configuration.js';

export default class TailwindThemeConfiguration {
    getTheme(): Theme {
        const config = resolveConfig(tailwindConfig);
        const rawColors = config.theme.colors;
        const rawScreens = config.theme.screens;
        return new Theme(rawColors as Colors, rawScreens as Screens);
    }
}
