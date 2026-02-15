import { CircularProgress } from "@mui/material";
import styles from "./Loader.module.scss";

export default function Loader() {
  return (
    <div className={styles.loaderContainer}>
      <div className={styles.spinnerWrapper}>
        <CircularProgress  size={64} sx={{color: 'primary',}}/>
        <p className={styles.text}>Loading your booking options...</p>
        <p className={styles.subtext}>Please wait while we fetch available services</p>
      </div>
    </div>
  );
}