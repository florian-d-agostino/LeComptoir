import type { Product } from "./types.js";

export const CATALOG: readonly Product[] = [
    { reference: "CAFE", label: "Café Espresso", unitPrice: 2.5, category: "BEVERAGE" },
    { reference: "THE", label: "Thé Vert", unitPrice: 3.0, category: "BEVERAGE" },
    { reference: "SANDWICH", label: "Sandwich Club", unitPrice: 5.5, category: "FOOD" },
    { reference: "SOUPE", label: "Soupe de Légumes", unitPrice: 4.0, category: "FOOD" },
];
