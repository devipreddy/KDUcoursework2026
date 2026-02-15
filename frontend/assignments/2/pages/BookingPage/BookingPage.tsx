import BookingForm from "../../components/BookingForm/BookingForm";
import BookingSummary from "../../components/BookingSummary/BookingSummary";
import styles from "./BookingPage.module.scss";

const BookingPage = () => {
  
  return (
    <div className={styles.pageContainer}>
      <div className={styles.formSection}>
        <div className={styles.pageHeader}>
          <h1>Schedule Your Cleaning</h1>
          <p>Fill in your details below to book a cleaning service</p>
        </div>
        <BookingForm />
      </div>
      <div className={styles.summarySection}>
        <BookingSummary />
      </div>
    </div>
  );
};

export default BookingPage;