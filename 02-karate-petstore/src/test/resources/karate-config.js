function fn() {
  var config = {
    baseUrl: 'https://petstore.swagger.io/v2'
  };

  karate.configure('connectTimeout', 10000);
  karate.configure('readTimeout', 20000);

  return config;
}
