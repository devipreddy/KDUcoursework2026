import NavigationBar from "../components/NavigationBar";
import { Suspense } from "react";
import Loader from "../components/Loader";
import { Outlet } from "react-router-dom";

const Layout: React.FC = () => (
  <>
    <NavigationBar />
    <Suspense fallback={<Loader />}>
      <Outlet />
    </Suspense>
  </>
);

export default Layout;