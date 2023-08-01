import { IUser } from "@src/models/User";
import "supertest";

declare module "supertest" {
  export interface Response {
    headers: Record<string, string[]>;
  }
}
