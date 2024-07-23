import { CartItem } from "./cart";

export interface Order {
    id: number;
    userId: number;
    items: CartItem[];
    totalAmount: number;
    status: string;
    orderDate: Date;
  }