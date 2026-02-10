import { useNavigate } from "react-router-dom";
import { useCallback } from "react";
import type { Product } from "../types/Products";
import { getDiscountedPrice } from "../utils/pricing";

type ProductCardProps = {
  product: Product;
};

const ProductCard: React.FC<ProductCardProps> = ({ product }) => {
  const navigate = useNavigate();
  const discountedPrice = getDiscountedPrice(product.price, product.discountPercentage);

  // Navigate to product details page
  const handleClick = useCallback(() => { navigate(`/product/${product.id}`);}, [navigate, product.id]);

  return (
    <div className="product-card" onClick={handleClick} role="button" tabIndex={0} onKeyDown={(e) => e.key === "Enter" && handleClick()}>
      
      <img src={product.thumbnail} alt={product.title} className="thumbnail"/>

      <h3>{product.title}</h3>

      <p className="price">
        {discountedPrice ? (
          <>
            <span className="original">${product.price}</span>
            <strong>${discountedPrice}</strong>
          </>) : (<strong>${product.price}</strong>)}
      </p>

      <p>{product.rating}</p>
      <p className="brand">{product.brand}</p>
    </div>
  );
};

export default ProductCard;
