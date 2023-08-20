import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'jsonFormat'
})
export class JsonFormatPipe implements PipeTransform {
  transform(value: any): string {
    try {
      return JSON.stringify(JSON.parse(value), null, 2);
    } catch (e) {
      return value;
    }
  }
}
