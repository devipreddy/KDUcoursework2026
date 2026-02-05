import { Controller, Get,Query } from '@nestjs/common';
import { BooksService } from './books.service';
import { Book } from './entities/book.entity';

@Controller('books')
export class BooksController {
  constructor(private readonly booksService: BooksService) {}


  @Get()
  findAll() {
    return this.booksService.fetchBooksFromData();
  }

  @Get('search')
  searchBooks(
    @Query('title') title?: string,
    @Query('author') author?: string,
  ): Book []{
    return this.booksService.searchBooks(title, author);
  }

  @Get('available') getAvailableBooks(){
    return this.booksService.getAvailableBooks();
  }
  
  @Get('yearrange') getBooksByYearRange(
    @Query('startYear') startYear: number,
    @Query('endYear') endYear: number,
  ): Book[] {
    return this.booksService.getBooksByYearRange(startYear,endYear);
  }

}
