import { Injectable } from '@angular/core';
import { GameResultService } from './game-result.service';
import { Observable, from, of } from 'rxjs';
import { concatMap, toArray, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class StressTestService {

  constructor(private gameService: GameResultService) {}

  runStressTest(userId: number, onStep: (count: number, time: number) => void): Observable<null[]> {
    const baseLat = 55.760186;
    const baseLng = 37.618711;
    const stepsToMeasure = new Set([1, 5, 10, 50, 100, 500, 1000]);
    const requests = Array.from({ length: 1000 }, (_, i) => i + 1);

    let start = performance.now();

    return from(requests).pipe(
      concatMap((i) => {
        const offsetLat = baseLat + (Math.random() - 0.5) * 0.01;
        const offsetLng = baseLng + (Math.random() - 0.5) * 0.01;

        return this.gameService.saveGame(userId, offsetLat, offsetLng, baseLat, baseLng, 0).pipe(
          catchError(err => of(err)),
          concatMap(() => {
            if (stepsToMeasure.has(i)) {
              const time = +(performance.now() - start).toFixed(2);
              onStep(i, time);
            }
            return of(null);
          })
        );
      }),
      toArray(),
    );
  }
}
