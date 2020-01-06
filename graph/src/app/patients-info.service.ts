import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { patient, blood_glucose, cholesterol, uric_acid, hemoglobin, testresult, DateRange } from './chart/chart.component';

@Injectable({
  providedIn: 'root'
})
export class PatientsInfoService {

  constructor(private http : HttpClient) { }

  async getPatientInfo(dates : DateRange)
  {
    return this.http.post<patient>("http://localhost:8102/patients_Count",dates).toPromise();
  }

  async getbgCount(dates : DateRange)
  {
    return this.http.post<blood_glucose>("http://localhost:8102/blood_glucose_Count",dates).toPromise();
  }

  async getcholesterolCount(dates : DateRange)
  {
    return this.http.post<cholesterol>("http://localhost:8102/cholesterol_Count",dates).toPromise();
  }

  async geturicAcidCount(dates : DateRange)
  {
    return this.http.post<uric_acid>("http://localhost:8102/uric_acid_Count",dates).toPromise();
  }

  async gethemoglobinCount(dates : DateRange)
  {
    return this.http.post<hemoglobin>("http://localhost:8102/hemoglobin_Count",dates).toPromise();
  }

  async getcenterNameList(dates : DateRange)
  {
    return this.http.post("http://localhost:8102/center_name",dates).toPromise();
  }
}
