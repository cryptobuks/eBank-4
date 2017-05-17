import { Creditor } from '../creditor/creditor';
import { Customer } from '../customer/customer';

export class Order {

  id: number;
  date: Date;
  amount: number;
  customer: Customer;
  creditor: Creditor;
  description: string;
  type: string;

  constructor(){
    this.creditor = new Creditor();
  }

  //
  // constructor(jsonObject: string) {
  //   Object.assign(this, JSON.parse(jsonObject));
  // }

}
