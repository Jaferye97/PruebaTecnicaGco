import { Component } from '@angular/core';
import { Product } from '../interfaces/productInterfaces';
import { ProductService } from '../services/product.service';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css'],
})
export class IndexComponent {
  loading = true;
  products: Product[] = [];

  displayedColumns: string[] = [
    'code',
    'name',
    'category',
    'stock',
    'price',
    'description',
    'active',
    'action',
  ];

  constructor(private service: ProductService) {}

  ngOnInit() {
    this.getAllData();
  }

  getAllData() {
    this.loading = true;
    this.service.GetAllProducts().subscribe((res) => {
      this.products = res;
      this.loading = false;
    });
  }
}
