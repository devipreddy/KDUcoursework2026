import { useState } from "react";
import styles from "./ProductView.module.scss";
import type { Product } from "../../types/Products";
import { getDiscountedPrice } from "../../utils/pricing";
import { useAppDispatch } from "../../hooks/useAppDispatch";
import { addToCart } from "../../features/cart/cartSlice";

interface Props {
  product: Product;
}

const ProductView: React.FC<Props> = ({ product }) => {
  const dispatch = useAppDispatch();

  const images = product.images && product.images.length > 0 ? product.images : [product.thumbnail];

  const [activeImage, setActiveImage] = useState<string>(images[0]);

  const discountedPrice = getDiscountedPrice(
    product.price,
    product.discountPercentage
  );

  return (
    <div className={styles.wrapper}>
      <div>
        <img src={activeImage} alt={product.title} />

        <div className={styles.thumbRow}>
          {images.map((img, idx) => (
            <button
              key={img + idx}
              onClick={() => setActiveImage(img)}
              aria-pressed={activeImage === img}
              aria-label={`Show image ${idx + 1} for ${product.title}`}
            >
              <img src={img} width={50} alt={`Thumbnail ${idx + 1} of ${product.title}`} />
            </button>
          ))}
        </div>
      </div>

      <div>
        <h1>{product.title}</h1>
        <p>{product.description}</p>

        <p>
          {discountedPrice ? (
            <>
              <span className={styles.original}>
                ${product.price}
              </span>
              <strong>${discountedPrice}</strong>
            </>
          ) : (
            <strong>${product.price}</strong>
          )}
        </p>

        <p>Rating: {product.rating}</p>
        <p>Stock: {product.stock}</p>
        <p>Brand: {product.brand}</p>
        <p>Category: {product.category}</p>

        <button
          className={styles.addBtn}
          onClick={() => dispatch(addToCart(product))}
        >
          Add to Cart
        </button>
      </div>
    </div>
  );
};

export default ProductView;
