import { Routes, Route } from "react-router-dom";
import Layout from "./components/Layout";
import { ProductProvider } from "./context/ProductContext";
import { lazy } from "react";

const Home = lazy(() => import("./pages/Home"));
const ProductDetails = lazy(() => import("./pages/ProductDetails"));

const App: React.FC = () => {
  return (
    <ProductProvider>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Home />} />
          <Route path="product/:id" element={<ProductDetails />} />
        </Route>
      </Routes>
    </ProductProvider>
  );
};

export default App;
