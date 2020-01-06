import { Component, OnInit, OnChanges, ViewChild  } from '@angular/core';
import { PatientsInfoService } from '../patients-info.service';
import { FormGroup, FormBuilder, Validators, FormGroupDirective } from '@angular/forms';
import { DatePipe } from '@angular/common';

export class patient{
  public female_child : String;
  public adult_male : String ;
  public female_65 : String;
  public adult_female : String ;
  public male_child : String;
  public male_65 : String;
}

export class blood_glucose{
  public result_400plus : String;
  public result_50less : String;
}

export class cholesterol{
  public adults_cholesterol: string;
  public child_cholesterol : string;
}

export class uric_acid{
 
  public male_above_8 : string;
  public female_below_2 : string;
  public female_above_7 : String;
  public male_below_4 : String;
}

export class hemoglobin{
  public adult_female: String;
  public adult_male : String;
  public child_below_5 : String;
  public child_6_to_14 : String;
  public childmale_15_to_18 : String;
  public childfemale_15_to_18 : String;
  public above_18 : String;
}

export class testresult{
  public patientId : String;
  public name : String;
  public result : string;
  public startTime : string;
}

export class DateRange{
  public startdate :String;
  public enddate :String;
  public centerName : String;
}

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css']
})
export class ChartComponent implements OnInit,OnChanges {

  datesorting : FormGroup;
  selectCenter : FormGroup;

  loading : boolean = false;

  range : DateRange;
  

  patientInfo : patient;
  bgCount : blood_glucose;
  cholesterolCounts : cholesterol;
  uric_acidCounts : uric_acid;
  hemoglobincount: hemoglobin;
  centernameList ;
  dateResult : testresult;

  progressBar : boolean = false;



  constructor(private patientService : PatientsInfoService,private formbuild:FormBuilder,private datePipe: DatePipe) { 
    this.datesorting  = this.formbuild.group({
      startdate:['',Validators.compose([Validators.required])],
      enddate :['',Validators.compose([Validators.required])],
      centerName :['']
    })
    this.selectCenter = this.formbuild.group({
      centerName :['']
    })
  }

  
  ngOnInit() {
  }
  ngOnChanges()
  {
    if(!this.loading)
    {
      this.progressBar = true;
    }
    this.patientInformation(this.range);
    this.bloodglucoseCount(this.range);
    this.cholesterolCount(this.range);
    this.uricacidCount(this.range);
      this.hemoglobinCount(this.range);
  }


  chartOptions = {
    responsive: true
  };

  chartData = [
    { data: [330, 600, 260, 700], label: 'X' },
    { data: [120, 455, 100, 340], label: 'Y' }
  ];

  chartLabels = ['A', 'B', 'C', 'D'];

  onChartClick(event) {
    console.log(event);
  }

   patientInformation(dates : DateRange)
  {
    this.patientService.getPatientInfo(dates).then(result=>{
      this.patientInfo = result;
    })
  }

  bloodglucoseCount(dates : DateRange)
  {
    this.patientService.getbgCount(dates).then(result=>{
      this.bgCount = result;
    })
  }

  cholesterolCount(dates : DateRange)
  {
    this.patientService.getcholesterolCount(dates).then(result=>{
      this.cholesterolCounts = result;
    })
  }
  uricacidCount(dates : DateRange)
  {
    this.patientService.geturicAcidCount(dates).then(result=>{
      this.uric_acidCounts = result;
    })
    
  }

  hemoglobinCount(dates : DateRange)
  {
    this.patientService.gethemoglobinCount(dates).then(result=>{
      this.hemoglobincount = result;
      this.progressBar = false;
      this.loading = true;

    })
  }

  sort(datesorting:FormGroup)
  {
   datesorting.value.startdate =this.datePipe.transform(datesorting.value.startdate,"yyyy-MM-dd");
   datesorting.value.enddate =this.datePipe.transform(datesorting.value.enddate,"yyyy-MM-dd");
   this.range = datesorting.value;
   this.range.centerName ="";
   this.selectCenter.get('centerName').reset( );
   this.loading = false;
   this.progressBar =true;
   this.patientService.getcenterNameList(this.range).then(result=>{
    this.centernameList = result;
  })
  this.ngOnChanges();
  

  }

  sortByCenter(selectCenter : FormGroup)
  {
    this.range.centerName = selectCenter.value.centerName
    this.loading = false;
    this.progressBar =true;
    this.ngOnChanges();

  }
}
