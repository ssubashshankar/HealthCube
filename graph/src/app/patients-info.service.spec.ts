import { TestBed } from '@angular/core/testing';

import { PatientsInfoService } from './patients-info.service';

describe('PatientsInfoService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PatientsInfoService = TestBed.get(PatientsInfoService);
    expect(service).toBeTruthy();
  });
});
