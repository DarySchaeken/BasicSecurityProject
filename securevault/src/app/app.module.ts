import {BrowserModule} from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AngularFireModule } from 'angularfire2';
import { AngularFireDatabaseModule } from 'angularfire2/database';
import { AngularFireAuthModule } from 'angularfire2/auth';
import { LoginComponent } from './Login/login.component';
import { AppComponent } from './app.component';
import { LeftMessagingPaneComponent } from './left-messaging-pane/left-messaging-pane.component';
import {RouterModule} from '@angular/router';
import { MessageContainerComponent } from './message-container/message-container.component';
import { RightMessagingPaneComponent } from './right-messaging-pane/right-messaging-pane.component';
import {environment} from '../environments/environment';


@NgModule({
    declarations: [
        LoginComponent,
        AppComponent,
        LeftMessagingPaneComponent,
        MessageContainerComponent,
        RightMessagingPaneComponent,
  ],
    imports: [
        BrowserModule,
        RouterModule.forRoot([
                { path: '', component: LoginComponent, pathMatch: 'full', data: { title: 'Securevault | Login' } },
                { path: 'message', component: MessageContainerComponent , data: { title: 'Securevault | Message'} }
                ]),
        AngularFireModule.initializeApp(environment.firebase),
        AngularFireDatabaseModule,
        AngularFireAuthModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})

export class AppModule {}
