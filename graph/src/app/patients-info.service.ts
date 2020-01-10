import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
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

  postScreenShot(screen :any)
  {
   console.log(screen)
    // return this.http.post("http://localhost:8102/getscreen",{screen},{headers : new HttpHeaders({
    //   'Content-Type': 'text/html'
    // })})
    var body =screen;
    this.http.post<any>("http://localhost:8102/sendmail", body).subscribe(
      (res) => console.log(res),
      (err) => console.log(err)
    );
  }

  async getOutlierCenter(dates : DateRange)
  {
    return this.http.post<any>("http://localhost:8102/send_report",dates,{
      responseType :'text' as 'json'
    }).toPromise();
  }

   getWeeklydate()
  {
    return this.http.get("http://localhost:8102/last_week");
  }
}
 