export class Customer {

  id: number;
  firstName: string;
  lastName: string;
  embg: string;
  address: string;
  email: string;
  transactionNumber: string;
  balance: number;


  constructor(jsonObject: string) {
    Object.assign(this, JSON.parse(jsonObject));
  }

}
