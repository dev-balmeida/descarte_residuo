export class DescarteResiduo {
    id?: number;
    nome?: string;
    endereco?: string;
    zona?: string;
    contatos?: string;
    horarioExpediente?: string;
    expanded?: boolean;

    constructor(id: number, nome: string, endereco: string, zona: string, contatos: string, horarioExpediente: string) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.zona = zona;
        this.contatos = contatos;
        this.horarioExpediente = horarioExpediente;
        this.expanded = false;
    }
}
