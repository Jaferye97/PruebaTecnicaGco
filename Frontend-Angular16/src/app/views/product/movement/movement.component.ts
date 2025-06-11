import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';

import { ProductService } from '../services/product.service';
import { Product, Movement } from '../interfaces/productInterfaces';

@Component({
  selector: 'app-movement',
  templateUrl: './movement.component.html',
  styleUrls: ['./movement.component.css'],
})
export class MovementComponent {
  productId!: string;
  loadingPage = true;
  product: Product | null = null;
  movements: Movement[] = [];

  displayedColumns: string[] = ['date', 'type', 'amount', 'description'];

  constructor(
    private service: ProductService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.productId = this.route.snapshot.paramMap.get('id')!;
    this.getData(Number(this.productId));
  }

  getData(id: number): void {
    this.service.GetProductWithMovement(id).subscribe((data) => {
      this.loadingPage = false;
      this.product = data.product || null;
      this.movements = data.movements || [];
    });
  }

  handleBack(): void {
    this.router.navigate(['/product']);
  }
}
