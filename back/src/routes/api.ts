import { project } from "@src/model/project";
import { Response, Router, raw } from "express";

const apiRouter = Router();

apiRouter.get("/projects", (req, res: Response<project[]>) => {
  res.json([
    {
      title: "This is Jhoel",
      tags: ["front-end", "svelte", "tailwindcss", "github-aip"],
      description:
        "This is my portfolio website. I wanted to learn new technologies, so I did it with Svelte and Tailwindcss. The projects are fetched through GitHub's API. Each project has a this-is-jhoel.json file which describes the card's content.",
      githubUrl: "https://github.com/bagnascojhoel/data-structure-and-algos",
      websiteUrl: "http://localhost:3000",
      projectId: "9863df78-d71d-4a66-81ea-581a70482b14",
    },
  ]);
});

export default apiRouter;
