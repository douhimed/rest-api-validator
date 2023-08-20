import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import Swal from "sweetalert2";
import {Operation} from "../../models/Operation";
import {OperationService} from "../../services/operation.service";
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-update-operation',
  templateUrl: './update-operation.component.html',
  styleUrls: ['./update-operation.component.css']
})
export class UpdateOperationComponent implements OnInit {
  operationId!: number;
  operation!: Operation;
  updateOperationFormGroup: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private operationService: OperationService,
    private route: ActivatedRoute,
    private router: Router,
    private toastr: ToastrService
  ) {
    this.updateOperationFormGroup = this.formBuilder.group({
      url: ['', Validators.required],
      type: [''],
      body: [''],
      expectedType: [''],
      expectedResponse: ['']
    });
  }

  ngOnInit(): void {
    this.operationId = this.route.snapshot.params['id'];
    this.operationService.getOperationById(this.operationId).subscribe((operation) => {
      this.operation = operation;
      this.updateOperationFormGroup.setValue({
        url: operation.url,
        type: operation.type,
        expectedType: operation.expectedType,
        body: operation.body,
        expectedResponse: operation.expectedResponse
      });
    });
  }

  handleUpdateOperation(): void {
    if (this.updateOperationFormGroup.valid) {
      const updatedOperation: { id: number; url: any; type: any; body: any; expectedType: any; expectedResponse: any } = {
        id: this.operationId,
        url: this.updateOperationFormGroup.value.url,
        type:this.updateOperationFormGroup.value.type,
        body: this.updateOperationFormGroup.value.body,
        expectedType: this.updateOperationFormGroup.value.expectedType,
        expectedResponse: this.updateOperationFormGroup.value.expectedResponse,

      };

      this.operationService.updateOperation(updatedOperation).subscribe({
        next: () => {
          Swal.fire({
            position: 'center',
            icon: 'success',
            title: 'Operation updated',
            showConfirmButton: false,
            timer: 1000
          });
          console.log(updatedOperation)
          setTimeout(() => {
            this.router.navigate(['/']);
          }, 1100);
        },
        error: (err) => {
          this.toastr.error('Error: '+err.error.message, 'Error');
        }
      });
    }
  }
}
