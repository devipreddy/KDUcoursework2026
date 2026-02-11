
export function getDiscountedPrice( price: number, discount: number ): string | null {
  if (discount <= 0) return null;
  return (price - (price * discount) / 100).toFixed(2);
}
