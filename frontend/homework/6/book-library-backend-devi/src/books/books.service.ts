import { Injectable, Logger } from '@nestjs/common';
import { BooksData } from './books.data';
import { Book } from './entities/book.entity';

@Injectable()
export class BooksService {

  private books: Book[] = BooksData;
  private readonly logger = new Logger(BooksService.name);


  async fetchBooksFromData():Promise<Book[]>{
    await new Promise(resolve => setTimeout(resolve, 1000));
    this.logger.log(`Fetched ${this.books.length} books from data source.`);
    return this.books;
  }

  searchBooks(title?: string, author?: string): Book[] {
    let result = this.books;

    switch(true){
      case !!title && !!author:
        const lowerCaseTitle = title.toLowerCase();
        const lowerCaseAuthor = author.toLowerCase();
        
        result = result.filter((book) => {
          return book.title.toLowerCase().includes(lowerCaseTitle) && book.author.toLowerCase().includes(lowerCaseAuthor)
        },);

        this.logger.log(`Search for title: "${title}" and author: "${author}" returned ${result.length} results.`);
        break;

      
      case !!title:
        const lowerCaseTitleOnly = title.toLowerCase();

        result = result.filter((book) => {
          return book.title.toLowerCase().includes(lowerCaseTitleOnly)
        },);
        this.logger.log(`Search for title: "${title}" returned ${result.length} results.`);
        break;

      case !!author:
        const lowerCaseAuthorOnly = author.toLowerCase();

        result = result.filter((book) => {
          return book.author.toLowerCase().includes(lowerCaseAuthorOnly)
        },);
        this.logger.log(`Search for author: "${author}" returned ${result.length} results.`);
        break;
      default:
        this.logger.log(`No search parameters provided.`);
        return [];
    }

    return result;
  }

  getAvailableBooks(): Book[] {

    return this.books.filter((book) => book.available);

  }

  getBooksByYearRange(startYear: number, endYear: number): Book[] {

    if (isNaN(startYear) || isNaN(endYear)) {
      this.logger.warn(`Invalid year range: startYear (${startYear}) or endYear (${endYear}) is not a number.`);
      throw new Error('Invalid year range');
    }

    if (startYear > endYear) {
      this.logger.warn(`Invalid year range: startYear (${startYear}) is greater than endYear (${endYear}).`);
      throw new Error('Start year must be less than or equal to end year');
    }

    return this.books.filter((book) => {
      this.logger.log(`Fetched books published between ${startYear} and ${endYear}.`);
      return book.yearPublished >= startYear && book.yearPublished<= endYear;
      
    },);



  }

}
