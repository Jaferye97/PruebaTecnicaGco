import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { IndexComponent } from './index/index.component';
import { EditarComponent } from './editar/editar.component';
import { CrearComponent } from './crear/crear.component';
import { MovementComponent } from './movement/movement.component';

const routes: Routes = [
  {
    path: '',
    component: IndexComponent,
  },
  {
    path: 'crear',
    component: CrearComponent,
  },
  {
    path: ':id/movement',
    component: MovementComponent,
  },
  {
    path: ':id',
    component: EditarComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ProductRoutingModule {}
