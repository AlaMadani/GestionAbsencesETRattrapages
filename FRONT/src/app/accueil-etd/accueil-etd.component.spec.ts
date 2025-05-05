import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccueilEtdComponent } from './accueil-etd.component';

describe('AccueilEtdComponent', () => {
  let component: AccueilEtdComponent;
  let fixture: ComponentFixture<AccueilEtdComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AccueilEtdComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AccueilEtdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
