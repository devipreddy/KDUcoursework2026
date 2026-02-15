import { configureStore } from "@reduxjs/toolkit";
import bookingReducer from "../features/booking/bookingSlice";
import { bookingApi } from "../features/booking/bookingApi";

export const store = configureStore({
  reducer: {
    booking: bookingReducer,
    [bookingApi.reducerPath]: bookingApi.reducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware().concat(bookingApi.middleware),
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;