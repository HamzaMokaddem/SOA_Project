export interface ILune {
  id?: number;
  name?: string;
  latitude?: number;
  longitude?: number;
  taille?: number;
  planetName?: string;
  planetId?: number;
}

export class Lune implements ILune {
  constructor(
    public id?: number,
    public name?: string,
    public latitude?: number,
    public longitude?: number,
    public taille?: number,
    public planetName?: string,
    public planetId?: number
  ) {}
}
