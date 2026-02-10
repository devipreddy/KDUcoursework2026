import type { Product } from "../types/Products";
import ProductCard from "./ProductCard";

// Grid component for displaying products; shows empty state if no results
type ProductGridProps = {
  products: Product[];
};

const ProductGrid: React.FC<ProductGridProps> = ({ products }) => {
  if (products.length === 0) {
    return <p className="empty-state">No results found</p>;
  }

  return (
    <div className="products-container">
      {products.map((product) => (<ProductCard key={product.id} product={product} />))}
    </div>
  );
};

export default ProductGrid;
