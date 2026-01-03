export const allCustomers: Customer[] = [
  {
    id: 1,
    name: 'Amit Kumar',
    email: 'amit.kumar@gmail.com',
    phone: '9876543210',
    gender: 'Male',
    dob: '15-08-1990'
  },
  {
    id: 2,
    name: 'Anil Sharma',
    email: 'anil.sharma@gmail.com',
    phone: '9123456789',
    gender: 'Male',
    dob: '22-01-1988'
  },
  {
    id: 3,
    name: 'Anjali Singh',
    email: 'anjali.singh@gmail.com',
    phone: '9988776655',
    gender: 'Female',
    dob: '10-11-1992'
  },
  {
    id: 4,
    name: 'Rahul Verma',
    email: 'rahul.verma@gmail.com',
    phone: '9012345678',
    gender: 'Male',
    dob: '05-03-1987'
  },
  {
    id: 5,
    name: 'Ravi Patel',
    email: 'ravi.patel@gmail.com',
    phone: '9090909090',
    gender: 'Male',
    dob: '18-07-1991'
  },
  {
    id: 6,
    name: 'Sunita Rao',
    email: 'sunita.rao@gmail.com',
    phone: '9345678123',
    gender: 'Female',
    dob: '30-09-1989'
  },
  {
    id: 7,
    name: 'Suresh Reddy',
    email: 'suresh.reddy@gmail.com',
    phone: '9871203456',
    gender: 'Male',
    dob: '12-12-1985'
  },
  {
    id: 8,
    name: 'Priya Mehta',
    email: 'priya.mehta@gmail.com',
    phone: '9567891234',
    gender: 'Female',
    dob: '25-04-1993'
  },
  {
    id: 9,
    name: 'Pooja Nair',
    email: 'pooja.nair@gmail.com',
    phone: '9786543211',
    gender: 'Female',
    dob: '08-06-1990'
  },
  {
    id: 10,
    name: 'Alisha Khan',
    email: 'alisha.khan@gmail.com',
    phone: '9998887776',
    gender: 'Female',
    dob: '14-02-1994'
  }
];
export interface Customer {
  id: number;
  name: string;
  email: string;
  phone: string;
  gender: string;
  dob: string; // dd-MM-yyyy
}
