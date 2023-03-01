/**
 * 在生产环境 代理是无法生效的，所以这里没有生产环境的配置
 * -------------------------------
 * The agent cannot take effect in the production environment
 * so there is no configuration of the production environment
 * For details, please see
 * https://pro.ant.design/docs/deploy
 */
export default {
  dev: {
    /* '/auth/': {
      target: 'http://swwx.sdses.com/wxd',
      changeOrigin: true,
      pathRewrite: { '^': '' },
    },
    '/api_sy/': {
      target: 'http://swwx.sdses.com/wxd',
      changeOrigin: true,
      pathRewrite: { '^': '' },
    }, */
    '/api/': {
      target: 'http://192.168.0.105:1128',
      changeOrigin: true,
      pathRewrite: { '^': '' },
    }
  },
  test: {
    '/api/': {
      target: 'https://preview.pro.ant.design',
      changeOrigin: true,
      pathRewrite: { '^': '' },
    },
  },
  pre: {
    '/api/': {
      target: 'your pre url',
      changeOrigin: true,
      pathRewrite: { '^': '' },
    },
  }
};
