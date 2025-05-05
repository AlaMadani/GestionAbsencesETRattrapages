import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PendingAbsencesComponent } from './pending-absences.component';

describe('PendingAbsencesComponent', () => {
  let component: PendingAbsencesComponent;
  let fixture: ComponentFixture<PendingAbsencesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PendingAbsencesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PendingAbsencesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
