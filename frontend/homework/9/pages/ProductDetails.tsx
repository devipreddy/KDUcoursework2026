import { useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { useAppDispatch } from "../hooks/useAppDispatch";
import { useAppSelector } from "../hooks/useAppSelector";
import { fetchProductById } from "../features/products/productSlice";
import ProductView from "../components/ProductView";
import Loader from "../components/Loader";
import ErrorState from "../components/ErrorState";
import styles from "./ProductDetails.module.scss";

const ProductDetails: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const dispatch = useAppDispatch();

  const { selectedProduct, loading, error } = useAppSelector(
    (state) => state.products
  );

  useEffect(() => {
    if (id) dispatch(fetchProductById(id));
  }, [id, dispatch]);

  if (loading) return <Loader />;
  if (error) return <ErrorState message={error} />;
  if (!selectedProduct) return null;

  return (
    <div className={styles.wrapper}>
      <div style={{ textAlign: "left", marginBottom: "1rem" }}>
        <button
          onClick={() => navigate(-1)}
          className={styles.backButton}
          aria-label="Go back"
        >
          ← Back
        </button>
      </div>

      <ProductView product={selectedProduct} />
    </div>
  );
};

export default ProductDetails;
