import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmploisTeacherComponent } from './emplois-teacher.component';

describe('EmploisTeacherComponent', () => {
  let component: EmploisTeacherComponent;
  let fixture: ComponentFixture<EmploisTeacherComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmploisTeacherComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EmploisTeacherComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
