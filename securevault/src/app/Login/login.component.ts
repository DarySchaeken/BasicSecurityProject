import {Component, Renderer2, OnInit, ElementRef, ViewChild} from '@angular/core';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {User} from '../../_models/user';
import {Observable} from 'rxjs/Observable';
import {Router} from '@angular/router';
import {CookieService} from 'ngx-cookie-service';

@Component({
    selector: 'app-logo',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css'],
})
@Injectable()
export class LoginComponent implements OnInit {

  private apiUrl: string = environment.apiUrl;
  private users: User[];
  @ViewChild('login') login: ElementRef;
  @ViewChild('newUser') newUser: ElementRef;
  @ViewChild('userName') userName: ElementRef;
  @ViewChild('password') password: ElementRef;
  @ViewChild('confirmPassword') confirmPassword: ElementRef;

  private getUserUrl = this.apiUrl + 'account';
  private checkUserUrl = this.apiUrl + 'account/';
  private checkPasswordUrl = this.apiUrl + 'account/login';
  private creatUserUrl = this.apiUrl + 'account/register';
  private userNameCheck = false;
  private passwordCheck = false;
  private now = Date.now();

    constructor(private renderer: Renderer2, private http: HttpClient, private router: Router, private cookie: CookieService) {
        this.renderer.setStyle(document.body, 'background-color', '#66ccff');
    }

    ngOnInit() {
        this.getUsers().subscribe(users => {
            this.users = users;
        }, error => console.log(error));
    }


    onClick(): void {
        if (this.login.nativeElement.style.visibility !== 'visible') {
            this.makeLoginVisible();
        } else if (this.userNameCheck && this.passwordCheck) {
            this.cookie.set('username', this.userName.nativeElement.value.trim(), this.now + 1);
            this.router.navigateByUrl('/message');
        } else if (this.confirmPassword.nativeElement.value !== '') {
            this.makeNewUser();
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
        this.checkIfUserExists().subscribe(result => {
            if (result) {
                console.log(true);
                this.newUser.nativeElement.style.visibility = 'hidden';
            } else {
                console.log(false);
                this.newUser.nativeElement.style.visibility = 'visible';
            }
        });
        this.checkIfUsernameIsValid();
    }

    checkIfUsernameIsValid(): boolean {
        this.checkIfUserExists().subscribe(result => {
            if (result) {
                return this.userNameCheck = true;
            }
        });
        return false;
    }

    getUsers(): Observable<User[]> {
        return this.http.get<User[]>(this.getUserUrl);
    }

    checkIfUserExists(): Observable<boolean> {
       return this.http.get<boolean>(this.checkUserUrl + this.userName.nativeElement.value.trim());
    }

    checkPassword(): boolean {
        this.http.post<User>(this.checkPasswordUrl, new User(this.userName.nativeElement.value.trim(),
            this.password.nativeElement.value.trim())).subscribe(result => {
            if (result.userName === this.userName.nativeElement.value.trim()) {
                return this.passwordCheck =  true;
            }
        });
        return false;
    }

    makeNewUser() {
        if (this.password.nativeElement.value !== '' && this.confirmPassword.nativeElement.value !== ''
            && this.userName.nativeElement.value !== '') {
            if (this.confirmPassword.nativeElement.value.trim() === this.password.nativeElement.value.trim()) {
                this.cookie.set('username', this.userName.nativeElement.value.trim());
                this.router.navigateByUrl('/message');
                return this.http.post<User>(this.creatUserUrl, new User(this.userName.nativeElement.value.trim(),
                    this.password.nativeElement.value.trim())).subscribe(result => {
                        console.log(result);
                    });
            } else {
                alert('De opgegeven passwoorden komen zijn niet gelijk');
            }
        }
    }
}
