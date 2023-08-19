import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ProjectService} from '../../services/project.service';
import {TestResult} from "../../models/testResult";

@Component({
  selector: 'app-compare-report-component',
  templateUrl: './compare-report-component.component.html',
  styleUrls: ['./compare-report-component.component.css']
})
export class CompareReportComponent implements OnInit {
  testResult!: TestResult;
  loading: boolean = true;
  error!: string;

  constructor(private route: ActivatedRoute, private compareService: ProjectService) {
  }

  ngOnInit(): void {
    const projectIdParam = this.route.snapshot.paramMap.get('id');
    if (!projectIdParam) {
      this.error = 'Project ID is missing or invalid.';
      this.loading = false;
      return;
    }

    const projectId = +projectIdParam;
    this.compareService.compareJson(projectId).subscribe(
      (testResult: TestResult) => {

        for(let i=0;i<testResult.responseDto.length;i++){
          for(let j=0;j<testResult.responseDto[i].messages.length;j++){
            testResult.responseDto[i].messages[j].value = JSON.stringify(testResult.responseDto[i].messages[j].value)
          }
        }

        this.testResult = testResult;
        this.loading = false;
      },
      (error: any) => {
        this.error = 'Error fetching comparison report.';
        this.loading = false;
      }
    );
  }

  toggleCollapse(responseDto: any) {
    responseDto.isCollapsed = !responseDto.isCollapsed;
  }
}
