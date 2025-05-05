import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AbsRattEtdComponent } from './abs-ratt-etd.component';

describe('AbsRattEtdComponent', () => {
  let component: AbsRattEtdComponent;
  let fixture: ComponentFixture<AbsRattEtdComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AbsRattEtdComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AbsRattEtdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
