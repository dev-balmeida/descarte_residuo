import { Component, OnInit } from '@angular/core';
import { DescarteResiduo } from '../descarteResiduo';
import { DescarteResiduoService } from '../descarteResiduo.service';

@Component({
  selector: 'app-descarteResiduo',
  templateUrl: './descarteResiduo.component.html',
  styleUrls: ['./descarteResiduo.component.css']
})
export class DescarteResiduoComponent implements OnInit {

  descarte: DescarteResiduo[] = [];
  newDescarte: DescarteResiduo = {};
  selectedDescarte: DescarteResiduo | null = null;
  showAddForm: boolean = false;
  searchParams: any = {};

  constructor(private descarteResiduoService: DescarteResiduoService) { }

  ngOnInit(): void {
    this.loadDescarte();
  }

  startAddDescarte(): void {
    this.showAddForm = true;
  }

  cancelAddDecarte(): void {
    this.showAddForm = false;
  }

  loadDescarte(): void {
    this.descarteResiduoService.pointRead().subscribe((data: DescarteResiduo[]) => {
      this.descarte = data;
    });
  }

  pointCreate(): void {
    this.descarteResiduoService.pointCreate(this.newDescarte).subscribe((descarte: DescarteResiduo) => {
      this.descarte.push(descarte);
      this.newDescarte = {};
      this.showAddForm = false;
    });
  }

  pointUpdate(): void {
    if (this.selectedDescarte) {
      this.descarteResiduoService.pointUpdate(this.selectedDescarte.id!, this.selectedDescarte).subscribe((descarte: DescarteResiduo) => {
        const index = this.descarte.findIndex(b => b.id === descarte.id);
        if (index !== -1) {
          this.descarte[index] = descarte;
        }
        this.selectedDescarte = null;
      });
    }
  }

  pointDelete(id: number): void {
    this.descarteResiduoService.pointDelete(id).subscribe(() => {
      this.descarte = this.descarte.filter(b => b.id !== id);
    });
  }

  pointSearch(): void {
    const formattedSearchParams = {
      nome: this.searchParams.nome || null,
      endereco: this.searchParams.endereco || null,
      zona: this.searchParams.zona || null,
      contatos: this.searchParams.contatos || null,
      horarioExpediente: this.searchParams.horarioExpediente || null,

    };

    this.descarteResiduoService.pointSearch(formattedSearchParams).subscribe((data: DescarteResiduo[]) => {
      this.descarte = data;
    });
  }

  clearSelection(): void {
    this.selectedDescarte = null;
  }

  clearFilters(): void {
    this.searchParams = {};
    this.loadDescarte();
  }
}
