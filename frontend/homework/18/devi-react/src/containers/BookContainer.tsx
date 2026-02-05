
import { useState } from 'react';
import type { Book } from '../types/Book';
import type { Genre } from '../types/Book';

import {
fetchAllBooks,
fetchAvailableBooks,
fetchBooksByYearRange,
searchBooksApi,
} from '../service/bookApi';

import SearchForm from '../components/SearchForm';
import YearForm from '../components/YearForm';
import BookList from '../components/BookList';
import GenreFilter from '../components/GenreFilter';
import RatingFilter from '../components/RatingFilter';
import Statistics from '../components/Statistics';
import Header from '../components/Header';

const BookContainer = () => {
const [allBooks, setAllBooks] = useState<Book[]>([]);
const [displayedBooks, setDisplayedBooks] = useState<Book[]>([]);

const [selectedGenre, setSelectedGenre] = useState<Genre | 'ALL'>('ALL');
const [minRating, setMinRating] = useState(0);

const handleGetAllBooks = async () => {
const books = await fetchAllBooks();
setAllBooks(books);
setDisplayedBooks(applyClientSideFilters(books));
};

const handleSearch = async (title: string, author: string) => {
const books = await searchBooksApi(title, author);
setAllBooks(books);
setDisplayedBooks(applyClientSideFilters(books));
};

const handleAvailableBooks = async () => {

if (allBooks.length === 0) {  
  const books = await fetchAvailableBooks();  
  setAllBooks(books);  
  setDisplayedBooks(applyClientSideFilters(books));  
  return;  
}  


setDisplayedBooks(applyClientSideFilters(allBooks.filter(book => book.available)));

};

const handleYearRange = async (
startYear: number,
endYear: number
) => {

if (allBooks.length === 0) {  
  const books = await fetchBooksByYearRange(startYear, endYear);  
  setAllBooks(books);  
  setDisplayedBooks(applyClientSideFilters(books));  
  return;  
}  

setDisplayedBooks(applyClientSideFilters(allBooks).filter(  
  book =>  
    book.yearPublished >= startYear &&  
    book.yearPublished <= endYear  
));

};

const applyClientSideFilters = (books: Book[]) => {
let result = books;

if (selectedGenre !== 'ALL') {  
    result = result.filter(  
    (book) => book.genre === selectedGenre  
    );  
}  

if (minRating > 0) {  
    result = result.filter(  
    (book) => book.rating >= minRating  
    );  
}  

return result;

};

const handleReset = () => {
setDisplayedBooks(applyClientSideFilters(allBooks));
};

const calculateBookStatistics = (books: Book[]) => {
  const totalBooks = books.length;

  const availableBooks = books.filter(
    (book) => book.available
  ).length;

  const unavailableBooks = totalBooks - availableBooks;

  const averageRating =
    totalBooks === 0
      ? 0
      : Number(
          (
            books.reduce((sum, book) => sum + book.rating, 0) /
            totalBooks
          ).toFixed(2)
        );

  return {
    totalBooks,
    availableBooks,
    unavailableBooks,
    averageRating,
  };
};

const stats = calculateBookStatistics(displayedBooks);


return (
<div className='main-container'>
<Header />   
<button className="get-all-books-button" onClick={handleGetAllBooks}> Get All Books </button>

<SearchForm onSearch={handleSearch} />  

  <button className="get-available-books-button" onClick={handleAvailableBooks}>Get Available Books</button>  

  <YearForm onYearSearch={handleYearRange} />  

  <GenreFilter selectedGenre={selectedGenre} onGenreChange={setSelectedGenre}/>  

  <RatingFilter minRating={minRating} onRatingChange={setMinRating}/>  

  <button className="reset-filters-button" onClick={handleReset}> Reset Filters</button>  

    <Statistics
    totalBooks={stats.totalBooks}
    availableBooks={stats.availableBooks}
    unavailableBooks={stats.unavailableBooks}
    averageRating={stats.averageRating}
    />
  <BookList books={displayedBooks} />  
</div>

);
};

export default BookContainer;