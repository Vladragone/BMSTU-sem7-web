import { Routes } from '@angular/router';
import { StartComponent } from './pages/start/start.component';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { RatingComponent } from './pages/rating/rating.component';
import { GameSettingsComponent } from './pages/game-settings/game-settings.component';
import { GameComponent } from './pages/game/game.component';
import { GameResultComponent } from './pages/game-result/game-result.component';
import { FaqComponent } from './pages/faq/faq.component';
import { StressComponent } from './pages/stress/stress.component';

export const routes: Routes = [
  { path: '', component: StartComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'rating', component: RatingComponent},
  { path: 'game-settings', component: GameSettingsComponent},
  { path: 'game', component: GameComponent},
  { path: 'game-result', component: GameResultComponent},
  { path: 'faq', component: FaqComponent },
  { path: 'stress', component: StressComponent }
];
