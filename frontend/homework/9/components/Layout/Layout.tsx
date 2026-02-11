import { Suspense } from "react";
import { Outlet } from "react-router-dom";
import NavigationBar from "../NavigationBar/NavigationBar";
import Loader from "../Loader";
import styles from "./Layout.module.scss";

const Layout: React.FC = () => {
  return (
    <div className={styles.layout}>
      <NavigationBar />

      <main className={styles.content}>
        <Suspense fallback={<Loader />}>
          <Outlet />
        </Suspense>
      </main>
    </div>
  );
};

export default Layout;
