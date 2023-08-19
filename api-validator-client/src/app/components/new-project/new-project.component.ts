import {Component, OnInit} from '@angular/core';
import Swal from "sweetalert2";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {ProjectService} from "../../services/project.service";
import {Project} from "../../models/project";
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-new-project',
  templateUrl: './new-project.component.html',
  styleUrls: ['./new-project.component.css']
})
export class NewProjectComponent implements OnInit {
  newProjectFormGroup!: FormGroup;

  constructor(private fb: FormBuilder, private projectService: ProjectService, private router: Router,private toastr: ToastrService) {
  }

  ngOnInit(): void {
    this.newProjectFormGroup = this.fb.group({
      name: this.fb.control(null, [Validators.required, Validators.minLength(3)]),
      withAuth: this.fb.control(false)
    });
  }

  handleSaveProject() {
    if (this.newProjectFormGroup.valid) {
      let project: Project = this.newProjectFormGroup.value;
      this.projectService.saveProject(project).subscribe({
        next: data => {
          this.toastr.success('Project added', '', {
            positionClass: 'toast-top-right',
            timeOut: 1000,
            onActivateTick: true
          });
          setTimeout(() => {
            this.newProjectFormGroup.reset();
            this.router.navigate(['/']);
          }, 1100);
        },
        error: err => {
          this.toastr.error('Error: ' + err.error.message, 'Error');
        }
      });

      if (this.newProjectFormGroup.value.withAuth) {
        this.toastr.info('The project requires credentials.', '');
      } else {
        this.toastr.info('The project does not require credentials.', '');
      }
    }
  }
}
