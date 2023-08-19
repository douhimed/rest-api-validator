import { Component, OnInit } from '@angular/core';
import { ActivatedRoute} from '@angular/router';
import { TestResult } from 'src/app/models/testResult';
import { ProjectService } from 'src/app/services/project.service';
import { OperationService } from 'src/app/services/operation.service';
import { JiraTicketService } from 'src/app/services/jira-ticket.service';
import { Fields } from 'src/app/models/jira-models/fields';
import { Messages } from 'src/app/models/Messages';
import { jiraPayload } from 'src/app/models/jira-models/jiraPayload';
import Swal from 'sweetalert2';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-project-tests',
  templateUrl: './project-tests.component.html',
  styleUrls: ['./project-tests.component.css'],
})
export class ProjectTestsComponent implements OnInit {
  id!: number;
  testResult!: TestResult;

  jiraModel: Fields = { summary: '', description: '', project: { key: '' }, issuetype: { id: '' } };
  jiraPayload:jiraPayload={fields:{ summary: '', description: '', project: { key: '' }, issuetype: { id: '' } }};

  isCollapsed = true;

  constructor(private projectService: ProjectService,private operationService: OperationService , private toastr: ToastrService ,private jiraTicketService: JiraTicketService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.loadProjectTestResults();
  }

  loadProjectTestResults(): void {
    this.projectService.runProjectTest(this.id).subscribe((data) => {

      for(let i=0;i<data.responseDto.length;i++){
        for(let j=0;j<data.responseDto[i].messages.length;j++){
          data.responseDto[i].messages[j].value = JSON.stringify(data.responseDto[i].messages[j].value)
        }
      }

      this.testResult = data
    });
  }

  handleUpdateExpectedResponse(id: number, newExpectedResponse: string): void {
    this.operationService.updateExcpectedResponse(id, newExpectedResponse).subscribe(
      (data) => {
        this.toastr.success('Expected response updated successfully', 'Success');
        this.loadProjectTestResults();
      },
      (error) => {
        this.toastr.error('Failed to update expected response: '+error.error.message, 'Error');
      }
    );
  }

  //JIRA
  sendJiraTicket(type:string,url:string,messages:Messages[]) {

    this.jiraModel.summary=`${type} : ${url}`
    this.jiraModel.project.key=`AP`

    let desc: string = '';
    for (let i = 0; i < messages.length; i++) {
      const message = messages[i];
      desc += `Rapport N°${i} :
              Operation : ${message.op ?? ''}
              Path : ${message.path ?? ''}
              Value : ${message.value ?? ''}
              -----------------------------\n`;
    }

    this.jiraModel.description=`${desc}`
    this.jiraModel.issuetype.id=`10029`

    this.jiraPayload.fields=this.jiraModel

    this.jiraTicketService.sendJiraTicket(this.jiraPayload).subscribe(
      (response) => {
        this.showSuccessAlert();
        console.log('Jira ticket sent successfully!', response);
      },
      (error) => {
        this.showErrorAlert();
        console.error('Error sending Jira ticket:', error);
      }
    )
  }

  toggleCollapse(responseDto: any) {
    responseDto.isCollapsed = !responseDto.isCollapsed;
  }

  showSuccessAlert() {
    this.toastr.success('Jira ticket sent successfully', '');
  }

  showErrorAlert() {
    this.toastr.error('Failed to send Jira ticket. Please try again later', '');
}
}
