import {Routes} from '@angular/router';
import {LoginComponent} from './Login/login.component';

export const appRoutes: Routes = [
    {path: '/', component: LoginComponent, data: {backgroundColor: '#66CCFF', Title: 'Secure vault | Login'}}
];
