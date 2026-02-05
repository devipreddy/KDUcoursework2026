import { Genre } from './genre.enum';

export class Book {
  id: string;
  title: string;
  author: string;
  genre: Genre;
  yearPublished: number;
  pages: number;
  available: boolean = false;
  description?: string;
  rating?: number;

  constructor(
    id: string,
    title: string,
    author: string,
    genre: Genre,
    yearPublished: number,
    pages: number,
    available: boolean,
    description?: string,
    rating?: number,
  ) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.genre = genre;
    this.yearPublished = yearPublished;
    this.pages = pages;
    this.available = available;
    this.description = description;
    this.rating = rating;
  }
}
