import { Component } from '@angular/core';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css'],
})
export class SidebarComponent {
  activeSection: string | null = null;

  menu = [
    {
      title: 'Product',
      key: 'product',
      icon: 'storage',
      items: [
        { name: 'Products', route: '/product' },
        { name: 'Movement Product', route: '/product/movement' },
      ],
    },
  ];

  toggleSection(key: string) {
    this.activeSection = this.activeSection === key ? null : key;
  }
}
