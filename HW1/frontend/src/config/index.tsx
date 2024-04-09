let HOST, BASE_URL;

const scheme = {
    HTTP: 'http://',
};

if (import.meta.env.PROD) {
    HOST = '142.93.36.39';
    BASE_URL = `${scheme.HTTP}${HOST}`;
} else {
    HOST = 'localhost:8080';
    BASE_URL = `${scheme.HTTP}${HOST}`;
}

const config = {
    PRODUCTION: import.meta.env.PROD,
    HOST,
    BASE_URL,
    API_URL: `${BASE_URL}/api/`,
};

console.log(import.meta.env);

export default config;
