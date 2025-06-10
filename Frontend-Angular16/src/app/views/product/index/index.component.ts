import { Component } from '@angular/core';
import { Product } from '../interfaces/productInterfaces';
import { ProductService } from '../services/product.service';
import Swal from 'sweetalert2';

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

  inputs = {
    option: '',
    text: '',
  };

  constructor(private service: ProductService) {}

  ngOnInit() {
    this.getAllData();
  }

  submitForm() {
    this.getDataFilter();
  }

  getAllData() {
    this.loading = true;
    this.service.GetAllProducts().subscribe((res) => {
      this.products = res;
      this.loading = false;
    });
  }

  getDataFilter() {
    if (!this.inputs.text) {
      Swal.fire('Error', 'Please enter text to search.', 'error');
      return;
    }

    if (!this.inputs.option) {
      Swal.fire('Error', 'Please select option to search.', 'error');
      return;
    }

    this.loading = true;
    this.service
      .Search(this.inputs.option, this.inputs.text)
      .subscribe((res) => {
        this.products = res;
        this.loading = false;
      });
  }
}
