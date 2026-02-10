
const BASE_URL = import.meta.env.VITE_API_BASE_URL;

// Fetch all products
export async function getProducts(signal?: AbortSignal) {
  const res = await fetch(`${BASE_URL}/products`, { signal });
  if (!res.ok) throw new Error("Failed to fetch products");
  return res.json();
}

// Search products by query string
export async function searchProducts(query: string, signal?: AbortSignal) {
  const res = await fetch(
    `${BASE_URL}/products/search?q=${encodeURIComponent(query)}`,
    { signal }
  );
  if (!res.ok) throw new Error("Search failed");
  return res.json();
}
// Fetch single product by ID

export async function getProductById(id: string, signal?: AbortSignal) {
  const res = await fetch(`${BASE_URL}/products/${id}`, { signal });
  if (!res.ok) {
    if (res.status === 404) throw new Error("Product not found");
    throw new Error("Failed to fetch product");
  }
  return res.json();
}
