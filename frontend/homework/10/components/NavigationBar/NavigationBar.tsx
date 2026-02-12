import React from 'react'
import { NavLink } from "react-router-dom";
import styles from "./NavigationBar.module.scss";

function NavigationBar(): React.JSX.Element {
  return (
    <nav className={styles.navigationBar}>
        <div className={styles.brand}>User Directory</div>
        <div className={styles.links}>
            <NavLink to="/" className={({ isActive }) => isActive ? `${styles.link} ${styles.active}` : styles.link }>
                Home
            </NavLink>
        </div>
    </nav>
  )
}

export default NavigationBar;
