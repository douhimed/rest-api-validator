import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Operation } from "../../models/Operation";
import { OperationService } from "../../services/operation.service";
import Swal from "sweetalert2";
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-add-operation',
  templateUrl: './add-operation.component.html',
  styleUrls: ['./add-operation.component.css']
})
export class AddOperationComponent implements OnInit {
  operation: Operation = {
    id: 0,
    url: '',
    type: '',
    body: '',
    expectedResponse: '',
    actualResponse: '',
    expectedType: ''
  };

  constructor(private operationService: OperationService, private route: ActivatedRoute, private router: Router, private toastr: ToastrService) { }
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  onSubmit() {
    const projectId = this.route.snapshot.paramMap.get('projectId');
    if (projectId) {
      this.operationService.addOperation(projectId, this.operation).subscribe(
        operationId => {
          this.toastr.success('Operation added', '', {
            positionClass: 'toast-top-right',
            timeOut: 1000,
            onActivateTick: true
          });
          this.router.navigate(['projects', projectId, 'operations']);
        },
        error => {
          this.toastr.error('Error: '+error.error.message, 'Error');
        }
      );
    }
  }
}
