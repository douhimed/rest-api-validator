import {TestResult} from '../models/testResult';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Project} from '../models/project';
import {Observable} from 'rxjs';
import {Operation} from "../models/Operation";
import {ResponseDto} from "../models/ResponseDto";

@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  private backendHost = "http://localhost:8080/project"

  constructor(private http: HttpClient) {
  }

  getProjectsList(): Observable<Project[]> {
    return this.http.get<Project[]>(`${this.backendHost}`)
  }

  runProjectTest(id: number): Observable<TestResult> {
    return this.http.get<TestResult>(`${this.backendHost}/${id}/tests`)
  }

  public saveProject(project: Project): Observable<Project> {
    return this.http.post<Project>(`${this.backendHost}`, project);
  }

  public updateProject(project: Project): Observable<Project> {
    const url = `${this.backendHost}/${project.id}`;
    return this.http.put<Project>(url, project);
  }

  getProjectById(id: number): Observable<Project> {
    const url = `${this.backendHost}/${id}`;
    return this.http.get<Project>(url);
  }

  public deleteProject(id: number): Observable<any> {
    const url = `${this.backendHost}/${id}`;
    return this.http.delete(url);
  }

  getAllOperationsByProjectId(projectId: number): Observable<Operation[]> {
    const url = `${this.backendHost}/${projectId}`;
    return this.http.get<Operation[]>(url);
  }

  compareJson(id: number): Observable<TestResult> {
    return this.http.get<TestResult>(`${this.backendHost}/${id}/rapport`)
  }

}

