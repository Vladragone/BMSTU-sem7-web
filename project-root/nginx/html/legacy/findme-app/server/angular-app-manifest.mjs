
export default {
  bootstrap: () => import('./main.server.mjs').then(m => m.default),
  inlineCriticalCss: true,
  baseHref: '/legacy/',
  locale: undefined,
  routes: [
  {
    "renderMode": 2,
    "route": "/legacy"
  },
  {
    "renderMode": 2,
    "route": "/legacy/register"
  },
  {
    "renderMode": 2,
    "route": "/legacy/login"
  },
  {
    "renderMode": 2,
    "route": "/legacy/profile"
  },
  {
    "renderMode": 2,
    "route": "/legacy/rating"
  },
  {
    "renderMode": 2,
    "route": "/legacy/game-settings"
  },
  {
    "renderMode": 2,
    "route": "/legacy/game"
  },
  {
    "renderMode": 2,
    "route": "/legacy/game-result"
  },
  {
    "renderMode": 2,
    "route": "/legacy/faq"
  }
],
  entryPointToBrowserMapping: undefined,
  assets: {
    'index.csr.html': {size: 981, hash: '6e3280175ba03f11c358c5ef157ada0d882b74709cd194b701526b40eb5b0ca7', text: () => import('./assets-chunks/index_csr_html.mjs').then(m => m.default)},
    'index.server.html': {size: 1154, hash: '74c70121f33317dafe695e8338dbde4e2cd3aa7081cd6c67bf6d80d043db1893', text: () => import('./assets-chunks/index_server_html.mjs').then(m => m.default)},
    'register/index.html': {size: 3519, hash: 'c795f5398e070ea22351b09f8cff446b81aff2acf58be6d45be0ed5e2bbd54ef', text: () => import('./assets-chunks/register_index_html.mjs').then(m => m.default)},
    'rating/index.html': {size: 5586, hash: '430a9c67281db2be224e8eda99253330ac8185e8a7b80b69aa09fc2906dcae40', text: () => import('./assets-chunks/rating_index_html.mjs').then(m => m.default)},
    'game-settings/index.html': {size: 3283, hash: 'ca53bfc46af05466a8ce87c832527eb9406814f07c2f966ba809cd6a3226f047', text: () => import('./assets-chunks/game-settings_index_html.mjs').then(m => m.default)},
    'game/index.html': {size: 2155, hash: 'ab6b30f11cb213b2b8206a235ebdd7e24ed936b5702fc612f89895dc1b0b1018', text: () => import('./assets-chunks/game_index_html.mjs').then(m => m.default)},
    'game-result/index.html': {size: 3692, hash: '162157fb8d1fa89315f99664b27aeaaa07f0ba2d3814c3d92aca554ed8f3dbf7', text: () => import('./assets-chunks/game-result_index_html.mjs').then(m => m.default)},
    'faq/index.html': {size: 3043, hash: '3d4c90d95eb41a560eb0e850f32f6ed57edaa08922f6197a45e42dfefd14622e', text: () => import('./assets-chunks/faq_index_html.mjs').then(m => m.default)},
    'login/index.html': {size: 3359, hash: '9bc0952203f0a287595780c3007b0ec0cef3bd73c313fd4cd7c56b8a0338a4b9', text: () => import('./assets-chunks/login_index_html.mjs').then(m => m.default)},
    'profile/index.html': {size: 3588, hash: 'ecdfd325b2cd094a1bb01431797bf555bb2800032b4ed2d2524501cd49706608', text: () => import('./assets-chunks/profile_index_html.mjs').then(m => m.default)},
    'index.html': {size: 4627, hash: 'e9effc7b5d56aedc9d2523bb1165fb941991bfc3f17d348000b4fe7ca2055275', text: () => import('./assets-chunks/index_html.mjs').then(m => m.default)},
    'styles-JG6W6TFZ.css': {size: 417, hash: '5R6MiR0xNyo', text: () => import('./assets-chunks/styles-JG6W6TFZ_css.mjs').then(m => m.default)}
  },
};
