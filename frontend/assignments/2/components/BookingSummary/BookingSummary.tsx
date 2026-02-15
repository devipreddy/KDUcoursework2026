import { useSelector } from "react-redux";
import type { RootState } from "../../app/store";
import { useGetConfigQuery } from "../../features/booking/bookingApi";
import styles from "./BookingSummary.module.scss";

const BookingSummary = () => {
  const form = useSelector((s: RootState) => s.booking.form);
  const { data } = useGetConfigQuery();
  if (!data) return null;
  const type = data.types.find(t => t.id === form.type);
  const frequency = data.frequencies.find(f => f.id === form.frequency);
  const pricing = data.pricing;
  let subtotal = 0;
  if (type) subtotal += type.basePrice;
  subtotal += form.bedrooms * pricing.perBedroom;
  subtotal += form.bathrooms * pricing.perBathroom;
  subtotal += form.hours * pricing.perHour;
  const selectedExtras = form.extras.map(id => data.extras.find(e => e.id === id)).filter(Boolean);

  selectedExtras.forEach(extra => {if (extra) subtotal += extra.price;});

  const total = frequency ? subtotal * frequency.multiplier : subtotal;

  return (
    <div className={styles.summaryWrapper}>
      <div className={styles.summaryCard}>
        <h2>{type?.name || "Cleaning Service"}</h2>
        <div style={{ display: "flex", gap: "8px", alignItems: "center", marginBottom: "16px" }}>
          <span></span>
          <span>{form.date || "Select date"}</span>
          <span>•</span>
          <span></span>
          <span>{form.time || "Select time"}</span>
        </div>

        <hr className={styles.sectionDivider} />
        <div className={styles.details}>
          <div className={styles.detailItem}>
            <div className={styles.content}>
              <div className={styles.label}>Bedrooms</div>
              <div className={styles.value}>{form.bedrooms}</div>
            </div>
          </div>

          <div className={styles.detailItem}>
            <div className={styles.content}>
              <div className={styles.label}>Bathrooms</div>
              <div className={styles.value}>{form.bathrooms}</div>
            </div>
          </div>

          <div className={styles.detailItem}>
            <div className={styles.content}>
              <div className={styles.label}>Duration</div>
              <div className={styles.value}>{form.hours} hours</div>
            </div>
          </div>

          {frequency && ( <div className={styles.detailItem}>
              <div className={styles.content}>
                <div className={styles.label}>Frequency</div>
                <div className={styles.value}>{frequency.label}</div>
              </div>
            </div>)}
        </div>

        {selectedExtras.length > 0 && ( <><hr className={styles.sectionDivider} />
            <div className={styles.extrasList}>
              {selectedExtras.map(extra => (
                <div key={extra?.id} className={styles.extraItem}>
                  <span className={styles.name}>{extra?.name}</span>
                  <span className={styles.price}>+{extra?.price}</span>
                </div>))}
            </div></>)}

        <hr className={styles.sectionDivider} />
        <div className={styles.priceSection}>
          <div className={styles.priceRow + " " + styles.subtotal}>
            <span>Subtotal</span>
            <span className={styles.price}>{subtotal.toFixed(2)}</span>
          </div>

          {frequency && frequency.multiplier > 1 && (<div className={styles.priceRow}>
              <span>{frequency.label} Discount ({(frequency.multiplier - 1) * 100}% off)</span>
              <span className={styles.discount}>-{(subtotal - (subtotal * frequency.multiplier)).toFixed(2)}</span>
            </div>)}

          <div className={styles.priceRow + " " + styles.total}>
            <span>Total Amount</span>
            <span>{total.toFixed(2)}</span>
          </div>
        </div>

        {form.address && (<div className={styles.tooltip}>
             <strong>Address:</strong> {form.address}
          </div>)}
      </div>
    </div>
  );
};

export default BookingSummary;