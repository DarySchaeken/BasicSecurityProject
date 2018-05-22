import {Component, Renderer2, OnInit, ElementRef, ViewChild, AfterViewInit, Input} from '@angular/core';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {User} from '../../_models/user';
import {Observable} from 'rxjs/Observable';
import {forEach} from "@angular/router/src/utils/collection";

@Component({
    selector: 'app-logo',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css'],
})
@Injectable()
export class LoginComponent implements OnInit, AfterViewInit {

  private apiUrl: string = environment.apiUrl;
  private users: User[];
  @ViewChild('login') login: ElementRef;
  @ViewChild('newUser') newUser: ElementRef;
  @ViewChild('userName') userName: ElementRef;

  private checkUserUrl = this.apiUrl + 'account';

    constructor(private renderer: Renderer2, private http: HttpClient) {
        this.renderer.setStyle(document.body, 'background-color', '#66ccff');
    }

    ngAfterViewInit() {
    }

    ngOnInit() {
        this.getUsers().subscribe(users => {
            this.users = users;
        }, error => console.log(error));
    }


    onClick(): void {
        if (this.login.nativeElement.style.visibility !== 'visible') {
            this.makeLoginVisible();
        } else if (this.checkIfUserExists() && this.checkPassword()) {
            alert('ok');
        } else {
            this.hideLogin();
        }
    }

    makeLoginVisible(): void {
        this.login.nativeElement.style.visibility = 'visible';
    }

    hideLogin(): void {
        this.login.nativeElement.style.visibility = 'hidden';
        this.newUser.nativeElement.style.visibility = 'hidden';
    }

    showNewUser(): void {
        if (this.checkIfUserExists()) {
            console.log(true);
            this.newUser.nativeElement.style.visibility = 'hidden';
        } else {
            console.log(false);
            this.newUser.nativeElement.style.visibility = 'visible';
        }
    }

    getUsers(): Observable<User[]> {
        return this.http.get<User[]>(this.checkUserUrl);
    }

    checkIfUserExists(): boolean {
        console.log('1');
        for (let i = 0; i <= this.users.length; i++) {
            if (this.userName.nativeElement.value.trim() === this.users[i].userName.trim()) {
                return true;
            }
        }
        return false;
    }

    checkPassword(): boolean {
        return true;
    }
}
