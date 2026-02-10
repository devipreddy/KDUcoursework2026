import { createContext, useContext, useState, useCallback,} from "react";
import type { Product } from "../types/Products";
import { getProducts, searchProducts, getProductById,} from "../api/products.api";

// Global state for product data and search functionality
type ProductContextType = {
  products: Product[];
  selectedProduct: Product | null;
  searchQuery: string;
  loading: boolean;
  searchLoading: boolean;
  error: string | null;
  fetchAll: () => Promise<void>;
  search: (q: string) => Promise<void>;
  fetchById: (id: string) => Promise<void>;
  setSearchQuery: (q: string) => void;
  clearSearch: () => void;
};


const ProductContext = createContext<ProductContextType | null>(null);

// Custom hook for accessing product context
export const useProductContext = () => {
  const context = useContext(ProductContext);
  if (!context) throw new Error("ProductContext missing");
  return context;
};

export const ProductProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [products, setProducts] = useState<Product[]>([]);
  const [selectedProduct, setSelectedProduct] = useState<Product | null>(null);
  const [searchQuery, setSearchQuery] = useState("");
  const [loading, setLoading] = useState(false);
  const [searchLoading, setSearchLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  // Fetch all products
  const fetchAll = useCallback(async () => {
    const controller = new AbortController();
    setLoading(true);
    setError(null);
    try {
      const data = await getProducts(controller.signal);
      setProducts(data.products ?? []);
    } catch (e: any) {
      setError(e.message);
    } finally {
      setLoading(false);
    }

  }, []);
  
// Search products; empty query resets to all products
  
  const search = useCallback(async (q: string) => {
    if (!q.trim()) return fetchAll();

    const controller = new AbortController();

    setSearchLoading(true);
    setError(null);
    try {
      const data = await searchProducts(q, controller.signal);
      setProducts(data.products ?? []);
    } catch (e: any) {
      setError(e.message);
    } finally {
      setSearchLoading(false);
    }
  }, [fetchAll]);

  // Fetch single product by ID for details page
  const fetchById = useCallback(async (id: string) => {
    const controller = new AbortController();
    setLoading(true);
    setError(null);
    try {
      const data = await getProductById(id, controller.signal);
      setSelectedProduct(data);
    } catch (e: any) {
      setError(e.message);
    } finally {
      setLoading(false);
    }
  }, []);

  const clearSearch = () => {
    setSearchQuery("");
    fetchAll();
  };

  return (
    <ProductContext.Provider value={{ products, selectedProduct, searchQuery, loading, searchLoading, error, fetchAll, search, fetchById, setSearchQuery, clearSearch,}} >
      {children}
    </ProductContext.Provider>
  );
};
