import { useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { useProductContext } from "../context/ProductContext";
import ProductView from "../components/ProductView";
import Loader from "../components/Loader";
import ErrorState from "../components/ErrorState";

const ProductDetails: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const { selectedProduct, fetchById, loading, error,} = useProductContext();

  // Fetch product by ID from URL param; redirect if missing
  useEffect(() => {
    if (!id) {
      navigate("/");
      return;
    }
    fetchById(id); }, [id, fetchById, navigate]);

  if (loading) return <Loader />;
  if (error) return <ErrorState message={error} />;
  if (!selectedProduct) return null;

  return (
    <div className="product-details-wrapper">
      <button className="back-button" onClick={() => navigate("/")}>
        ← Back to products
      </button>

      <ProductView product={selectedProduct} />
    </div>
  );
};

export default ProductDetails;
