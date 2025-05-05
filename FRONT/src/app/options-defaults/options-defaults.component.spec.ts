import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OptionsDefaultsComponent } from './options-defaults.component';

describe('OptionsDefaultsComponent', () => {
  let component: OptionsDefaultsComponent;
  let fixture: ComponentFixture<OptionsDefaultsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OptionsDefaultsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OptionsDefaultsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
