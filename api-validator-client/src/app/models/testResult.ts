import { ResponseDto } from "./ResponseDto";

export class TestResult {
  id!: number;
  name:string = "";
  responseDto: ResponseDto[] = [];
}
