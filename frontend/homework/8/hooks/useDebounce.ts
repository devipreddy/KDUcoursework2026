import { useEffect, useState } from "react";

// Delays updates to value by 500ms
export function useDebounce<T>(value: T): T {
  const [debounced, setDebounced] = useState(value);

  useEffect(() => {
    const id = setTimeout(() => setDebounced(value), import.meta.env.DELAY);
    return () => clearTimeout(id); // Cleanup pending timeouts
  }, [value, import.meta.env.DELAY ]);

  return debounced;
}
