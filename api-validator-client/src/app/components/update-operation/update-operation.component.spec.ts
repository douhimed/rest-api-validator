import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateOperationComponent } from './update-operation.component';

describe('UpdateOperationComponent', () => {
  let component: UpdateOperationComponent;
  let fixture: ComponentFixture<UpdateOperationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateOperationComponent]
    });
    fixture = TestBed.createComponent(UpdateOperationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
