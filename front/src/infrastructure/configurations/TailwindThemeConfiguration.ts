import type { Screens } from './../../types/Theme';

import Theme, { type Colors } from 'types/Theme';
import resolveConfig from 'tailwindcss/resolveConfig';
import tailwindConfig from './TailwindConfiguration.js';

export default class TailwindThemeConfiguration {
  getTheme(): Theme {
    const config = resolveConfig(tailwindConfig);
    const rawColors = config.theme.colors;
    const rawScreens = config.theme.screens
    return new Theme(
      rawColors as Colors,
      rawScreens as Screens
    );
  }
}