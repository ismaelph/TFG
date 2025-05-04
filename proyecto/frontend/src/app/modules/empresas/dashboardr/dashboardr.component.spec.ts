import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardrComponent } from './dashboardr.component';

describe('DashboardrComponent', () => {
  let component: DashboardrComponent;
  let fixture: ComponentFixture<DashboardrComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashboardrComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardrComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
