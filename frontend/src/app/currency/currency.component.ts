import { Component, OnInit } from '@angular/core';


import { CurrencyService } from './currency.service';

@Component({
  selector: 'ebank-currency',
  templateUrl: './currency.component.html',
	styleUrls: ['./currency.component.css'],
  providers: [CurrencyService]
})
export class CurrencyComponent implements OnInit {

  currencies: string[];

  from: string;
  to: string;
  value: number;
  converted: number;

  constructor(private currencyService: CurrencyService) {

  }

  ngOnInit() {
    this.currencyService.getCurrencies().subscribe(res => {
      this.currencies = res.map(r => r.currencyCode);
      this.from = this.currencies[0];
      this.to = this.currencies[1];
    });
  }

  convert() {
    this.currencyService.getValue(this.from, this.to, this.value.toString()).subscribe(res => {
      this.converted = res;
    });
  }

  swapCurrencies() {
   let p = this.from;
   this.from = this.to;
   this.to = p;

   let x = this.value;
   this.value = this.converted;
   this.converted = x;
  }

}
