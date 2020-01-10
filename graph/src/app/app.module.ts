import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ChartComponent } from './chart/chart.component';
import { Router, Routes, RouterModule } from '@angular/router';
import { HeaderComponent } from './header/header.component';
import { ChartsModule } from 'ng2-charts';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatTableModule} from '@angular/material/table';

import { MatToolbarModule, MatButtonModule, MatSidenavModule, MatIconModule, MatListModule, MatFormFieldModule, MatInputModule, MatDatepickerModule, MatNativeDateModule, MatCheckboxModule, MatCardModule, MatTabsModule, MatStepperModule, MatOptionModule, MatSelectModule, MatProgressSpinnerModule, MatDialogModule, MatProgressBarModule } from '@angular/material';
import { ReactiveFormsModule, FormsModule, FormControl } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { CenterNameComponent } from './center-name/center-name.component';
import { WeeklyReportComponent } from './weekly-report/weekly-report.component';



const route : Routes =[
  {
    path :'', redirectTo : 'chart', pathMatch : 'full'
  },
  {
    path : 'chart', component : ChartComponent
  },
  {
    path : 'report' , component : WeeklyReportComponent
  }
]


@NgModule({
  declarations: [
    AppComponent,
    ChartComponent,
    HeaderComponent,
    CenterNameComponent,
    WeeklyReportComponent
  ],
  imports: [
    BrowserModule,
    ChartsModule,
    AppRoutingModule,
    RouterModule.forRoot(route),
    HttpClientModule,
    BrowserAnimationsModule,
    MatTableModule,
    MatDatepickerModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatNativeDateModule,
    MatInputModule,
    MatButtonModule,
    MatOptionModule,
    MatSelectModule,
    MatProgressSpinnerModule,
    MatProgressBarModule
  

    
  ],
  providers: [DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
