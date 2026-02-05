type StatisticsProps = {
  totalBooks: number;
  availableBooks: number;
  unavailableBooks: number;
  averageRating: number;
};

const Statistics = ({
  totalBooks,
  availableBooks,
  unavailableBooks,
  averageRating,
}: StatisticsProps) => {
  return (
    <div className="statistics">
      <p><strong>Total Books:</strong> {totalBooks}</p>
      <p><strong>Available:</strong> {availableBooks}</p>
      <p><strong>Unavailable:</strong> {unavailableBooks}</p>
      <p><strong>Average Rating:</strong> {averageRating}</p>
    </div>
  );
};

export default Statistics;