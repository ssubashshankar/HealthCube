import { Component } from '@angular/core';
import html2canvas from 'html2canvas';
import { promise } from 'protractor';
import { PatientsInfoService } from './patients-info.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'graph';

  screen;
  constructor(private patientService: PatientsInfoService){

  }
  screenshot(){
    //console.log("done")
    var temp =html2canvas(document.getElementById("capture")).then(function(canvas) {
      return canvas.toDataURL();
      // var image = new Image();
	    // image.src = canvas.toDataURL("image/png");
	    // return image;
    });
    //console.log (temp);
    temp.then(result=>{
     // document.body.appendChild(result)
      this.patientService.postScreenShot(result);
    })
  }

}
