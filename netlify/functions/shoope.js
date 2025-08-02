const axios = require('axios');

exports.handler = async (event) => {
  const { itemid, shopid } = event.queryStringParameters;
  
  try {
    const response = await axios.get(`https://shopee.com.br/api/v4/item/get`, {
      params: { itemid, shopid },
      headers: {
        'User-Agent': 'Mozilla/5.0',
        'Referer': 'https://shopee.com.br/'
      }
    });

    return {
      statusCode: 200,
      body: JSON.stringify({
        nome: response.data.data.name,
        imagem: `https://cf.shopee.com.br/file/${response.data.data.image}`,
        preco: (response.data.data.price / 100000).toFixed(2)
      })
    };
  } catch (error) {
    return {
      statusCode: 500,
      body: JSON.stringify({ error: "Erro ao buscar produto" })
    };
  }
};