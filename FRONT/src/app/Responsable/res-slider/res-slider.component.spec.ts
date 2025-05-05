import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResSliderComponent } from './res-slider.component';

describe('ResSliderComponent', () => {
  let component: ResSliderComponent;
  let fixture: ComponentFixture<ResSliderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ResSliderComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ResSliderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
