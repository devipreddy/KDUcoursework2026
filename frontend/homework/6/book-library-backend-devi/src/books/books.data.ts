import { Book } from './entities/book.entity';
import { Genre } from './entities/genre.enum';

export const BooksData: Book[] = [
  new Book('1', 'Clean Code', 'Martin', Genre.PROGRAMMING, 2008, 464, true, 'A handbook of agile software craftsmanship', 4.5),
  new Book('2', 'The Pragmatic Programmer', 'Andy Hunt', Genre.PROGRAMMING, 1999, 352, true, 'Journey to mastery in software development', 4.7),
  new Book('3', 'Design Patterns', 'Gang of Four', Genre.PROGRAMMING, 1994, 395, false, 'Elements of reusable object-oriented software', 4.2),
  new Book('4', 'You Dont Know JS', 'Kyle Simpson', Genre.PROGRAMMING, 2015, 278, true, undefined, 4.0),
  new Book('5', 'Introduction to Algorithms', 'Thomas H. Cormen', Genre.PROGRAMMING, 2009, 1312, false, undefined, 4.8),
  new Book('6', 'Dune', 'Frank Herbert', Genre.SCIENCE_FICTION, 1965, 412, true, 'Epic science fiction novel',3.9),
  new Book('7', 'Foundation', 'Isaac Asimov', Genre.SCIENCE_FICTION, 1951, 255, true, undefined, 3.9),
  new Book('8', '1984', 'George Orwell', Genre.SCIENCE_FICTION, 1949, 328, false, undefined, 4.1),
  new Book('9', 'Brave New World', 'Aldous Huxley', Genre.SCIENCE_FICTION, 1932, 311, true, undefined, 3.8),

  new Book('10', 'The Hobbit', 'J.R.R. Tolkien', Genre.FANTASY, 1937, 310, true, undefined, 4.3),
  new Book('11', 'The Lord of the Rings', 'J.R.R. Tolkien', Genre.FANTASY, 1954, 1178, false, undefined, 4.9),
  new Book('12', 'Harry Potter and the Sorcerer’s Stone', 'J.K. Rowling', Genre.FANTASY, 1997, 309, true, undefined, 4.6),
  new Book('13', 'The Name of the Wind', 'Patrick Rothfuss', Genre.FANTASY, 2007, 662, true, undefined, 4.5),

  new Book('14', 'Sapiens', 'Yuval Noah Harari', Genre.HISTORY, 2011, 443, false, 'A brief history of humankind', 4.2),
  new Book('15', 'Homo Deus', 'Yuval Noah Harari', Genre.HISTORY, 2015, 450, true, undefined, 4.1),

  new Book('16', 'Atomic Habits', 'James Clear', Genre.SELF_HELP, 2018, 320, true, undefined, 4.5),
  new Book('17', 'Deep Work', 'Cal Newport', Genre.SELF_HELP, 2016, 304, false, undefined, 4.2),

  new Book('18', 'Thinking, Fast and Slow', 'Daniel Kahneman', Genre.PSYCHOLOGY, 2011, 499, true, undefined, 4.5),

  new Book('19', 'The Psychology of Money', 'Morgan Housel', Genre.FINANCE, 2020, 256, true, undefined, 4.0),
  new Book('20', 'Rich Dad Poor Dad', 'Robert Kiyosaki', Genre.FINANCE, 1997, 336, false, undefined, 4.1),

  new Book('21', 'The Alchemist', 'Paulo Coelho', Genre.FICTION, 1988, 208, true, undefined, 4.2),
  new Book('22', 'To Kill a Mockingbird', 'Harper Lee', Genre.FICTION, 1960, 281, true, undefined, 4.3),
  new Book('23', 'The Great Gatsby', 'F. Scott Fitzgerald', Genre.FICTION, 1925, 180, false, undefined, 4.0),
  new Book('24', 'Crime and Punishment', 'Fyodor Dostoevsky', Genre.CLASSIC, 1866, 671, false, undefined, 4.1),
  new Book('25', 'War and Peace', 'Leo Tolstoy', Genre.CLASSIC, 1869, 1225, true, undefined, 4.2),
];
