import { useSelector } from "react-redux";
import type { RootState } from "../../app/store";
import { Button } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { resetBooking } from "../../features/booking/bookingSlice";
import { useDispatch } from "react-redux";
import styles from "./ConfirmationPage.module.scss";

const ConfirmationPage = () => {
  const booking = useSelector( (s: RootState) => s.booking.confirmedBooking);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  if (!booking) {
    navigate("/");
    return null;
  }

  return (
    <div className={styles.pageContainer}>
      <div className={styles.confirmationCard}>
        <h1 className={styles.title}>Booking Confirmed!</h1>
        <p className={styles.subtitle}> Your cleaning service has been successfully scheduled. A confirmation email will be sent shortly.</p>

        <span className={styles.statusBadge}>Booking Successful</span>

        <div className={styles.detailsSection}>
          <div className={styles.detailRow}>
            <span className={styles.label}>Booking ID</span>
            <span className={styles.value}>{booking.bookingId}</span>
          </div>

          <div className={styles.detailRow}>
            <span className={styles.label}>Service Type</span>
            <span className={styles.value}>{booking.type}</span>
          </div>

          <div className={styles.detailRow}>
            <span className={styles.label}>Date</span>
            <span className={styles.value}>{booking.date}</span>
          </div>

          <div className={styles.detailRow}>
            <span className={styles.label}>Time</span>
            <span className={styles.value}>{booking.time}</span>
          </div>

          <div className={styles.detailRow}>
            <span className={styles.label}>Duration</span>
            <span className={styles.value}>{booking.hours} hours</span>
          </div>

          <div className={styles.detailRow}>
            <span className={styles.label}>Address</span>
            <span className={styles.value}>{booking.address}</span>
          </div>

          <div className={styles.detailRow}>
            <span className={styles.label}>Email</span>
            <span className={styles.value}>{booking.email}</span>
          </div>
        </div>

        <div className={styles.infoBox}>
          <strong>What happens next?</strong>
          <p> Our team will confirm your booking within 24 hours. You'll receive a confirmation call and email with the assigned cleaner's details.</p>
        </div>

        <div className={styles.actionButtons}>
          <Button variant="contained" size="large" className={styles.primaryButton} onClick={() => {dispatch(resetBooking());
              navigate("/");}}> Book Another Service </Button>

          <Button variant="outlined" size="large" className={styles.secondaryButton} onClick={() => window.print()}>Print Receipt </Button>
        </div>
      </div>
    </div>
  );
};

export default ConfirmationPage;