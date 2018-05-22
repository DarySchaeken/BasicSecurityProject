import {Component, Renderer2, OnInit, ElementRef, ViewChild, AfterViewInit, Input} from '@angular/core';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {User} from '../../_models/user';
import {Observable} from 'rxjs/Observable';

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
        },error => console.log(error));
    }


    onClick(): void {
        if (this.login.nativeElement.style.visibility !== 'visible') {
            this.makeLoginVisible();
        } else {
            this.hideLogin();
        }
    }

    makeLoginVisible(): void {
        this.login.nativeElement.style.visibility = 'visible';
    }

    hideLogin(): void {
        this.login.nativeElement.style.visibility = 'hidden';
    }

    showNewUser(): void {
        if (!this.checkIfUserExists()) {
            this.newUser.nativeElement.style.visibility = 'visible';
        } else {
            this.newUser.nativeElement.style.visibility = 'hidden';
        }
    }

    getUsers(): Observable<User[]> {
        return this.http.get<User[]>(this.checkUserUrl);
    }

    checkIfUserExists(): boolean {
        this.users.forEach(user => {
            if (this.userName.nativeElement.textContent === user.userName) {
                return true;
            }
        });
        return false;
    }
}
