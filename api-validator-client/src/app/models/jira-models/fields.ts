import { Issuetype } from "./Issuetype";
import { Project } from "./Project";

export interface Fields {
  summary : string;
  description : string;
  project : Project;
  issuetype : Issuetype;
}
