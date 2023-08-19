import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {OperationService} from '../../services/operation.service';
import {JsonFormatPipe} from "../../json-format.pipe";
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-operation-details',
  templateUrl: './operation-details.component.html',
  styleUrls: ['./operation-details.component.css'],
  providers: [JsonFormatPipe],
})
export class OperationDetailsComponent implements OnInit {
  operationId!: number;
  operationDetails = [
    {
      title: 'URL:',
      content: '',
      collapsed: true
    },
    {
      title: 'Type:',
      content: '',
      collapsed: true
    },
    {
      title: 'Body:',
      content: '',
      collapsed: true
    },
    {
      title: 'Expected Type:',
      content: '',
      collapsed: true
    },
    {
      title: 'Expected Response:',
      content: '',
      collapsed: true
    },
  ];
  operation: any = {
    url: 'https://api-validator.com/api',
    type: 'GET',
    body: '{}',
    expectedType: '{}',
    expectedResponse: '{}',
    actualResponse: '{}'

  };

  constructor(
    private route: ActivatedRoute,
    private operationService: OperationService,
    private toastr: ToastrService
  ) {
  }

  ngOnInit(): void {
    this.operationId = this.route.snapshot.params['id'];
    this.operationService.getOperationById(this.operationId).subscribe(
      (operation) => {
        this.operation = operation;
      },
      (error) => {
        this.toastr.error('Erreur lors de la récupération de l\'opération : '+error.error.message, 'Error');
      }
    );
  }

  toggleCollapse(detail: any): void {
    detail.collapsed = !detail.collapsed;
    if (!detail.collapsed) {
      switch (detail.title) {
        case 'URL:':
          detail.content = this.operation?.url;
          break;
        case 'Type:':
          detail.content = this.operation?.type;
          break;
        case 'Body:':
          detail.content = this.operation?.body;
          break;
        case 'Expected Type:':
          detail.content = this.operation?.expectedType;
          break;
        case 'Expected Response:':
          detail.content = this.operation?.expectedResponse;
          break;
        default:
          detail.content = '';
      }
    }

  }
}
