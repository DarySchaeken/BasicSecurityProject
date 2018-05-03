import { Component, Renderer2 } from '@angular/core';

@Component({
  selector: 'app-left-messaging-pane',
  templateUrl: './left-messaging-pane.component.html',
  styleUrls: ['./left-messaging-pane.component.css']
})
export class LeftMessagingPaneComponent {

    constructor(private renderer: Renderer2) {
        this.renderer.setStyle(document.body, 'background-color', '#ffffff');
    }

}
