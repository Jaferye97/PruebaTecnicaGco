import { Component } from '@angular/core';
import { Router } from '@angular/router';

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

  inputs = {
    option: '',
    text: '',
  };

  constructor(private service: ProductService, private router: Router) {}

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

  handleToggle(id: number) {
    this.loading = true;
    this.service.ToggleState(id).subscribe(() => {
      Swal.fire('Success', 'Product updated successfully.', 'success');
      this.getAllData();
    });
  }

  navigateToEdit(id: number) {
    this.router.navigate([`/product/${id}`]);
  }

  navigateToCreate() {
    console.log('/product/crear');
    this.router.navigate(['/product/crear']);
  }
}
