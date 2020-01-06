import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CenterNameComponent } from './center-name.component';

describe('CenterNameComponent', () => {
  let component: CenterNameComponent;
  let fixture: ComponentFixture<CenterNameComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CenterNameComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CenterNameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
