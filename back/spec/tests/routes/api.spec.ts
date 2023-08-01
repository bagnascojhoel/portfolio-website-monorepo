import supertest, { SuperTest, Test, Response } from "supertest";

import app from "@src/server";

import { Project } from "@src/model/project";
import * as GetProjectsUseCaseWrapper from "@src/use-cases/get-projects.use-case";
import exp from "constants";

const GET_PROJECTS_PATH = "/api/projects";

describe("Api", () => {
  let agent: SuperTest<Test>;

  beforeAll((done) => {
    agent = supertest.agent(app);
    done();
  });

  describe(`GET ${GET_PROJECTS_PATH}`, () => {
    const request = () => agent.get(GET_PROJECTS_PATH);

    it(`should return JSON content-type and status code of 200`, (done) => {
      spyOn(GetProjectsUseCaseWrapper, "getProjects").and.resolveTo();

      request().end((_: Error, res: Response) => {
        expect(res.status).toBe(200);

        expect(res.header["content-type"]).toContain("application/json");
        done();
      });
    });

    it(`should return one project object inside the array`, (done) => {
      const aProject: Project = {
        title: "This is Jhoel",
        tags: ["front-end", "svelte", "tailwindcss", "github-aip"],
        description:
          "This is my portfolio website. I wanted to learn new technologies, so I did it with Svelte and Tailwindcss. The projects are fetched through GitHub's API. Each project has a this-is-jhoel.json file which describes the card's content.",
        repositoryUrl:
          "https://github.com/bagnascojhoel/data-structure-and-algos",
        websiteUrl: "http://localhost:3000",
        projectId: "9863df78-d71d-4a66-81ea-581a70482b14",
      };
      const useCaseSpy = spyOn<any, any>(
        GetProjectsUseCaseWrapper,
        "getProjects"
      ).and.resolveTo([aProject]);

      request().end((_: Error, res: Response) => {
        expect(useCaseSpy).toHaveBeenCalled();
        expect(res.body).toHaveSize(1);
        expect(res.body[0]).toEqual(aProject);
        done();
      });
    });
  });
});
