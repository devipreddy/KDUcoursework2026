import { NavLink } from "react-router-dom";
import { useEffect } from "react";
import styles from "./NavigationBar.module.scss";
import { useAppDispatch } from "../../hooks/useAppDispatch";
import { useAppSelector } from "../../hooks/useAppSelector";
import {
  setSearchQuery,
  clearSearch,
  fetchProducts,
  searchProductsThunk,
} from "../../features/products/productSlice";
import { useDebounce } from "../../hooks/useDebounce";

const NavigationBar: React.FC = () => {
  const dispatch = useAppDispatch();
  const { searchQuery, searchLoading } = useAppSelector((s) => s.products);
  const { totalItems } = useAppSelector((s) => s.cart);

  const debounced = useDebounce(searchQuery, 500);

  useEffect(() => {
    if (!debounced.trim()) {
      dispatch(fetchProducts());
    } else {
      dispatch(searchProductsThunk(debounced));
    }
  }, [debounced, dispatch]);

  return (
    <nav className={styles.nav}>
      <NavLink
        to="/"
        className={({ isActive }) =>
          isActive ? styles.active : styles.link
        }
      >
        Home
      </NavLink>

      <NavLink
        to="/cart"
        className={({ isActive }) =>
          isActive ? styles.active : styles.link
        }
      >
        Cart ({totalItems})
      </NavLink>

      <div className={styles.searchBox}>
        <input
          className={styles.input}
          value={searchQuery}
          onChange={(e) =>
            dispatch(setSearchQuery(e.target.value))
          }
          placeholder="Search products..."
        />

        {searchQuery && (
          <button
            className={styles.clearBtn}
            onClick={() => {
              dispatch(clearSearch());
              dispatch(fetchProducts());
            }}
            aria-label="Clear search"
          >
            ✕
          </button>
        )}

        {searchLoading && <span className={styles.spinner} aria-hidden />}
      </div>
    </nav>
  );
};

export default NavigationBar;
