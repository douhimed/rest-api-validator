import { TestBed } from '@angular/core/testing';

import { JiraTicketService } from './jira-ticket.service';

describe('JiraTicketService', () => {
  let service: JiraTicketService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JiraTicketService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
