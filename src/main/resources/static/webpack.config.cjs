const path = require('path');

module.exports = {
    entry: './script/imaging-record.js',
    output: {
        filename: 'bundle.js',
        path: path.resolve(__dirname, 'dist'),
    },
    resolve: {
        extensions: ['.js', '.wasm'],
        fallback: {
            "fs": false,
            "path": require.resolve("path-browserify")
        }
    },
    mode: 'development',
    experiments: {
        asyncWebAssembly: true, // WebAssembly 비동기 로딩 활성화
        syncWebAssembly: true,  // (필요할 경우 사용, deprecated)
        topLevelAwait: true,    // WebAssembly 로딩 지원
    },
    module: {
        rules: [
            {
                test: /\.wasm$/,
                type: "webassembly/async" // 기본 WebAssembly 로딩 방식 유지
            }
        ]
    }
};
