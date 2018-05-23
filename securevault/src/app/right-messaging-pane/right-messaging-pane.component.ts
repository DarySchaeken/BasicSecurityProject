import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs/Observable';
import {Message} from '../../_models/message';
import {CookieService} from 'ngx-cookie-service';
import {Router} from '@angular/router';
import 'rxjs/add/operator/map';

@Component({
  selector: 'app-right-messaging-pane',
  templateUrl: './right-messaging-pane.component.html',
  styleUrls: ['./right-messaging-pane.component.css']
})
export class RightMessagingPaneComponent implements OnInit {
    private selectedFile: File = null;
    private fileUploadUrl = environment.apiUrl + 'message/sendMessage';
    private messagesUrl = environment.apiUrl + 'message';
    public currentUserName;

    constructor(private http: HttpClient, private cookie: CookieService, private router: Router) {}

    ngOnInit() {
        this.currentUserName = this.cookie.get('username');
    }

    selectedFileChanged(event) {
        this.selectedFile = <File>event.target.files[0];
    }

    sendFile() {
      // Voor Formdata
        const user = prompt('Naar wie wilt u dit bericht verzenden?', 'Dary');
        const formData = new FormData();
      formData.append( 'file', this.selectedFile);
      formData.append('sender', this.currentUserName);
      formData.append('receiver', user);
      formData.append('subject', this.selectedFile.name)
      this.http.post(this.fileUploadUrl, formData).subscribe(res => {
        console.log(res);
      });
      this.router.navigateByUrl('/message');
      /* Voor binary input als back-end dat toelaat
        this.http.post(this.fileUploadUrl, this.selectedFile).subscribe(res => {
            console.log(res);
        });
      */
    }

    downloadFile(id) {
        const downloadUrl = this.messagesUrl + '/?id=' + id;
        window.open(downloadUrl);
    }

    getMessages() {
        return this.http.get(this.messagesUrl).subscribe();
    }
}
