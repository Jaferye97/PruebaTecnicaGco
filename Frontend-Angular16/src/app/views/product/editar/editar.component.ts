import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';

import Swal from 'sweetalert2';

import { ProductService } from '../services/product.service';

@Component({
  selector: 'app-editar',
  templateUrl: './editar.component.html',
  styleUrls: ['./editar.component.css'],
})
export class EditarComponent {
  productId!: string;
  form: FormGroup;
  loadingPage = true;
  loadingUpdate = false;

  constructor(
    private fb: FormBuilder,
    private service: ProductService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.form = this.fb.group({
      id: [0, Validators.required],
      name: ['', Validators.required],
      description: ['', Validators.required],
      category: ['', Validators.required],
      code: ['', Validators.required],
      price: [null, [Validators.required, Validators.min(0)]],
      stock: [null, [Validators.required, Validators.min(0)]],
      active: [true],
    });
  }

  ngOnInit(): void {
    this.productId = this.route.snapshot.paramMap.get('id')!;
    this.getDataProduct(Number(this.productId));
  }

  getDataProduct(id: number): void {
    this.service.GetProduct(id).subscribe((data) => {
      this.form.patchValue(data);
      this.loadingPage = false;
    });
  }

  editProduct(): void {
    if (this.form.invalid) return;
    this.loadingUpdate = true;
    this.service.UpdateProduct(this.form.value).subscribe({
      next: () => {
        this.loadingUpdate = false;
        Swal.fire('Success', 'Product updated successfully.', 'success');
        this.router.navigate(['/product']);
      },
      error: () => {
        this.loadingUpdate = false;
      },
    });
  }

  handleBack(): void {
    this.router.navigate(['/product']);
  }
}
