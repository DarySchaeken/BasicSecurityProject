import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { LoginComponent } from './Login/login.component';
import { AppComponent } from './app.component';
import { LeftMessagingPaneComponent } from './left-messaging-pane/left-messaging-pane.component';
import {appRoutes} from './app.routes';
import {RouterModule} from '@angular/router';


@NgModule({
    declarations: [
        LoginComponent,
        AppComponent,
        LeftMessagingPaneComponent,
  ],
    imports: [
        BrowserModule,
        RouterModule.forRoot(appRoutes)
    ],
  providers: [],
  bootstrap: [AppComponent]
})

export class AppModule {}
