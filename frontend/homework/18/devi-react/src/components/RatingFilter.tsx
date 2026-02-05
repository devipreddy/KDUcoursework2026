type RatingFilterProps = {
minRating: number;
onRatingChange: (rating: number) => void;
};

const RatingFilter = ({
minRating,
onRatingChange,
}: RatingFilterProps) => {
return (
<select value={minRating} className="rating-filter" onChange={(e) => onRatingChange(Number(e.target.value))}>
<option value={0} className="rating-option">All Ratings</option>
<option value={3} className="rating-option">3 & above</option>
<option value={4} className="rating-option">4 & above</option>
<option value={4.5} className="rating-option">4.5 & above</option>
</select>
);
};

export default RatingFilter;