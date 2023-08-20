import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ProjectsListComponent} from './components/projects-list/projects-list.component';
import {ProjectTestsComponent} from './components/project-tests/project-tests.component';
import {NewProjectComponent} from './components/new-project/new-project.component';
import {UpdateProjectComponent} from './components/update-project/update-project.component';
import {OperationsComponent} from "./components/operations/operations.component";
import {AddOperationComponent} from "./components/add-operation/add-operation.component";
import {UpdateOperationComponent} from "./components/update-operation/update-operation.component";
import {OperationDetailsComponent} from "./components/operation-details/operation-details.component";
import {CompareReportComponent} from "./components/compare-report-component/compare-report-component.component";


const routes: Routes = [
  {path: "projects", component: ProjectsListComponent},
  {path: "", redirectTo: "projects", pathMatch: 'full'},
  {path: "project/:id/tests", component: ProjectTestsComponent},
  {path: "new-project", component: NewProjectComponent},
  {path: "update-project/:id", component: UpdateProjectComponent},
  {path: 'add-operation/:projectId', component: AddOperationComponent},
  {path: 'projects/:projectId/operations', component: OperationsComponent},
  {path: 'projects/:projectId/add-operation', component: AddOperationComponent},
  {path: "update-operation/:id", component: UpdateOperationComponent},
  {path: 'operations/:id', component: OperationDetailsComponent},
  { path: 'projects/:id/rapport', component: CompareReportComponent },



];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
