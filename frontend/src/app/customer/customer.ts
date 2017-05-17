export class Customer {

  id: number;
  firstName: string;
  lastName: string;
  embg: string;
  address: string;
  email: string;
  transactionNumber: string;
  balance: number;
  password: string;
  active: boolean;
  role: Role;

  constructor() {

  }

}

class Role {
  id: number;
  name: string;
}
