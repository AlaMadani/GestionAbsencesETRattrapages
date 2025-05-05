import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PendingRattrapagesComponent } from './pending-rattrapages.component';

describe('PendingRattrapagesComponent', () => {
  let component: PendingRattrapagesComponent;
  let fixture: ComponentFixture<PendingRattrapagesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PendingRattrapagesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PendingRattrapagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
