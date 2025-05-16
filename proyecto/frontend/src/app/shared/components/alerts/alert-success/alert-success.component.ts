import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-alert-success',
  templateUrl: './alert-success.component.html',
  styleUrls: ['./alert-success.component.css']
})
export class AlertSuccessComponent implements OnInit {
  @Input() mensaje: string = '';
  visible = true;

  ngOnInit(): void {
    setTimeout(() => {
      this.visible = false;
    }, 4000);
  }
}
