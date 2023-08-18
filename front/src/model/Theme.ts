export type Colors = {
  primary: string;
  'on-primary': string;
  'primary-variant': string;
  background: string;
  'on-background': string;
};

export type Screens = {
  sm: string;
  md: string;
  lg: string;
  '2xl': string;
  xl: string;
};

export default class Theme {
  constructor(readonly colors: Colors, readonly screens: Screens) {}

  getScreenValue(screen: keyof Screens): number {
    return Number.parseInt(this.screens[screen].slice(0, -2));
  }
}
