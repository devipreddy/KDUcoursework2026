import styles from "./Header.module.scss";

const Header = () => {
  return (
    <header className={styles.header}>
      <div className={styles.logo}>
        <h2>Cleanly</h2>
      </div>
      <div className={styles.contact}>
        <span>+91 9876543210</span>
      </div>
    </header>
  );
};

export default Header;