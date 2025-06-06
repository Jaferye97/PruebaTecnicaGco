import { Component } from '@angular/core';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css'], // ← CSS, no SCSS
})
export class SidebarComponent {
  productMenuOpen = true;

  toggleProductMenu() {
    this.productMenuOpen = !this.productMenuOpen;
  }
}
