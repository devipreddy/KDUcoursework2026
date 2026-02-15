import { createSlice} from "@reduxjs/toolkit";
import type { PayloadAction } from "@reduxjs/toolkit";
import type { BookingFormData } from "./types";

const initialFormState: BookingFormData = {
  type: "",
  frequency: "",
  bedrooms: 0,
  bathrooms: 0,
  hours: 0,
  date: "",
  time: "",
  email: "",
  address: "",
  cardNumber: "",
  expiry: "",
  cvv: "",
  extras: [],
};

interface BookingState {
  form: BookingFormData;
  confirmedBooking: any | null;
}

const initialState: BookingState = {
  form: initialFormState,
  confirmedBooking: null,
};

const bookingSlice = createSlice({
  name: "booking",
  initialState,
  reducers: { 
    updateField< K extends keyof BookingFormData>(state: BookingState, action: PayloadAction<{ field: K ; value: BookingFormData[K]}>) {
      const { field, value } = action.payload
      state.form[field] = value
    },

    toggleExtra(state, action: PayloadAction<string>) {
      const id = action.payload;
      if (state.form.extras.includes(id)) {
        state.form.extras = state.form.extras.filter((e) => e !== id);
      } else {
        state.form.extras.push(id);
      }
    },

    setConfirmedBooking(state, action: PayloadAction<any>) {
      state.confirmedBooking = action.payload;
    },

    resetBooking(state) {
      state.form = initialFormState;
      state.confirmedBooking = null;
    },
  },
});

export const { updateField, toggleExtra, setConfirmedBooking, resetBooking,} = bookingSlice.actions;
export default bookingSlice.reducer;