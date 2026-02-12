import { Suspense } from "react";
import { Outlet } from "react-router-dom";
import NavigationBar from "../NavigationBar/NavigationBar";

const Layout: React.FC = () => {
  return (
    <div>
      <NavigationBar />

      <main>
        <Suspense>
          <Outlet />
        </Suspense>
      </main>
    </div>
  );
};

export default Layout;
