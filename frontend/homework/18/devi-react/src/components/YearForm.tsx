import { useState } from 'react';

type YearFormProps = {
onYearSearch: (startYear: number, endYear:number) => void;
}
const YearForm =({ onYearSearch }: YearFormProps) => {
const [startYear, setStartYear] = useState(0);
const [endYear, setEndYear] = useState(0);

const handleSubmit = (e: React.SubmitEvent) => {  
    e.preventDefault();   
    onYearSearch(startYear, endYear);  
}  

return (  
    <form className='year-form' onSubmit={handleSubmit}>  
        <div className='start-year-field'>  
            <input type="number" placeholder="Start Year" value = {startYear} onChange={(e) => setStartYear(Number(e.target.value))}/>  
        </div>  

        <div className='end-year-field'>  
            <input type="number" placeholder="End Year" value = {endYear} onChange={(e) => setEndYear(Number(e.target.value))}/>  
        </div>  

        <button type="submit" className='year-search-submit-button'>Search by Year Range</button>  
    </form>  
)

}

export default YearForm;