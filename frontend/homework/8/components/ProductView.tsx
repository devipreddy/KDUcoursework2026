import { useState } from "react";
import type { Product } from "../types/Products";
import { getDiscountedPrice } from "../utils/pricing";

type ProductViewProps = {
  product: Product;
};

const ProductView: React.FC<ProductViewProps> = ({ product }) => {
  // Use product images or fallback to thumbnail
  const images = product.images && product.images.length > 0 ? product.images : [product.thumbnail];
  const [activeImage, setActiveImage] = useState(images[0]);
  const discountedPrice = getDiscountedPrice(product.price, product.discountPercentage);

  return (
    <div className="product-details">
      <div className="product-gallery">
        <div className="main-image">
          <img src={activeImage} alt={product.title} />
        </div>

        <div className="thumbnail-row">
          {images.map((img) => (
            <button key={img} className={img === activeImage ? "active" : ""}onClick={() => setActiveImage(img)}>
              <img src={img} alt={product.title} />
            </button>
          ))}
        </div>
      </div>

      <div className="product-info">
        <h1 className="product-title">{product.title}</h1>
        <p className="product-description">{product.description}</p>

        <p className="price">
          {discountedPrice ? (
            <>
              <span className="original">${product.price}</span>
              <strong>${discountedPrice}</strong>
            </>) : (<strong>${product.price}</strong>)}
        </p>

        <p>Rating: {product.rating}</p>
        <p>Stock: {product.stock}</p>
        <p>Brand: {product.brand}</p>
        <p>Category: {product.category}</p>
      </div>
    </div>
  );
};

export default ProductView;
