import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CatchUpRequestFormComponent } from './catch-up-request-form.component';

describe('CatchUpRequestFormComponent', () => {
  let component: CatchUpRequestFormComponent;
  let fixture: ComponentFixture<CatchUpRequestFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CatchUpRequestFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CatchUpRequestFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
