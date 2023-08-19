import {ComponentFixture, TestBed} from '@angular/core/testing';

import {CompareReportComponent} from './compare-report-component.component';

describe('CompareReportComponentComponent', () => {
  let component: CompareReportComponent;
  let fixture: ComponentFixture<CompareReportComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CompareReportComponent]
    });
    fixture = TestBed.createComponent(CompareReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
