import { ILune } from 'app/shared/model/lune.model';

export interface IPlanet {
  id?: number;
  name?: string;
  latitude?: number;
  longitude?: number;
  etat?: string;
  etoileName?: string;
  etoileId?: number;
  lunes?: ILune[];
}

export class Planet implements IPlanet {
  constructor(
    public id?: number,
    public name?: string,
    public latitude?: number,
    public longitude?: number,
    public etat?: string,
    public etoileName?: string,
    public etoileId?: number,
    public lunes?: ILune[]
  ) {}
}
