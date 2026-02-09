import { useMemo, useState } from "react";
import type { Product } from "../types/Products";

type ProductViewProps = {
  product: Product;
};

const ProductView: React.FC<ProductViewProps> = ({ product }) => {
  const [activeImage, setActiveImage] = useState(product.images[0]);

  const discountedPrice = useMemo(() => {
    if (product.discountPercentage <= 0) return null;

    return ( product.price - (product.price * product.discountPercentage) / 100).toFixed(2);
  }, [product.price, product.discountPercentage]);

  return (
    <div className="product-details">
      <div className="product-images">
        <div className="main-image">
          <img src={activeImage} alt={product.title} />
        </div>

        <div className="thumbnail-row">
          {product.images.map((img, idx) => ( <img key={idx} src={img} alt={`${product.title}-${idx}`} className={img === activeImage ? "active" : ""} onClick={() => setActiveImage(img)}/>))}
        </div>
      </div>

      <div className="product-info">
        <h1 className="product-title">{product.title}</h1>
        <p className="product-description">{product.description}</p>

        <p>
          {discountedPrice ? (
            <>
              <span className="original">${product.price}</span>
              <b>${discountedPrice}</b>
            </> ) : ( <>${product.price}</>
          )}
        </p>

        <p>Discount: {product.discountPercentage}%</p>
        <p>Rating: {product.rating} / 5</p>
        <p>Stock: {product.stock}</p>
        <p>Brand: {product.brand}</p>
        <p>Category: {product.category}</p>
      </div>
    </div>
  );
};

export default ProductView;
