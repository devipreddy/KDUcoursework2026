import { Routes, Route } from "react-router-dom";
import Layout from "./components/Layout/Layout";
import { lazy } from "react";

const Home = lazy(() => import("./pages/Home"));
const ProductDetails = lazy( () => import("./pages/ProductDetails"));
const Cart = lazy(() => import("./pages/Cart"));

const App: React.FC = () => {
  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route index element={<Home />} />
        <Route path="product/:id" element={<ProductDetails />} />
        <Route path="cart" element={<Cart />} />
      </Route>
    </Routes>
  );
};

export default App;
