import { useState, useEffect } from "react";
import type { Product } from "../types/Products";
import ProductCard from "../components/ProductCard";


const Home: React.FC = () => {
  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {

    let timeoutId: number;

    const fetchProducts = async () => {

        try {
            const resultOfFetch = await fetch("https://dummyjson.com/products");
            if (!resultOfFetch.ok) throw new Error("API failed");

            const productsData = await resultOfFetch.json();
            setProducts(productsData.products);
        } catch (e) {
            setError("Failed to fetch products");
        } finally {
            timeoutId = window.setTimeout(() => {setLoading(false);}, 1000);
        }
    };

    fetchProducts();

    return () => {
        clearTimeout(timeoutId);
    };
  }, []);



  return (
    <div className="home">
      {loading && <p>Loading products...</p>}

      {error && <p className="error">{error}</p>}

      {!loading && !error && (
        <div className="products-container">
          {products.map((product) => (
            <ProductCard key={product.id} product={product} />
          ))}
        </div>
      )}
    </div>
  );
};

export default Home;
