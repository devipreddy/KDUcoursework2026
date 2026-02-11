import styles from "./Loader.module.scss";

const Loader: React.FC = () => {
  return (
    <div className={styles.loader}>
      <p>Loading...</p>
    </div>
  );
};

export default Loader;
