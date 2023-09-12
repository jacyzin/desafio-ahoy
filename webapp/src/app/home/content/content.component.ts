import {Component, OnInit} from '@angular/core';
import {MatSnackBar} from "@angular/material/snack-bar";
import {AgenteService} from "../../service/agente.service";
import {Resposta} from "../../interface/resposta.interface";
import {HttpEvent, HttpResponse} from "@angular/common/http";

@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.scss']
})
export class ContentComponent implements OnInit {
  public files: any[] = [];
  isSendingFiles: boolean = false;
  isShowUploadFiles: boolean = true;

  constructor(private _snackBar: MatSnackBar,
  private service: AgenteService){}

  ngOnInit(): void {
    this.noSendFiles();
  }

  /**
   * anexa arquivos na lista
   */
  onFileChange(fileList: File[]){
    if (this.validateExtension(fileList)) {
      this._snackBar.open("Somente arquivos com extensão .xml", 'Fechar', {
        duration: 2000,
      });
      return;
    } else {
      this.files = fileList;
      this._snackBar.open("Arquivos carregados com sucesso!", 'Fechar', {
        duration: 2000,
      });
    }

  }

  /**
   * envia arquivos para a api
   */
  async sendFile() {
    const responses : any[] = [];
    const bar =  [...this.files].map(async file => {
      this.startsendFiles();
      this.service.sendFile(file).subscribe(() => {
        responses.push(true);
        })

    });
    await Promise.all(bar).then(() => {
      setTimeout(()=> {
        this.noSendFiles();
        if (responses.length > 0) {
          this._snackBar.open("Arquivos enviado com sucesso!", "Fechar");
          this.files = [];
        }},4000)
    });

  }

  /**
   * deleta arquivo da listagem
   */
  deleteFile(fileToDelete: File){
    this.files = [...this.files].filter((file: File) => { return file.name != fileToDelete.name });
    this._snackBar.open("Arquivo excluído!", 'Fechar', {
      duration: 2000,
    });
  }

  private validateExtension(fileList: File[]): boolean {
    const containsNonXml = [...fileList].filter(file => {
      return (file.type != 'text/xml');
    }).length > 0
    return containsNonXml;
  }

  private startsendFiles() {
    this.isShowUploadFiles = false;
    this.isSendingFiles = true;
  }

  private noSendFiles() {
    this.isSendingFiles = false;
    this.isShowUploadFiles = true;
  }

}
