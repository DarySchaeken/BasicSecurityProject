import {BrowserModule} from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {HttpClientModule} from '@angular/common/http';

import { LoginComponent } from './Login/login.component';
import { AppComponent } from './app.component';
import {RouterModule} from '@angular/router';
import { MessageContainerComponent } from './message-container/message-container.component';
import { RightMessagingPaneComponent } from './right-messaging-pane/right-messaging-pane.component';
import { CookieService } from 'ngx-cookie-service';


@NgModule({
    declarations: [
        LoginComponent,
        AppComponent,
        MessageContainerComponent,
        RightMessagingPaneComponent
  ],
    imports: [
        BrowserModule,
        RouterModule.forRoot([
                { path: '', component: LoginComponent, pathMatch: 'full', data: { title: 'Securevault | Login' }},
                { path: 'message', component: MessageContainerComponent , data: { title: 'Securevault | Message'}}
                ]),
        HttpClientModule
    ],
  providers: [CookieService],
  bootstrap: [AppComponent]
})

export class AppModule {}
