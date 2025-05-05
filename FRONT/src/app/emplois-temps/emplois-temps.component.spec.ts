import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmploisTempsComponent } from './emplois-temps.component';

describe('EmploisTempsComponent', () => {
  let component: EmploisTempsComponent;
  let fixture: ComponentFixture<EmploisTempsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmploisTempsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EmploisTempsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
