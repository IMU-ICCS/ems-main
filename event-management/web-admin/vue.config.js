
module.exports = {
  publicPath: process.env.NODE_ENV === 'production' ? '/admin/' : '/',

  devServer: {
    proxy: {
      '^': {
        target: process.env.NODE_ENV === 'production' ? 'https://localhost:8111' : 'http://localhost:8111',
        ws: true,
        changeOrigin: true
      },
    }
  }
}