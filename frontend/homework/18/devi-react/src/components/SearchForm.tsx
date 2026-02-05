
import { useState } from 'react';

type SearchFormProps = {
onSearch: (title: string, author: string) => void;
}
const SearchForm = ({ onSearch }: SearchFormProps) => {
const [title, setTitle] = useState('');
const [author, setAuthor] = useState('');

const handleSubmit = (e: React.SubmitEvent) => {
e.preventDefault();
onSearch(title, author);
};

return(  
    <form className='input-form' onSubmit={handleSubmit}>  
        <div className='title-field'>  
            <input type="text" placeholder="Search by title" value={title} onChange={(e) => setTitle(e.target.value)} />  
        </div>  

        <div className='author-field'>  
            <input type="text" placeholder="Search by author" value={author} onChange={(e) => setAuthor(e.target.value)}/>  
        </div>  

        <button type="submit" className='search-submit-button' >Search</button>  
    </form>  
);

};

export default SearchForm;