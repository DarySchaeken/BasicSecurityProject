import {BrowserModule} from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { LoginComponent } from './Login/login.component';
import { AppComponent } from './app.component';
import { LeftMessagingPaneComponent } from './left-messaging-pane/left-messaging-pane.component';
import {RouterModule} from '@angular/router';
import { MessageContainerComponent } from './message-container/message-container.component';
import { RightMessagingPaneComponent } from './right-messaging-pane/right-messaging-pane.component';


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
                ])
    ],
  providers: [],
  bootstrap: [AppComponent]
})

export class AppModule {}
