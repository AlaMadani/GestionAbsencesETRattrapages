import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcessedAbsencesComponent } from './processed-absences.component';

describe('ProcessedAbsencesComponent', () => {
  let component: ProcessedAbsencesComponent;
  let fixture: ComponentFixture<ProcessedAbsencesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProcessedAbsencesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProcessedAbsencesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
