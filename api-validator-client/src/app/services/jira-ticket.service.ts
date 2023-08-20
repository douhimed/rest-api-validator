import { HttpClient} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { jiraPayload } from '../models/jira-models/jiraPayload';

@Injectable({
  providedIn: 'root'
})
export class JiraTicketService {

  private baseUrl = 'http://localhost:8080/bugs';

  constructor(private http: HttpClient) { }

  sendJiraTicket(payload: jiraPayload): Observable<any> {
    return this.http.post<any>(this.baseUrl, payload);
  }
}
