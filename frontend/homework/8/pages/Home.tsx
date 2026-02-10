import { useEffect } from "react";
import { useProductContext } from "../context/ProductContext";
import ProductCard from "../components/ProductCard";
import Loader from "../components/Loader";
import ErrorState from "../components/ErrorState";

const Home: React.FC = () => {
  const { products, loading, error, fetchAll } = useProductContext();

  // Load all products on mount
  useEffect(() => {fetchAll();}, [fetchAll]);

  if (loading) return <Loader />;
  if (error) return <ErrorState message={error} />;

  return (
    <div className="products-container">
      {products.length === 0 && <p>No results found</p>}
      {products.map((p) => (
        <ProductCard key={p.id} product={p} />
      ))}
    </div>
  );
};

export default Home;
