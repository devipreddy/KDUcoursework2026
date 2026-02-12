
export type User = {
    id: number;
    firstName: string;
    lastName: string;
    age: number;
    email: string;
    phone: string;
    image: string;
}

export type UsersResponse = {
  users: User[];
  total: number;
  skip: number;
  limit: number;
};

export type CreateUserPayload = {
  firstName: string;
  lastName: string;
  age: number;
  email: string;
};

