// src/app/pipes/gender-icon.pipe.ts
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'genderIconClass'
})
export class GenderIconPipe implements PipeTransform {
  transform(gender: string): string {
    if (!gender) return 'bi bi-question-circle text-secondary';

    switch (gender.toLowerCase()) {
      case 'male':
        return 'bi bi-gender-male text-primary'; // blue for male
      case 'female':
        return 'bi bi-gender-female text-danger'; // red for female
      default:
        return 'bi bi-question-circle text-secondary'; // fallback
    }
  }
}

