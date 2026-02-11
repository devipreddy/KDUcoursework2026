import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import type { Product } from "../../types/Products";
import {
  getProducts,
  searchProducts,
  getProductById,
} from "../../api/products.api";

interface ProductState {
  products: Product[];
  selectedProduct: Product | null;
  searchQuery: string;
  loading: boolean;
  searchLoading: boolean;
  error: string | null;
}

const initialState: ProductState = {
  products: [],
  selectedProduct: null,
  searchQuery: "",
  loading: false,
  searchLoading: false,
  error: null,
};


export const fetchProducts = createAsyncThunk(
  "products/fetchAll",
  async (_arg, thunkAPI) => {
    const data = await getProducts(thunkAPI.signal);
    return data.products as Product[];
  }
);

export const searchProductsThunk = createAsyncThunk(
  "products/search",
  async (query: string, thunkAPI) => {
    const data = await searchProducts(query, thunkAPI.signal);
    return data.products as Product[];
  }
);

export const fetchProductById = createAsyncThunk(
  "products/fetchById",
  async (id: string, thunkAPI) => {
    const data = await getProductById(id, thunkAPI.signal);
    return data as Product;
  }
);

const productSlice = createSlice({
  name: "products",
  initialState,
  reducers: {
    setSearchQuery(state, action) {
      state.searchQuery = action.payload;
    },
    clearSearch(state) {
      state.searchQuery = "";
    },
  },
  extraReducers: (builder) => {
    builder
      /* FETCH ALL */
      .addCase(fetchProducts.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchProducts.fulfilled, (state, action) => {
        state.loading = false;
        state.products = action.payload;
      })
      .addCase(fetchProducts.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message || "Failed to fetch";
      })

      /* SEARCH */
      .addCase(searchProductsThunk.pending, (state) => {
        state.searchLoading = true;
      })
      .addCase(searchProductsThunk.fulfilled, (state, action) => {
        state.searchLoading = false;
        state.products = action.payload;
      })
      .addCase(searchProductsThunk.rejected, (state, action) => {
        state.searchLoading = false;
        state.error = action.error.message || "Search failed";
      })

      /* FETCH BY ID */
      .addCase(fetchProductById.pending, (state) => {
        state.loading = true;
      })
      .addCase(fetchProductById.fulfilled, (state, action) => {
        state.loading = false;
        state.selectedProduct = action.payload;
      })
      .addCase(fetchProductById.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message || "Failed";
      });
  },
});

export const { setSearchQuery, clearSearch } = productSlice.actions;
export default productSlice.reducer;
