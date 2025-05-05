import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcessedRattrapagesComponent } from './processed-rattrapages.component';

describe('ProcessedRattrapagesComponent', () => {
  let component: ProcessedRattrapagesComponent;
  let fixture: ComponentFixture<ProcessedRattrapagesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProcessedRattrapagesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProcessedRattrapagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
