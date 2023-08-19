export interface Operation {
  id: number;
  url: string;
  type: string;
  body: string;
  expectedResponse: string;
  actualResponse: string;
  expectedType: string;
}
