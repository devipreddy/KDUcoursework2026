import { Outlet } from "react-router-dom";
import Header from "../Header/Header";
import styles from "./Layout.module.scss";

const Layout = () => {
  return (
    <div className={styles.layoutWrapper}>
        <Header />
      <main className={styles.main}>
        <Outlet />
      </main>
    </div>
  );
};

export default Layout;