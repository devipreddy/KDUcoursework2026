import type { Genre } from '../types/Book';
import { GENRES } from '../types/Book';

type GenreFilterProps = {
selectedGenre: Genre | 'ALL';
onGenreChange: (genre: Genre | 'ALL') => void;
};

const GenreFilter = ({
selectedGenre,
onGenreChange,
}: GenreFilterProps) => {
return (
<select value={selectedGenre} className="genre-filter" onChange={(e) => onGenreChange(e.target.value as Genre | 'ALL') } >
<option value="ALL">All Genres</option>

{GENRES.map((genre) => (<option key={genre} value={genre}> {genre}  
  </option>))}  
</select>

);
};

export default GenreFilter;