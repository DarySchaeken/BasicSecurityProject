import { Component, OnInit, Renderer2 } from '@angular/core';

@Component({
  selector: 'app-message-container',
  templateUrl: './message-container.component.html'
})
export class MessageContainerComponent implements OnInit {

  constructor(private renderer: Renderer2) {
      this.renderer.setStyle(document.body, 'background-color', '#FFFFFF');
  }

  ngOnInit() {
  }

}
