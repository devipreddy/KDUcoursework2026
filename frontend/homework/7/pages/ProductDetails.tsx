import { useParams, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import type { Product } from "../types/Products";
import ProductView from "../components/ProductView";

const ProductDetails: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const [product, setProduct] = useState<Product | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!id) return;
    let timeoutId: number;

    const fetchProduct = async () => {
        try {
            const res = await fetch(`https://dummyjson.com/products/${id}`);
            if (!res.ok) throw new Error("Invalid product ID");

            const data: Product = await res.json();
            setProduct(data);
        } catch (e) {
            setError("Failed to fetch product");
        } finally {

            timeoutId = window.setTimeout(() => { setLoading(false);}, 1000);
        }
    };

    fetchProduct();

    return () => {
        clearTimeout(timeoutId);
    };
  }, [id]);


  if (loading) return <p>Loading product...</p>;
  if (error) return <p>{error}</p>;
  if (!product) return null;

  return (
    <div className="product-details-wrapper">
      <button className="back-button" onClick={() => navigate(-1)}>Back</button>
      <ProductView product={product} />
    </div>
  );
};

export default ProductDetails;
