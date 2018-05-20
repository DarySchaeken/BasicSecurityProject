import {Component, Renderer2, OnInit} from '@angular/core';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {User} from '../../_models/user';
import {Observable} from 'rxjs/Observable';
import {timestamp} from "rxjs/operators/timestamp";

@Component({
    selector: 'app-logo',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css'],
})
@Injectable()
export class LoginComponent implements OnInit {

    private apiUrl: string = environment.apiUrl;
    private users: User[];
    private user: string;

    private checkUserUrl = this.apiUrl + 'account';

    constructor(private renderer: Renderer2, private http: HttpClient) {
        this.renderer.setStyle(document.body, 'background-color', '#66ccff');
    }

    ngOnInit() {
        this.getUsers().subscribe(users => {
            this.users = users;
        },error => console.log(error));
    }

    onClick(): void {

        if (document.getElementById('login').style.visibility !== 'visible') {
            this.makeLoginVisible();
        } else {
            this.hideLogin();
        }
    }

    makeLoginVisible(): void {
        const login = document.getElementById('login');
        login.style.visibility = 'visible';
    }

    hideLogin(): void {
        const login = document.getElementById('login');
        login.style.visibility = 'hidden';
    }

    showNewUser(): void {
        if (!this.checkIfUserExists()) {
            document.getElementById('newUser').style.visibility = 'visible';
        } else {
            document.getElementById('newUser').style.visibility = 'hidden';
        }
    }

    getUsers(): Observable<User[]> {
        return this.http.get<User[]>(this.checkUserUrl);
    }

    checkIfUserExists(): boolean {
        console.log(this.users);
        console.log(this.user);
        this.users.forEach(user => {
            console.log(user.userName);
            console.log(this.user == user.userName);
            if (user.userName == this.user) {
                return true;
            }
        });
        return false;
    }
}
