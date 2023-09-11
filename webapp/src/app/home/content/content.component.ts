import {Component} from '@angular/core';
import {MatSnackBar} from "@angular/material/snack-bar";
import {convertToParamMap} from "@angular/router";

@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.scss']
})
export class ContentComponent {
  public files: any[] = [];

  constructor(private _snackBar: MatSnackBar){}

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
      this._snackBar.open("Arquivo enviado com sucesso!", 'Fechar', {
        duration: 2000,
      });
    }

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
    const containsNonXml = [...fileList].filter(file => file.type != 'application/xml')
    console.log('containsNonXml:', containsNonXml);
    if (containsNonXml) {
      return true;
    } else {
      return false;
    }
  }

}
