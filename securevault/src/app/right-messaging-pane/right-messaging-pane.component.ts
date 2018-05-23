import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs/Observable';
import {Message} from '../../_models/message';
import {CookieService} from 'ngx-cookie-service';

@Component({
  selector: 'app-right-messaging-pane',
  templateUrl: './right-messaging-pane.component.html',
  styleUrls: ['./right-messaging-pane.component.css']
})
export class RightMessagingPaneComponent implements OnInit {
    private selectedFile: File = null;
    private fileUploadUrl = environment.apiUrl + 'sendMessage';
    private messagesUrl = environment.apiUrl;
    private messages: Message[];
    private currentUserName;
    private myMessages: Message[];

    constructor(private http: HttpClient, private cookie: CookieService) {}

    ngOnInit() {
        this.getMessages().subscribe(message => {
            this.messages = message;
        });
        this.currentUserName = this.cookie.get('username');
        this.cookie.delete('username');
        this.myMessages = this.getSendMessages();
    }

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

    downloadFile(id) {
        const downloadUrl = this.messagesUrl + '/?id=' + id;
        window.open(downloadUrl);
    }

    getMessages(): Observable<Message[]> {
        return this.http.get<Message[]>(this.messagesUrl);
    }

    getSendMessages(): Message[] {
        return this.messages.filter(message => message.sender === this.currentUserName);
    }
}
