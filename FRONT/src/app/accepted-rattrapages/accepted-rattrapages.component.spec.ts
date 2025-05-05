import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AcceptedRattrapagesComponent } from './accepted-rattrapages.component';

describe('AcceptedRattrapagesComponent', () => {
  let component: AcceptedRattrapagesComponent;
  let fixture: ComponentFixture<AcceptedRattrapagesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AcceptedRattrapagesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AcceptedRattrapagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
