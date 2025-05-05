import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HistoriqueTeacherComponent } from './historique-teacher.component';

describe('HistoriqueTeacherComponent', () => {
  let component: HistoriqueTeacherComponent;
  let fixture: ComponentFixture<HistoriqueTeacherComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HistoriqueTeacherComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HistoriqueTeacherComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
