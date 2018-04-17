import { Component, Renderer2 } from '@angular/core';

@Component({
  selector: 'app-logo',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {

    constructor(private renderer: Renderer2) {
        this.renderer.setStyle(document.body, 'background-color', '#66ccff');
    }

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