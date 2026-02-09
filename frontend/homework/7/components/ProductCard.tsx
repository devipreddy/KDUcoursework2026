import { useNavigate } from "react-router-dom";
import { useMemo, useCallback } from "react";
import type { Product } from "../types/Products";

type ProductCardProps = {
  product: Product;
};

const ProductCard: React.FC<ProductCardProps> = ({ product }) => {
  const navigate = useNavigate();

  const discountedPrice = useMemo(() => {
    if (product.discountPercentage <= 0) return null;

    return ( product.price - (product.price * product.discountPercentage) / 100 ).toFixed(2);
  }, [product.price, product.discountPercentage]);

  const handleClick = useCallback(() => {
    navigate(`/product/${product.id}`); }, [navigate, product.id]);

  return (
    <div className="product-card" onClick={handleClick}>
      <img src={product.thumbnail} alt={product.title} className="thumbnail"/>

      <h3 className="product-title">{product.title}</h3>
      <p>
        {discountedPrice ? (
          <>
            <span className="original">${product.price}</span>
            <b>${discountedPrice}</b>
          </>) : ( <>${product.price}</>)}
      </p>

      <p>Rating: {product.rating}</p>
      <p>{product.brand}</p>
    </div>
  );
};

export default ProductCard;
