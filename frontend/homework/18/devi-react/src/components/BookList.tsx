import type { Book } from '../types/Book'

type Props = {
  books: Book[];
};

const BookList = ({ books }: Props) => {
  if (!books || books.length === 0) {
    return <p>No books to display</p>;
  }

  return (
    <ul className="book-list">
      {books.map((book) => (
        <li
          key={book.id}
          className={`book-element ${
            book.available ? 'available' : 'unavailable'
          }`}
        >
          <strong>{book.title}</strong> <br />
          Author: {book.author} <br />
          Year: {book.yearPublished} <br />
          Available: {book.available ? 'Yes' : 'No'} <br />
          Rating: {book.rating}
        </li>
      ))}
    </ul>
  );
};

export default BookList;