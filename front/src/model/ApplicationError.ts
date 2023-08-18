export default abstract class ApplicationError {
  public constructor(readonly error: Error) {}

  public get message(): string {
    if (this.error instanceof Error) return this.error.message;
    else return undefined;
  }

  public get code(): string {
    if (this.error instanceof Error) return this.error.name;
    else return undefined;
  }
}
