import { useNavigate } from "react-router-dom";
import { useCallback } from "react";
import type { Product } from "../../types/Products";
import { getDiscountedPrice } from "../../utils/pricing";
import styles from "./ProductCard.module.scss";

type ProductCardProps = {
  product: Product;
};

const ProductCard: React.FC<ProductCardProps> = ({ product }) => {
  const navigate = useNavigate();
  const discountedPrice = getDiscountedPrice(product.price, product.discountPercentage);

  // Navigate to product details page
  const handleClick = useCallback(() => {
    navigate(`/product/${product.id}`);
  }, [navigate, product.id]);

  return (
    <div
      className={styles.card}
      onClick={handleClick}
      role="button"
      tabIndex={0}
      onKeyDown={(e) => e.key === "Enter" && handleClick()}
    >
      <img src={product.thumbnail} alt={product.title} className={styles.thumbnail} />

      <h3>{product.title}</h3>

      <p className={styles.price}>
        {discountedPrice ? (
          <>
            <span className={styles.original}>${product.price}</span>
            <strong>${discountedPrice}</strong>
          </>
        ) : (
          <strong>${product.price}</strong>
        )}
      </p>

      <p className={styles.rating}>{product.rating}</p>
      <p className={styles.brand}>{product.brand}</p>
    </div>
  );
};

export default ProductCard;
