import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AbsenceRequestFormComponent } from './absence-request-form.component';

describe('AbsenceRequestFormComponent', () => {
  let component: AbsenceRequestFormComponent;
  let fixture: ComponentFixture<AbsenceRequestFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AbsenceRequestFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AbsenceRequestFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
