
module.exports = {
  publicPath: process.env.NODE_ENV === 'production' ? '/admin/' : '/',

  devServer: {
    proxy: {
      '^': {
        target: 'https://localhost:8111',
        /*target: 'https://147.102.17.76:8111',*/
        ws: true,
        changeOrigin: true
      },
    }
  }
}