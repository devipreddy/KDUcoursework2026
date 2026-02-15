import { Routes, Route } from "react-router-dom";
import { lazy } from "react";
import Layout from "../components/Layout/Layout";

const BookingPage = lazy(() => import("../pages/BookingPage/BookingPage"));
const ConfirmationPage = lazy(() => import("../pages/ConformationPage/ConformationPage"));

const AppRouter = () => {
  return (
    <Routes>
      <Route element={<Layout />}>
        <Route path="/" index element={<BookingPage />} />
        <Route path="/confirmation" element={<ConfirmationPage />} />
      </Route>
    </Routes>
  );
};

export default AppRouter;