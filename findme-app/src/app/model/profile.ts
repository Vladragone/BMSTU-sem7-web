import { User } from './user';

export interface Profile {
  gameNum: number;
  regDate: string;
  score: number;
  user: User;
}
