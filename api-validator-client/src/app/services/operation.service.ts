import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Operation} from "../models/Operation";
import {Project} from "../models/project";

@Injectable({
  providedIn: 'root'
})
export class OperationService {
  private baseUrl = 'http://localhost:8080/operation';
  private baseUrlV2 = 'http://localhost:8080/project';

  constructor(private http: HttpClient) {
  }

  getAllOperationsByProjectId(projectId: number): Observable<Project> {
    const url = `${this.baseUrlV2}/${projectId}`;
    return this.http.get<Project>(url);
  }

  addOperation(projectId: string, operation: Operation): Observable<number> {
    const url = `${this.baseUrl}?projectId=${projectId}`;
    return this.http.post<number>(url, operation);
  }

  public deleteOperation(id: number): Observable<any> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.delete(url);
  }

  updateOperation(operation: { id: number; url: any; body: any; type: any; expectedType: any }): Observable<Operation> {
    const url = `${this.baseUrl}/${operation.id}`;
    return this.http.put<Operation>(url, operation);
  }

  getOperationById(id: number): Observable<Operation> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.get<Operation>(url);
  }
  getProjectById(projectId: number): Observable<Project> {
    const url = `${this.baseUrlV2}${projectId}`;
    return this.http.get<Project>(url);
  }

updateExcpectedResponse(id : number,newExcpectedResonse : string) : Observable<Operation>{
  const url = `${this.baseUrl}/${id}`;
  return this.http.patch<Operation>(url,newExcpectedResonse);
}
}
