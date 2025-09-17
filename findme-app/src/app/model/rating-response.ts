import { Profile } from './profile';

export interface RatingResponse {
  top: Profile[];
  yourRank: number;
  sortBy: string;
}
