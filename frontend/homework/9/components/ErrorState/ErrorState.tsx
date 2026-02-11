import styles from "./ErrorState.module.scss";

interface Props {
  message: string;
}

const ErrorState: React.FC<Props> = ({ message }) => {
  return (
    <div className={styles.error}>
      <p>{message}</p>
    </div>
  );
};

export default ErrorState;
