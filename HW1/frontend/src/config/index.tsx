let HOST, BASE_URL, WS_SCHEME;

const scheme = {
    WS: 'ws://',
    WSS: 'wss://',
    HTTP: 'http://',
};

if (import.meta.env.PROD) {
    HOST = '142.93.36.39';
    BASE_URL = `${scheme.HTTP}${HOST}`;
    WS_SCHEME = scheme.WSS;
} else {
    HOST = 'localhost:8080';
    BASE_URL = `${scheme.HTTP}${HOST}`;
    WS_SCHEME = scheme.WS;
}

const config = {
    PRODUCTION: import.meta.env.PROD,
    HOST,
    BASE_URL,
    API_URL: `${BASE_URL}/api/`,
};

export default config;
