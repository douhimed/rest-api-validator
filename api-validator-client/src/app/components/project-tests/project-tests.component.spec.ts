import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectTestsComponent } from './project-tests.component';

describe('ProjectTestsComponent', () => {
  let component: ProjectTestsComponent;
  let fixture: ComponentFixture<ProjectTestsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProjectTestsComponent]
    });
    fixture = TestBed.createComponent(ProjectTestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
