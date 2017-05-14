import { Component, OnInit, Input } from '@angular/core';

import { Creditor } from './creditor';
import { CreditorService } from './creditor.service';

@Component({
  selector: 'creditor-detail',
  templateUrl: './creditor-detail.component.html',
  styleUrls: ['./creditor-detail.component.css'],
  providers: [CreditorService]
})
export class CreditorDetailComponent implements OnInit {

  @Input() creditor: Creditor;

  constructor(private creditorService: CreditorService) {

  }

  // getCreditor() {
  //   this.creditorService.getCreditor().then((resp) => this.creditor = resp);
  // }

  ngOnInit(): void {
    //this.getCreditor();
  }

}
