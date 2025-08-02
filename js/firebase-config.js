// Configuração do Firebase
const firebaseConfig = {
  apiKey: "AIzaSyCkjahqXoRLKYU-SzHwiZUufv2icpX2hz4",
  authDomain: "achadinhos-cupons.firebaseapp.com",
  projectId: "achadinhos-cupons",
  storageBucket: "achadinhos-cupons.firebasestorage.app",
  messagingSenderId: "1060602461504",
  appId: "1:1060602461504:web:fb3babb7b4eb95c3958ea5"
};

// Inicializa o Firebase
const app = firebase.initializeApp(firebaseConfig);

// Inicializa os serviços que você está usando
const db = firebase.firestore();
const auth = firebase.auth();

// Configurações opcionais do Firestore
db.settings({ ignoreUndefinedProperties: true }); // Evita erros com campos undefined

// (Opcional) Exporte se precisar usar em outros arquivos
export { db, auth };