// Configuração do Firebase
const firebaseConfig = {
  apiKey: "AIzaSyCkjahqXoRLKYU-SzHwiZUufv2icpX2hz4",
  authDomain: "achadinhos-cupons.firebaseapp.com",
  projectId: "achadinhos-cupons",
  storageBucket: "achadinhos-cupons.appspot.com",
  messagingSenderId: "1060602461504",
  appId: "1:1060602461504:web:fb3babb7b4eb95c3958ea5"
};

// Inicialização segura do Firebase
if (typeof firebase !== 'undefined' && !firebase.apps.length) {
  firebase.initializeApp(firebaseConfig);
}

// Inicializa os serviços
const db = firebase.firestore();
const auth = firebase.auth();

// Configurações do Firestore
db.settings({ ignoreUndefinedProperties: true });