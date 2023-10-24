# My Portfolio

My personal portfolio website. The cards are built with information gathered from my public repositories on GitHub. Each project has its own description file, called `this-is-jhoel.json`. It is published with GitHub Pages.

## Description file template

```json
{
  "title": "My Portfolio",
  "tags": ["front-end", "svelte"],
  "description": "My description"
}

```

## Contributing

This project uses conventional commits. The types currently allowed are:

- build: Changes that affect the build system or external dependencies (example scopes: gulp, broccoli, npm)
- ci: Changes to our CI configuration files and scripts (example scopes: Travis, Circle, BrowserStack, SauceLabs)
- docs: Documentation only changes
- feat: A new feature
- fix: A bug fix
- perf: A code change that improves performance
- refactor: A code change that neither fixes a bug nor adds a feature
- style: Changes that do not affect the meaning of the code (white-space, formatting, missing semi-colons, etc)
- test: Adding missing tests or correcting existing tests

> Make sure to setup the githooks which will enforce this pattern on pre-commit.

## Front-end

### Technologies

Since the project premisse was a simple website to showcase my projects, I chooose technologies which allowed me a fast development and cheap deployment:

- Svelte
- Tailwind CSS
- Typescript

### Running it

To build it you will need node (recommended `v12.22.8`). Then clone this repository, access its folder and run:

```bash
npm run watch:local
```