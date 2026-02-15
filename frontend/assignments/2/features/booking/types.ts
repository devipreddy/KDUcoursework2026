
export interface PricingConfig {
  perBedroom: number;
  perBathroom: number;
  perHour: number;
}

export interface ConfigType {
  id: string;
  name: string;
  basePrice: number;  
}

export interface Frequency {
  id: string;
  label: string;
  multiplier: number;
}

export interface Extra {
  id: string;
  name: string;
  price: number;
}

export interface ConfigResponse {
  pricing: PricingConfig;   
  types: ConfigType[];
  frequencies: Frequency[];
  extras: Extra[];
  timeSlots: string[];
}

export interface BookingFormData {
  type: string;
  frequency: string;
  bedrooms: number;
  bathrooms: number;
  hours: number;
  date: string;
  time: string;
  email: string;
  cardNumber: string;
  expiry: string;
  cvv: string;
  extras: string[];
  address: string;  
}