import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResponsableDashbordComponent } from './responsable-dashbord.component';

describe('ResponsableDashbordComponent', () => {
  let component: ResponsableDashbordComponent;
  let fixture: ComponentFixture<ResponsableDashbordComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ResponsableDashbordComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ResponsableDashbordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
