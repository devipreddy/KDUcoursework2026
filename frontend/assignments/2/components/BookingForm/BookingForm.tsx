import { Button, } from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import type { RootState } from "../../app/store";
import { updateField, toggleExtra, setConfirmedBooking, } from "../../features/booking/bookingSlice";
import { useGetConfigQuery, useCreateBookingMutation,} from "../../features/booking/bookingApi";
import { useNavigate } from "react-router-dom";
import Loader from "../Loader/Loader";
import { useState } from "react";
import styles from "./BookingForm.module.scss";

const BookingForm = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const form = useSelector((s: RootState) => s.booking.form);
  const { data, isLoading } = useGetConfigQuery();
  const [createBooking, { isLoading: submitting }] = useCreateBookingMutation();
  const [agreed, setAgreed] = useState(false);
  if (isLoading || !data) return <Loader />;

  const increment = (field: keyof typeof form) => {dispatch( updateField({ field, value: (form[field] as number) + 1, }));};

  const decrement = (field: keyof typeof form) => {dispatch( updateField({ field, value: Math.max(0, (form[field] as number) - 1),}));};

  const emailValid = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(form.email);
  const cardValid = form.cardNumber.replace(/\s/g, "").length === 16;
  const cvvValid = form.cvv.length === 3;
  const expiryValid = form.expiry.length >= 4;
  const today = new Date();
  today.setHours(0, 0, 0, 0);
  const selectedDate = form.date ? new Date(form.date) : null;
  const dateValid = selectedDate && selectedDate >= today;
  const isValid = form.type && form.frequency && form.bedrooms > 0 && form.bathrooms > 0 && form.hours > 0 && dateValid && form.time && emailValid && cardValid && cvvValid && expiryValid && form.address.length > 5 && agreed;

  const submit = async () => {
    try {
      const res = await createBooking(form).unwrap();
      dispatch( setConfirmedBooking({...form, bookingId: res.bookingId,}));
      navigate("/confirmation");
    } catch (error) {
      alert("Booking failed. Please try again.");
    }
  };

  return (
    <div className={styles.formWrapper}>
      <section>
        <h3>Cleaning Preferences</h3>
        <div className={styles.preferenceGroup}>
          <p className={styles.groupLabel}>Service Type</p>
          <div className={styles.optionsWrapper}>
            {data.types.map((t) => (<Button key={t.id} variant={form.type === t.id ? "contained" : "outlined"} onClick={() => dispatch(updateField({ field: "type", value: t.id }))}>{t.name}</Button>))}
          </div>
        </div>

        <div className={styles.preferenceGroup}>
          <p className={styles.groupLabel}>Frequency</p>
          <div className={styles.optionsWrapper}>
            {data.frequencies.map((f) => (<Button key={f.id} variant={ form.frequency === f.id ? "contained" : "outlined"}onClick={() =>dispatch(updateField({ field: "frequency", value: f.id }))}>{f.label} </Button>))}
          </div>
        </div>
      </section>

      <section>
        <h3>Tell us about your home</h3>

        <div className={styles.counterGroup}>
          <label>Bedrooms</label>
          <div className={styles.counterControl}>
            <Button onClick={() => decrement("bedrooms")}> - </Button>
            <input type="text" readOnly value={form.bedrooms} />
            <Button onClick={() => increment("bedrooms")}> + </Button>
          </div>
        </div>

        <div className={styles.counterGroup}>
          <label>Bathrooms</label>
          <div className={styles.counterControl}>
            <Button onClick={() => decrement("bathrooms")}> - </Button>
            <input type="text" readOnly value={form.bathrooms} />
            <Button onClick={() => increment("bathrooms")}> + </Button>
          </div>
        </div>
      </section>

      <section>
        <h3>Choose hours & date</h3>
        <div className={styles.counterGroup}>
          <label>Hours</label>
          <div className={styles.counterControl}>
            <Button onClick={() => decrement("hours")}> - </Button>
            <input type="text" readOnly value={form.hours} />
            <Button onClick={() => increment("hours")}> + </Button>
          </div>
        </div>

        <div className={styles.dateTimeGroup}>
          <div className={styles.field}>
            <label>Date</label>
            <input type="date" value={form.date} onChange={(e) => dispatch( updateField({ field: "date", value: e.target.value,}))}/>
          </div>

          <div className={styles.field}>
            <label>Time</label>
            <select value={form.time} onChange={(e) => dispatch( updateField({field: "time", value: e.target.value,}))}>
              <option value="">Select time</option>
              {data.timeSlots.map((slot) => ( <option key={slot} value={slot}>{slot}</option>))}
            </select>
          </div>
        </div>
      </section>

      <section>
        <h3>Need any extras?</h3>
        <div className={styles.extrasSection}>
          {data.extras.map((extra) => (
            <div key={extra.id} className={styles.extraItem}>
              <input type="checkbox" id={`extra-${extra.id}`} checked={form.extras.includes(extra.id)} onChange={() => dispatch(toggleExtra(extra.id))} />
              <label htmlFor={`extra-${extra.id}`} className={styles.extraName}>{extra.name}</label>
              <div className={styles.extraPrice}> +{extra.price} </div>
            </div>))}
        </div>
      </section>

      <section>
        <h3>Payment Details</h3>
        <div className={styles.textFieldWrapper}>
          <label>Card Number</label>
          <input type="text" placeholder="1234 5678 9012 3456" value={form.cardNumber} maxLength={19} onChange={(e) => { let value = e.target.value.replace(/\s/g, "");
              if (value.length <= 16) {
                value = value.replace(/(\d{4})/g, "$1 ").trim();
              }
              dispatch( updateField({ field: "cardNumber", value,}));}}/>
          {form.cardNumber && form.cardNumber.replace(/\s/g, "").length !== 16 && (<div className={styles.errorMessage}>Card number must be 16 digits</div>)}
        </div>

        <div className={styles.fieldRow}>
          <div className={styles.textFieldWrapper}>
            <label>Expiry (MM/YY)</label>
            <input type="text" placeholder="MM/YY" value={form.expiry} maxLength={5} onChange={(e) => { let value = e.target.value.replace(/\D/g, "");
                if (value.length >= 2) {
                  value = value.slice(0, 2) + "/" + value.slice(2, 4);
                }
                dispatch( updateField({ field: "expiry", value, }));}}/>
            {form.expiry && form.expiry.length < 5 && ( <div className={styles.errorMessage}>Invalid format (MM/YY)</div>)}
          </div>

          <div className={styles.textFieldWrapper}>
            <label>CVV</label>
            <input type="text" placeholder="123" value={form.cvv} maxLength={3} onChange={(e) => { const value = e.target.value.replace(/\D/g, "");
                dispatch(updateField({field: "cvv",value,}));}}/>
            {form.cvv && form.cvv.length !== 3 && ( <div className={styles.errorMessage}>CVV must be 3 digits</div>)}
          </div>
        </div>
      </section>

      <section>
        <h3>Personal Details</h3>
        <div className={styles.textFieldWrapper}>
          <label>Email</label>
          <input type="email" placeholder="your@email.com" value={form.email} onChange={(e) => dispatch(updateField({ field: "email", value: e.target.value }))}/>
          {form.email && !emailValid && (<div className={styles.errorMessage}>Please enter a valid email</div>)}
        </div>

        <div className={styles.textFieldWrapper}>
          <label>Address</label>
          <textarea placeholder="Enter your complete address" value={form.address} onChange={(e) =>dispatch(updateField({ field: "address", value: e.target.value }))}/>
          {form.address && form.address.length <= 5 && (<div className={styles.errorMessage}>Address must be at least 6 characters</div>)}
        </div>
      </section>

      <div className={styles.checkboxGroup}>
        <label>
          <input type="checkbox" checked={agreed} onChange={() => setAgreed(!agreed)}/>
          <span>I agree to the <strong>Terms & Conditions</strong> and confirm all details are correct</span>
        </label>
      </div>

      <Button fullWidth variant="contained" size="large" disabled={!isValid || submitting} onClick={submit} className={styles.submitButton}>{submitting ? "Processing..." : "Complete Booking"}</Button>
    </div>
  );
};

export default BookingForm;