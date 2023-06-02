const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: true,
});

module.exports = {
  devServer: {
    proxy: {
      '/api': {
        target: 'http://127.0.0.1:8081', // 프록시 대상 서버
        changeOrigin: true,
        ws: false,
        pathRewrite: {
          '/api': ''
        }
      },
    },
  }
}