import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { ProductService } from '../services/product.service';

@Component({
  selector: 'app-crear',
  templateUrl: './crear.component.html',
  styleUrls: ['./crear.component.css'],
})
export class CrearComponent {
  form: FormGroup;
  loadingCreate = false;

  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
    private router: Router
  ) {
    this.form = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      category: ['', Validators.required],
      code: ['', Validators.required],
      price: [0, [Validators.required, Validators.min(0)]],
      stock: [0, [Validators.required, Validators.min(0)]],
    });
  }

  handleBack() {
    this.router.navigate(['/product']);
  }

  createProduct() {
    if (this.form.invalid) return;

    this.loadingCreate = true;

    this.productService.CreateProduct(this.form.value).subscribe({
      next: () => this.router.navigate(['/product']),
      error: (err) => {
        console.error('Error creating product', err);
        this.loadingCreate = false;
      },
    });
  }
}
