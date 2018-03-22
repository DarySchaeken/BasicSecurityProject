import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { LogoComponent } from './logo.component';


@NgModule({
  declarations: [
    LogoComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [LogoComponent]
})
export class AppModule { }
