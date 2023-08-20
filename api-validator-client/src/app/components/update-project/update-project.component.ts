import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import Swal from 'sweetalert2';
import {Project} from '../../models/project';
import {ProjectService} from '../../services/project.service';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-update-project',
  templateUrl: './update-project.component.html',
  styleUrls: ['./update-project.component.css']
})
export class UpdateProjectComponent implements OnInit {
  projectId!: number;
  project!: Project;
  updateProjectFormGroup: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private projectService: ProjectService,
    private route: ActivatedRoute,
    private router: Router,
    private toastr: ToastrService
  ) {
    this.updateProjectFormGroup = this.formBuilder.group({
      name: ['', Validators.required],
      withAuth: [false]
    });
  }

  ngOnInit(): void {
    this.projectId = this.route.snapshot.params['id'];
    this.projectService.getProjectById(this.projectId).subscribe((project) => {
      this.project = project;
      this.updateProjectFormGroup.setValue({
        name: project.name,
        withAuth: project.withAuth || false
      });
    });
  }

  handleUpdateProject(): void {
    if (this.updateProjectFormGroup.valid) {
      const updatedProject: Project = {
        id: this.projectId,
        name: this.updateProjectFormGroup.value.name,
        withAuth: this.updateProjectFormGroup.value.withAuth,
        operationDtos: []
      };
      this.projectService.updateProject(updatedProject).subscribe({
        next: () => {
          Swal.fire({
            position: 'center',
            icon: 'success',
            title: 'Project updated',
            showConfirmButton: false,
            timer: 1000
          }).then(() => {
            if (updatedProject.withAuth) {
              this.toastr.info('The project requires credentials.', 'With Authentication');
            }
            setTimeout(() => {
              this.router.navigate(['/']);
            }, 1100);
          });
        },
        error: (err) => {
          this.toastr.error('Error: ' + err.error.message, 'Error');
        }
      });
    }
    console.log(this.project.withAuth)
  }

  toggleWithAuth(checked: boolean): void {
    this.updateProjectFormGroup.patchValue({withAuth: checked});
  }

}
