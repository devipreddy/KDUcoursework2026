import styles from "./ProductGrid.module.scss";
import ProductCard from "../ProductCard";
import type { Product } from "../../types/Products";

interface Props {
  products: Product[];
}

const ProductGrid: React.FC<Props> = ({ products }) => {
  if (products.length === 0) {
    return <p className={styles.empty}>No results found</p>;
  }

  return (
    <div className={styles.grid}>
      {products.map((p) => (
        <ProductCard key={p.id} product={p} />
      ))}
    </div>
  );
};

export default ProductGrid;
