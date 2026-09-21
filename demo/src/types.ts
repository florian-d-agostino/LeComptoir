export interface Product {
    readonly reference: string;
    readonly label: string;
    readonly unitPrice: number;
    readonly category: string;
}

export interface CartLine {
    readonly product: Product;
    readonly quantity: number;
}

export interface Cart {
    readonly lines: readonly CartLines[];
    readonly total: number;
}