import { createApi, fetchBaseQuery  } from "@reduxjs/toolkit/query/react";
import type { User, UsersResponse, CreateUserPayload } from "../../type/User";

export const usersApi = createApi({

    reducerPath: "usersApi",

    baseQuery: fetchBaseQuery({
        baseUrl: "https://dummyjson.com",
    }),

    tagTypes: ["Users"],

    endpoints: (builder) => ({
        getUsers: builder.query<User[], void>({
            query: () => ({
                url: "/users",
                method: "GET",
            }),
            transformResponse: (response: UsersResponse) => response.users,
            providesTags: (result) => result ? [ ...result.map(({ id }) => ({ type: "Users" as const, id })),{ type: "Users", id: "LIST" },]: [{ type: "Users", id: "LIST" }],
        }),


        addUser: builder.mutation<User, CreateUserPayload>({
            query: (newUser) => ({
                url: "/users/add",
                method: "POST",
                body: newUser,
            }),
            invalidatesTags: [{ type: "Users", id: "LIST" }],
        }),

        getUserById: builder.query<User, number>({
            query: (id) => ({
                url: `/users/${id}`,
                method: "GET",
            }),
            providesTags: (result, error, id) =>
                result ? [{ type: "Users", id }] : [],
        }),

    }),


});

export const { useGetUsersQuery,useAddUserMutation, useGetUserByIdQuery } = usersApi;