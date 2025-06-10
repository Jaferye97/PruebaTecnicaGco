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
}
