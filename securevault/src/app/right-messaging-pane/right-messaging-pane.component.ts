import { Component } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';

@Component({
  selector: 'app-right-messaging-pane',
  templateUrl: './right-messaging-pane.component.html',
  styleUrls: ['./right-messaging-pane.component.css']
})
export class RightMessagingPaneComponent {
    selectedFile: File = null;
    fileUploadUrl = environment.apiUrl;

    constructor(private http: HttpClient) {}

    selectedFileChanged(event) {
        this.selectedFile = <File>event.target.files[0];
    }

    sendFile() {
      // Voor Formdata
      const formData = new FormData();
      formData.append('upload', this.selectedFile, this.selectedFile.name);
      this.http.post(this.fileUploadUrl, formData).subscribe(res => {
        console.log(res);
      });
      /* Voor binary input als back-end dat toelaat
        this.http.post(this.fileUploadUrl, this.selectedFile).subscribe(res => {
            console.log(res);
        });
      */
    }
}
