import { useEffect } from "react";
import { useAppDispatch } from "../hooks/useAppDispatch";
import { useAppSelector } from "../hooks/useAppSelector";
import { fetchProducts } from "../features/products/productSlice";
import ProductGrid from "../components/ProductGrid/ProductGrid";
import Loader from "../components/Loader";
import ErrorState from "../components/ErrorState";

const Home: React.FC = () => {
  const dispatch = useAppDispatch();
  const { products, loading, error } = useAppSelector(
    (state) => state.products
  );

  useEffect(() => {
    dispatch(fetchProducts());
  }, [dispatch]);

  if (loading) return <Loader />;
  if (error) return <ErrorState message={error} />;

  return <ProductGrid products={products} />;
};

export default Home;
