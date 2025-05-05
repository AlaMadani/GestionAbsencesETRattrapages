import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccueilTeacherComponent } from './accueil-teacher.component';

describe('AccueilTeacherComponent', () => {
  let component: AccueilTeacherComponent;
  let fixture: ComponentFixture<AccueilTeacherComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AccueilTeacherComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AccueilTeacherComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
