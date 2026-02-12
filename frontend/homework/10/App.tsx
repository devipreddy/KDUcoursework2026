import { Routes, Route } from "react-router-dom";
import Layout from "./components/Layout/Layout";
import { lazy } from "react";

const Home = lazy(() => import("./pages/Home"));
const UserPage = lazy(() => import("./pages/UserPage"));

const App: React.FC = () => {
  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route index element={<Home />} />
        <Route path="/users/:id" element={<UserPage />} />       
      </Route>
    </Routes>
  );
};

export default App;
