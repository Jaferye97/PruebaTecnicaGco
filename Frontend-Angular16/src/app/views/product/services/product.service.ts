import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { urlServicios } from '../../../../environments/url-servicios';
import { Observable } from 'rxjs';
import { Product } from '../interfaces/productInterfaces';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  constructor(private http: HttpClient) {}

  GetAllProducts(): Observable<Product[]> {
    const urlConsulta = `${environment.urlInicial}${urlServicios.product}`;
    return this.http.get<Product[]>(urlConsulta);
  }

  Search(option: string, text: string): Observable<any[]> {
    let url = `${environment.urlInicial}${urlServicios.product}`;

    switch (option) {
      case 'Code':
        url += `/findByCode/${text}`;
        break;
      case 'Category':
        url += `/findByCategory/${text}`;
        break;
      case 'Name':
        url += `/findByName/${text}`;
        break;
    }

    return this.http.get<Product[]>(url);
  }

  ToggleState(id: number): Observable<Product> {
    return this.http.post(
      `${environment.urlInicial}${urlServicios.product}/toggleIsActive/${id}`,
      {}
    );
  }

  GetProduct(id: number): Observable<Product> {
    return this.http.get(
      `${environment.urlInicial}${urlServicios.product}/${id}`
    );
  }

  UpdateProduct(product: Product): Observable<Product> {
    return this.http.put(
      `${environment.urlInicial}${urlServicios.product}/update`,
      product
    );
  }
}
