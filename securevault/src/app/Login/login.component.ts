import { Component } from '@angular/core';

@Component({
  selector: 'app-logo',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
    onClick(): void {

        if (document.getElementById('login').style.visibility !== 'visible') {
            this.makeVisible();
        } else {
            this.hide();
        }
      }

    makeVisible(): void {
        const login = document.getElementById('login');
        login.style.visibility = 'visible';
    }

    hide(): void {
        const login = document.getElementById('login');
        login.style.visibility = 'hidden';
    }

    /* Is al voor in de toekomst als de back-end kan checken
    showNewUser(): void {
      document.getElementById('newUser').style.visibility = 'visible';
    }
    */
}
