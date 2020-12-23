import { IPlanet } from 'app/shared/model/planet.model';

export interface IEtoile {
  id?: number;
  name?: string;
  latitude?: number;
  longitude?: number;
  planets?: IPlanet[];
}

export class Etoile implements IEtoile {
  constructor(public id?: number, public name?: string, public latitude?: number, public longitude?: number, public planets?: IPlanet[]) {}
}
