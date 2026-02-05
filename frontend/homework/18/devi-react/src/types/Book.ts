export type Genre =
  | 'Programming'
  | 'Fantasy'
  | 'Science Fiction'
  | 'Self Help'
  | 'Finance'
  | 'History'
  | 'Classic'
  | 'Fiction'
  | 'Psychology';

export const GENRES: Genre[] = [
  'Programming',
  'Fantasy',
  'Science Fiction',
  'Self Help',
  'Finance',
  'History',
  'Classic',
  'Fiction',
  'Psychology',
];

export interface Book {
  id: string;
  title: string;
  author: string;
  genre: Genre;
  yearPublished: number;
  pages: number;
  rating: number;
  available: boolean;
  description?: string;
}
