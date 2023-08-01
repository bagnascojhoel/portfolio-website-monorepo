import morgan from "morgan";
import helmet from "helmet";
import express, { Request, Response, NextFunction } from "express";
import swaggerUi from "swagger-ui-express";
import yamlParser from "yaml";
import fs from "fs";
import "express-async-errors";

import ApiRouter from "@src/routes/api";
import EnvVars from "@src/constants/EnvVars";

const swaggerFile = fs.readFileSync("src/swagger.yaml", "utf8");
const swaggerDocs = yamlParser.parse(swaggerFile);

const app = express();

// **** Setup **** //

// Basic middleware
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Show routes called in console during development
if (EnvVars.NODE_ENV === "development") {
  app.use(morgan("dev"));
}

// Security
if (EnvVars.NODE_ENV === "production") {
  app.use(helmet());
}

// Add APIs, must be after middleware
app.use("/api/meta", swaggerUi.serve, swaggerUi.setup(swaggerDocs));

app.use("/api/", ApiRouter);

// Add error handler
app.use(
  (
    err: any,
    _: Request,
    res: Response,
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    next: NextFunction
  ) => {
    let status = 400;
    if (err?.status) {
      status = err.status;
    }
    return res.status(status).json({ error: err.message });
  }
);

// **** Export default **** //

export default app;
