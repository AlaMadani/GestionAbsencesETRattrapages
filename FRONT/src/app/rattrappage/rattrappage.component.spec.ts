import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RattrappageComponent } from './rattrappage.component';

describe('RattrappageComponent', () => {
  let component: RattrappageComponent;
  let fixture: ComponentFixture<RattrappageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RattrappageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RattrappageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
