import { Project } from "@src/model/project";
import { getProjects } from "@src/use-cases/get-projects.use-case";
import { Response, Router, raw } from "express";

const apiRouter = Router();

apiRouter.get("/projects", async (req, res: Response<Project[]>) => {
  const projects = await getProjects();
  res.json(projects);
  res.status(200);
});

export default apiRouter;
