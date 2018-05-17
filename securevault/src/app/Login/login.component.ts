import { Component, Renderer2 } from '@angular/core';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import { User } from '../../_models/user';

@Component({
  selector: 'app-logo',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
@Injectable()
export class LoginComponent {

  private apiUrl: string = environment.apiUrl;
  private users: User[];
  private user;

  private checkUserUrl = this.apiUrl + 'account/user';

    constructor(private renderer: Renderer2, private http: HttpClient) {
        this.renderer.setStyle(document.body, 'background-color', '#66ccff');
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
      if (!this.checkIfUserExists) {
        document.getElementById('newUser').style.visibility = 'visible';
      } else {
        document.getElementById('newUser').style.visibility = 'hidden';
      }
    }

    getUsers() {
      return this.http.get<User[]>(this.checkUserUrl);
    }

    checkIfUserExists(): boolean {
      this.getUsers().subscribe(users => this.users = users as User[]);
      this.users.forEach(user => {
        if (user.userName = this.user) {
          return true;
        }
      });
      return false;
    }
}
