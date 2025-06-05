export default class Product {
  id?: number;
  name?: string;
  description?: string;
  price?: number;
  stock?: number;
  category?: string;
  code?: string;
  dateCreation?: string;
  active?: boolean;
}

export interface Movement {
  id?: number;
  idProduct?: number;
  type?: "ENTRADA" | "SALIDA";
  amount?: number;
  date?: string;
  description?: string;
}

export interface MovementsProduct {
  product?: Product;
  movements?: Movement[];
}
