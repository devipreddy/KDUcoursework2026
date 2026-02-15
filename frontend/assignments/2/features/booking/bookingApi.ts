import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import type { ConfigResponse, BookingFormData } from "./types";

export const bookingApi = createApi({
  reducerPath: "bookingApi",
  baseQuery: fetchBaseQuery({
    baseUrl: import.meta.env.VITE_BASE_API_URL,
  }),
  endpoints: (builder) => ({
    getConfig: builder.query<ConfigResponse, void>({
      query: () => import.meta.env.VITE_GET_ENDPOINT,
    }),

    createBooking: builder.mutation<{ bookingId: string },BookingFormData>({
      query: (body) => ({
        url: import.meta.env.VITE_POST_ENDPOINT,
        method: "POST",
        body,
      }),
    }),
  }),
});

export const { useGetConfigQuery, useCreateBookingMutation,} = bookingApi;