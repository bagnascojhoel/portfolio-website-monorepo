# My Portfolio Website

This project is composed of three applications:

- front-end: 
  - written with Svelte
  - built with Webpack
  - styled with Tailwindcss
  - structured with Ports and Adapters architecture
  - deployed to GitHub Pages

- back-end for front-end (bff):
  - written in Java, with Spring Boot
  - uses the GitHub API to fetch my repositories and projects to be shown in the UI
  - uses Spring Reactive to asynchronously fetch the extra information from a portfolio-description.json file on each repository
  - uses scheduling and cache to ensure some freshness and quick response times
  - architected with MVC, since its scope is too small for Clean Architecture or Ports and Adapters 
  - compiled to a Native Image to reduce the memory usage on fly.io (otherwise it went past the memory limit and the server dropped)
  - deployed to fly.io
- blog:
  - monolith written with JavaScript and Next.js
  - uses the Notion API to load the articles
  - deployed to Vercel

Since I am the only person working on it, I choose to use a monorepo for all of them.
Each one of the applications is built and deployed through a CI pipeline built with GitHub Actions.

## Extra Portfolio Description

The BFF reads a file from the repositories root path with name `portfolio-description.json`. If that
 file is missing, the repository is not returned. That file must follow this template:

```json
{
  "title": "my title", // required
  "description" "my description", // optional, default uses the description from repository
  "tags": ["tag"], // optional, default uses topics from repository
  "websiteUrl": "website url", // optional, default uses website url from repository
  "startsOpen": true // optional, default is false
  "complexity": "HIGH", // optional, default is MEDIUM
}
```

## Contributing

This project uses conventional commits.

> Make sure to setup the githooks which will enforce this pattern on pre-commit.

The types currently allowed are:

- build: Changes that affect the build system or external dependencies (example scopes: gulp, broccoli, npm)
- ci: Changes to our CI configuration files and scripts (example scopes: Travis, Circle, BrowserStack, SauceLabs)
- docs: Documentation only changes
- feat: A new feature
- fix: A bug fix
- perf: A code change that improves performance
- refactor: A code change that neither fixes a bug nor adds a feature
- style: Changes that do not affect the meaning of the code (white-space, formatting, missing semi-colons, etc)
- test: Adding missing tests or correcting existing tests

The scopes allowed are:

- bff: changes on `bff` folder
- front: changes on `front` folder
- blog: changes on `blog` folder
- docs: changes on `docs` folder
- all: changes that affect all applications