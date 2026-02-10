import { NavLink } from "react-router-dom";
import { useProductContext } from "../context/ProductContext";
import { useDebounce } from "../hooks/useDebounce";
import { useEffect } from "react";

const NavigationBar: React.FC = () => {
  const { searchQuery, setSearchQuery, search, clearSearch, } = useProductContext();
  // Debounce search queries to reduce API calls
  const debouncedQuery = useDebounce(searchQuery);
  useEffect(() => {search(debouncedQuery); }, [debouncedQuery, search]);


  return (
    <nav className="navigation-bar">
      <NavLink to="/" end className={({ isActive }) => (isActive ? "active" : "")}>
        Home
      </NavLink>

      <div className="search-box">
        <input value={searchQuery} onChange={(e) => setSearchQuery(e.target.value)} placeholder="Search products..."/>
        
        {searchQuery && <button onClick={clearSearch}>X</button>}
        
      </div>
    </nav>
  );
};

export default NavigationBar;
